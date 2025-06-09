package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.Chatgpt.AiSummaryRequestDTO;
import LogITBackend.LogIT.DTO.Chatgpt.AiSummaryResponseDTO;
import LogITBackend.LogIT.DTO.Chatgpt.ChatGPTRequestDTO;
import LogITBackend.LogIT.DTO.Chatgpt.ChatGPTResponseDTO;
import LogITBackend.LogIT.DTO.CommitDetailResponseDTO;
import LogITBackend.LogIT.DTO.CommitResponseDTO;
import LogITBackend.LogIT.DTO.FileResponseDTO;
import LogITBackend.LogIT.apiPayload.code.status.ErrorStatus;
import LogITBackend.LogIT.apiPayload.exception.GeneralException;
import LogITBackend.LogIT.config.security.SecurityUtil;
import LogITBackend.LogIT.converter.AiSummaryConverter;
import LogITBackend.LogIT.domain.SummaryTemplate;
import LogITBackend.LogIT.domain.Users;
import LogITBackend.LogIT.repository.SummaryTemplateRepository;
import LogITBackend.LogIT.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiSummaryCommandServiceImpl implements AiSummaryCommandService {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    @Autowired
    private RestTemplate template;

    private final GithubService githubService;
    private final UserRepository userRepository;
    private final SummaryTemplateRepository summaryTemplateRepository;

    // ChatGPT API 요청
    public String getResponseOfChatGptApi(String systemPrompt, String userPrompt){
        // request를 api로 보내 chatGPT응답받기
        ChatGPTRequestDTO chatGPTrequest = new ChatGPTRequestDTO(model, systemPrompt,userPrompt);
        ChatGPTResponseDTO chatGPTResponse =  template.postForObject(apiURL, chatGPTrequest, ChatGPTResponseDTO.class);

        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }

    @Override
    public AiSummaryResponseDTO.CreateAiSummaryResultDTO createAiSummary(
            String owners,
            String repository,
            AiSummaryRequestDTO.CreateAiSummaryRequest request
    ) {
        Long userId = SecurityUtil.getCurrentUserId();
        StringBuilder userPrompt = new StringBuilder();

        SummaryTemplate summaryTemplate = summaryTemplateRepository.findByUsers(
                userRepository.findById(userId)
                        .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND))
        );
        userPrompt.append("요약 글 template: ").append(summaryTemplate.getContent()).append("\n");
        for (String commitId : request.getCommitIdList()) {
            CommitDetailResponseDTO commitDetail = getCommitInfo(
                    owners,
                    repository,
                    commitId
            );
            userPrompt.append("커밋 메시지: ").append(commitDetail.getCommitResponseDTO().getMessage()).append("\n");
            userPrompt.append("커밋 파일 목록:\n");
            for (FileResponseDTO file : commitDetail.getFiles()) {
                userPrompt.append("파일명: ").append(file.getFilename()).append("\n");
                userPrompt.append("해당파일의 코드: ").append(file.getPatch()).append("\n");
            }
        }
        System.out.println("-------------------User Prompt: \n" + userPrompt.toString());
        String message = getResponseOfChatGptApi(
                "브랜치에 포함된 변경된 코드들을 제시해주면 이를 기반으로 코드와 코드에 대한 설명을 해주는 요약 글을 작성해줘. 양식은 template에 맞춰 작성해줘.",
                userPrompt.toString()
        );
        System.out.println("-------------------ChatGPT Response: \n" + message);
        return AiSummaryResponseDTO.CreateAiSummaryResultDTO.builder()
                .AiSummaryRecord(message)
                .build();
    }

    private CommitDetailResponseDTO getCommitInfo(String owner, String repo, String commitId) {
        Long userId = SecurityUtil.getCurrentUserId();
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        String token = user.getGithubAccesstoken();

        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("https://api.github.com/repos/%s/%s/commits/%s", owner, repo, commitId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token); // GitHub Personal Access Token 필요
        headers.set("Accept", "application/vnd.github+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
        JsonNode body = response.getBody();

        if (body == null) {
            throw new RuntimeException("GitHub API 응답이 비어 있습니다.");
        }

        // ✅ CommitResponseDTO 생성
        String message = body.path("commit").path("message").asText();
        String stats = String.format("additions: %d, deletions: %d, total: %d",
                body.path("stats").path("additions").asInt(),
                body.path("stats").path("deletions").asInt(),
                body.path("stats").path("total").asInt());

        String dateStr = body.path("commit").path("committer").path("date").asText();
        LocalDateTime date = LocalDateTime.parse(dateStr.replace("Z", ""));

        CommitResponseDTO commitResponseDTO = new CommitResponseDTO();
        commitResponseDTO.setId(commitId);
        commitResponseDTO.setRepo_id(null); // 필요 시 외부에서 주입
        commitResponseDTO.setMessage(message);
        commitResponseDTO.setStats(stats);
        commitResponseDTO.setDate(date);

        // ✅ FileResponseDTO 리스트 생성
        List<FileResponseDTO> files = new ArrayList<>();
        JsonNode filesArray = body.path("files");
        if (filesArray.isArray()) {
            for (JsonNode fileNode : filesArray) {
                FileResponseDTO fileDTO = new FileResponseDTO();
                fileDTO.setId(null); // DB 저장 전이므로 ID 없음
                fileDTO.setFilename(fileNode.path("filename").asText());
                fileDTO.setAdditions(fileNode.path("additions").asLong());
                fileDTO.setDeletions(fileNode.path("deletions").asLong());
                fileDTO.setPatch(fileNode.path("patch").asText(null)); // patch는 없을 수 있음
                fileDTO.setCreatedAt(null); // GitHub API에서는 없음
                fileDTO.setUpdatedAt(null); // GitHub API에서는 없음

                files.add(fileDTO);
            }
        }

        // ✅ 최종 DTO 조합
        return new CommitDetailResponseDTO(commitResponseDTO, files);
    }

    @Override
    @Transactional
    public void createSummaryTemplate(AiSummaryRequestDTO.CreateSummaryTemplateRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        Users getUser = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        SummaryTemplate summaryTemplate = AiSummaryConverter.toSummaryTemplate(request);
        summaryTemplate.setUsers(getUser);
        summaryTemplateRepository.deleteByUsers(getUser); // 기존 템플릿 삭제
        summaryTemplateRepository.flush(); // 🔥 강제 flush로 즉시 delete 쿼리 실행
        summaryTemplateRepository.save(summaryTemplate);
    }

    @Override
    public AiSummaryResponseDTO.getSummaryTemplateResultDTO getSummaryTemplate() {
        Long userId = SecurityUtil.getCurrentUserId();
        Users getUser = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        SummaryTemplate summaryTemplate = summaryTemplateRepository.findByUsers(getUser);
        if (summaryTemplate == null) {
            return AiSummaryResponseDTO.getSummaryTemplateResultDTO.builder()
                    .template("") // 기본 템플릿 설정
                    .build();
        }
        return AiSummaryConverter.toGetSummaryTemplateResultDTO(summaryTemplate);
    }
}
