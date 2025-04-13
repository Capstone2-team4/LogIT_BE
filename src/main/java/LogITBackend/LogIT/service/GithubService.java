package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.CommitDetailResponseDTO;
import LogITBackend.LogIT.DTO.CommitResponseDTO;
import LogITBackend.LogIT.DTO.RepositoryResponseDTO;

import java.util.List;

public interface GithubService {
    List<CommitResponseDTO> getInitialCommits(String owner, String repo);
    void getCommitsFromWebhook(String payload);

    CommitDetailResponseDTO getCommitDetails(String owners, String repos, String commitId);

    List<RepositoryResponseDTO> getUsersRepos();
}
