package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.CommitDetailResponseDTO;
import LogITBackend.LogIT.DTO.CommitResponseDTO;

import java.util.List;

public interface GithubService {
    List<CommitResponseDTO> getInitialCommits(String owner, String repo, String accessToken);
    void getCommitsFromWebhook(String payload);

    CommitDetailResponseDTO getCommitDetails(String owners, String repos, String commitId, String token);
}
