package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.*;

import java.util.List;

public interface GithubService {
    List<CommitResponseDTO> getCommits(String owner, String repo, String branch);

    CommitDetailResponseDTO getCommitDetails(String owners, String repos, String commitId);

    GithubRepoResponse getUsersRepos();

    List<OrgResponse> getUserOrgs();

    GithubRepoResponse getUserOrgsRepos(String owners);

    List<BranchResponseDTO> getUserBranches(String owner, String repo);
}
