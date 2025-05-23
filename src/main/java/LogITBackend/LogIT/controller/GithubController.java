package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.*;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/githubs")
public class GithubController {

    private final GithubService githubService;

    @GetMapping("/{owners}/{repos}/{branches}/commits")
    public ResponseEntity<ApiResponse<?>> getCommits(
            @PathVariable("owners") String owners,
            @PathVariable("repos") String repos,
            @PathVariable("branches") String branches
    ) {
        List<CommitResponseDTO> commits = githubService.getCommits(owners, repos, branches);
        return ResponseEntity.ok(ApiResponse.onSuccess(commits));
    }

    @GetMapping("/{owners}/{repos}/commits/{id}/details")
    public ResponseEntity<ApiResponse<?>> getCommitDetails(
            @PathVariable("owners") String owners,
            @PathVariable("repos") String repos,
            @PathVariable("id") String commitId
    ) {
        CommitDetailResponseDTO commitDetail = githubService.getCommitDetails(owners, repos, commitId);
        return ResponseEntity.ok(ApiResponse.onSuccess(commitDetail));
    }

    @GetMapping("/users/org")
    public ResponseEntity<ApiResponse<?>> getUserOrgs() {
        List<OrgResponse> orgs = githubService.getUserOrgs();
        return ResponseEntity.ok(ApiResponse.onSuccess(orgs));
    }

    @GetMapping("/users/{owners}/repos")
    public ResponseEntity<ApiResponse<?>> getUserOrgsRepos(
            @PathVariable("owners") String owners
    ) {
        GithubRepoResponse repos = githubService.getUserOrgsRepos(owners);
        return ResponseEntity.ok(ApiResponse.onSuccess(repos));
    }

    @GetMapping("/users/repos")
    public ResponseEntity<ApiResponse<?>> getUsersRepos() {
        GithubRepoResponse repos = githubService.getUsersRepos();
        return ResponseEntity.ok(ApiResponse.onSuccess(repos));
    }

    @GetMapping("/{owners}/{repos}/branches")
    public ResponseEntity<ApiResponse<?>> getUserBranches(
            @PathVariable("owners") String owners,
            @PathVariable("repos") String repos
    ) {
        List<BranchResponseDTO> branches = githubService.getUserBranches(owners, repos);
        return ResponseEntity.ok(ApiResponse.onSuccess(branches));

    }
}
