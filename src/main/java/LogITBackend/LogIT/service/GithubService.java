package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.CommitResponseDTO;

import java.util.List;

public interface GithubService {
    List<CommitResponseDTO> getInitialCommits(String owner, String repo, String accessToken);
    void getCommitsFromWebhook(String payload);
}
