package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.CommitDetailResponseDTO;
import LogITBackend.LogIT.DTO.CommitResponseDTO;
import LogITBackend.LogIT.DTO.GithubRepoResponse;
import LogITBackend.LogIT.DTO.RepositoryResponseDTO;

import java.util.List;

public interface GithubService {
    List<CommitResponseDTO> getCommits(String owner, String repo);

    CommitDetailResponseDTO getCommitDetails(String owners, String repos, String commitId);

    GithubRepoResponse getUsersRepos();
}
