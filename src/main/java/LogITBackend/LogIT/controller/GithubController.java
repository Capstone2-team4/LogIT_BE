package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.CommitDetailResponseDTO;
import LogITBackend.LogIT.DTO.CommitResponseDTO;
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
            @PathVariable("repos") String repos,
            @RequestParam String token
    ) {
        List<CommitResponseDTO> commits = githubService.getInitialCommits(owners, repos, token);
        return ResponseEntity.ok(ApiResponse.onSuccess(commits));
    }

    @GetMapping("/{owners}/{repos}/commits/{id}/details")
    public ResponseEntity<ApiResponse<?>> getCommitDetails(
            @PathVariable("owners") String owners,
            @PathVariable("repos") String repos,
            @PathVariable("id") String commitId,
            @RequestParam String token
    ) {

        CommitDetailResponseDTO commitDetail = githubService.getCommitDetails(owners, repos, commitId, token);
        return ResponseEntity.ok(ApiResponse.onSuccess(commitDetail));
    }

}
