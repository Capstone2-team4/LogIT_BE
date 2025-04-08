package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.CommitResponseDTO;
import LogITBackend.LogIT.domain.Commit;
import LogITBackend.LogIT.domain.Users;
import LogITBackend.LogIT.domain.enums.Gender;
import LogITBackend.LogIT.domain.enums.LoginType;
import LogITBackend.LogIT.domain.enums.UserStatus;
import LogITBackend.LogIT.repository.CommitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GithubServiceImpl implements GithubService {

    private final CommitRepository commitRepository;

    @Override
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

        return savedCommits.stream()
                .map(c -> new CommitResponseDTO(c.getId(), c.getUser().getId() ,c.getMessage(), c.getStats(), c.getDate()))
                .collect(Collectors.toList());

    }

    @Override
    public void getCommitsFromWebhook(String payload) {

    }
}
