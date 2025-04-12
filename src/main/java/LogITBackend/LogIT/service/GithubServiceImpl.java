package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.CommitDetailResponseDTO;
import LogITBackend.LogIT.DTO.CommitResponseDTO;
import LogITBackend.LogIT.DTO.FileResponseDTO;
import LogITBackend.LogIT.apiPayload.exception.GeneralException;
import LogITBackend.LogIT.domain.Commit;
import LogITBackend.LogIT.domain.CommitParent;
import LogITBackend.LogIT.domain.File;
import LogITBackend.LogIT.domain.Users;
import LogITBackend.LogIT.domain.enums.Gender;
import LogITBackend.LogIT.domain.enums.LoginType;
import LogITBackend.LogIT.domain.enums.UserStatus;
import LogITBackend.LogIT.repository.CommitParentRepository;
import LogITBackend.LogIT.repository.CommitRepository;
import LogITBackend.LogIT.repository.FileRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static LogITBackend.LogIT.apiPayload.code.status.ErrorStatus.COMMIT_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class GithubServiceImpl implements GithubService {

    private final CommitRepository commitRepository;
    private final CommitParentRepository commitParentRepository;
    private final FileRepository fileRepository;

    @Override
    @Transactional
    public List<CommitResponseDTO> getInitialCommits(String owner, String repo, String accessToken) {
        String url = String.format("https://api.github.com/repos/%s/%s/commits", owner, repo);


        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.set("Accept", "application/vnd.github+json");
        headers.set("X-GitHub-Api-Version", "2022-11-28");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate();

        Users dummyUser = Users.builder()
                .id(1L)  // DB에 실제 저장하지 않아도 됨, 그냥 임의의 ID
                .nickname("dummy01")
                .username("dummyuser")
                .password("dummy1234!")  // 사용 안 하므로 무관
                .loginType(LoginType.REGULAR)
                .status(UserStatus.ACTIVE)
                .githubNickname("dummyGithub")
                .email("dummy@example.com")
                .gender(Gender.MALE)
                .build();

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Map<String, Object>>>() {}
        );

        List<Map<String, Object>> body = response.getBody();
        if (body == null) return Collections.emptyList();

        List<Commit> savedCommits = body.stream().map(item -> {
            String sha = (String) item.get("sha");
            Map<String, Object> commit = (Map<String, Object>) item.get("commit");
            String message = (String) commit.get("message");
            Map<String, Object> author = (Map<String, Object>) commit.get("author");
            String dateStr = (String) author.get("date");
            LocalDateTime date = LocalDateTime.parse(dateStr.replace("Z", ""));



            return new Commit(
                    sha,
                    dummyUser,
                    message,
                    null,  // stats 필드는 이후에 계산할 수 있음
                    date,
                    null
            );
        }).collect(Collectors.toList());

        commitRepository.saveAll(savedCommits);

        // 커밋을 SHA 기준으로 Map으로 변환 (List 참조 대신 성능 개선함)
        Map<String, Commit> shaToCommitMap = savedCommits.stream()
                .collect(Collectors.toMap(Commit::getId, c -> c));

        List<CommitParent> savedParents = body.stream()
                .flatMap(item -> {
                    String childSha = (String) item.get("sha");
                    Commit child = shaToCommitMap.get(childSha);
                    List<Map<String, Object>> parents = (List<Map<String, Object>>) item.get("parents");

                    return parents.stream()
                            .map(parentMap -> {
                                String parentSha = (String) parentMap.get("sha");
                                Commit parent = shaToCommitMap.get(parentSha);
                                if (child != null && parent != null) {
                                    CommitParent cp = new CommitParent();
                                    cp.setCommit(child);
                                    cp.setParent(parent);
                                    return cp;
                                }
                                return null;
                            })
                            .filter(Objects::nonNull);
                })
                .collect(Collectors.toList());

        commitParentRepository.saveAll(savedParents);


        return savedCommits.stream()
                .map(c -> new CommitResponseDTO(c.getId(), c.getUser().getId() ,c.getMessage(), c.getStats(), c.getDate()))
                .collect(Collectors.toList());

    }

    @Override
    public void getCommitsFromWebhook(String payload) {

    }

    @Override
    @Transactional
    public CommitDetailResponseDTO getCommitDetails(String owner, String repo, String commitId, String token) {
        Commit commit = commitRepository.findById(commitId)
                .orElseThrow(() -> new GeneralException(COMMIT_NOT_FOUND));

        // stats가 null이면 GitHub에서 정보 요청
        if (commit.getStats() == null) {
            RestTemplate restTemplate = new RestTemplate();

            // GitHub API 호출
            String url = String.format("https://api.github.com/repos/%s/%s/commits/%s", owner, repo, commitId);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            headers.set("Accept", "application/vnd.github+json");

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<JsonNode> response = restTemplate.exchange(url, HttpMethod.GET, entity, JsonNode.class);
            JsonNode body = response.getBody();

            // stats 정보 세팅
            JsonNode statsNode = body.get("stats");
            if (statsNode != null) {
                int additions = statsNode.get("additions").asInt();
                int deletions = statsNode.get("deletions").asInt();
                int total = statsNode.get("total").asInt();
                String statsString = String.format("%d additions, %d deletions (total: %d)", additions, deletions, total);
                commit.setStats(statsString);
            }
            // files 저장
            List<File> fileList = new ArrayList<>();
            for (JsonNode fileNode : body.get("files")) {
                File file = new File();
                file.setCommit(commit);
                file.setFilename(fileNode.get("filename").asText());
                file.setAdditions(fileNode.get("additions").asLong());
                file.setDeletions(fileNode.get("deletions").asLong());
                file.setPatch(fileNode.has("patch") ? fileNode.get("patch").asText() : null);
                fileList.add(file);
            }
            fileRepository.saveAll(fileList);

            // 업데이트 저장
            commitRepository.save(commit);
        }

        List<File> files = fileRepository.findAllByCommitId(commitId);

        List<FileResponseDTO> fileResponses = files.stream()
                .map(FileResponseDTO::fromEntity)
                .collect(Collectors.toList());

        CommitResponseDTO commitResponseDTO = CommitResponseDTO.fromEntity(commit);
        return new CommitDetailResponseDTO(commitResponseDTO, fileResponses);
    }
}
