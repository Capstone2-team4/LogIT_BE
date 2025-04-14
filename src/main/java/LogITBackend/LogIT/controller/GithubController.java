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

    @GetMapping("/{owners}/{repos}/commits")
    public ResponseEntity<ApiResponse<?>> getCommits(
            @PathVariable("owners") String owners,
            @PathVariable("repos") String repos
    ) {
        List<CommitResponseDTO> commits = githubService.getCommits(owners, repos);
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

    @GetMapping("/users/repos")
    public ResponseEntity<ApiResponse<?>> getUsersRepos() {
        GithubRepoResponse repos = githubService.getUsersRepos();
        return ResponseEntity.ok(ApiResponse.onSuccess(repos));
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
}
