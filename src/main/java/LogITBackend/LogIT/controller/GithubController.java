package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.*;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.service.GithubService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/githubs")
public class GithubController {

    private final GithubService githubService;

    @Operation(summary = "깃허브 - 브랜치 커밋 리스트 조회", description =
            "# 깃허브 관련 API입니다."
    )
    @GetMapping("/{owners}/{repos}/{branches}/commits")
    public ResponseEntity<ApiResponse<?>> getCommits(
            @PathVariable("owners") String owners,
            @PathVariable("repos") String repos,
            @PathVariable("branches") String branches
    ) {
        List<CommitResponseDTO> commits = githubService.getCommits(owners, repos, branches);
        return ResponseEntity.ok(ApiResponse.onSuccess(commits));
    }

    @Operation(summary = "깃허브 - 커밋 세부사항 (파일 리스트) 조회", description =
            "# 깃허브 관련 API입니다."
    )
    @GetMapping("/{owners}/{repos}/commits/{id}/details")
    public ResponseEntity<ApiResponse<?>> getCommitDetails(
            @PathVariable("owners") String owners,
            @PathVariable("repos") String repos,
            @PathVariable("id") String commitId
    ) {
        CommitDetailResponseDTO commitDetail = githubService.getCommitDetails(owners, repos, commitId);
        return ResponseEntity.ok(ApiResponse.onSuccess(commitDetail));
    }

    @Operation(summary = "깃허브 - 유저의 Organization 조회", description =
            "# 깃허브 관련 API입니다."
    )
    @GetMapping("/users/org")
    public ResponseEntity<ApiResponse<?>> getUserOrgs() {
        List<OrgResponse> orgs = githubService.getUserOrgs();
        return ResponseEntity.ok(ApiResponse.onSuccess(orgs));
    }

    @Operation(summary = "깃허브 - Owner Repository 조회", description =
            "# 깃허브 관련 API입니다."
    )
    @GetMapping("/users/{owners}/repos")
    public ResponseEntity<ApiResponse<?>> getUserOrgsRepos(
            @PathVariable("owners") String owners
    ) {
        GithubRepoResponse repos = githubService.getUserOrgsRepos(owners);
        return ResponseEntity.ok(ApiResponse.onSuccess(repos));
    }

    @Operation(summary = "깃허브 - 유저의 Repository 조회", description =
            "# 깃허브 관련 API입니다."
    )
    @GetMapping("/users/repos")
    public ResponseEntity<ApiResponse<?>> getUsersRepos() {
        GithubRepoResponse repos = githubService.getUsersRepos();
        return ResponseEntity.ok(ApiResponse.onSuccess(repos));
    }

    @Operation(summary = "깃허브 - 유저의 특정 Repository Branch 조회", description =
            "# 깃허브 관련 API입니다."
    )
    @GetMapping("/{owners}/{repos}/branches")
    public ResponseEntity<ApiResponse<?>> getUserBranches(
            @PathVariable("owners") String owners,
            @PathVariable("repos") String repos
    ) {
        List<BranchResponseDTO> branches = githubService.getUserBranches(owners, repos);
        return ResponseEntity.ok(ApiResponse.onSuccess(branches));

    }

    @Operation(summary = "깃허브 - Commit 당시의 전체 코드 조회", description =
            "# 깃허브 관련 API입니다."
    )
    @GetMapping("/{owners}/{repos}/{filePath}")
    public ApiResponse<?> getCommitsFile(
            @PathVariable("owners") String owners,
            @PathVariable("repos") String repos,
            @PathVariable("filePath") String fileName,
            @RequestParam("commitId") String commitId
    ) {
        FileResponseDTO.CommitFileResponseDTO response = githubService.getCommitsFile(owners, repos, fileName, commitId);
        return ApiResponse.onSuccess(response);
    }
}
