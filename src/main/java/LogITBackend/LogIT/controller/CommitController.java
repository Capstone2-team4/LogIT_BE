package LogITBackend.LogIT.controller;

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
public class CommitController {

    private final GithubService githubService;

    @GetMapping("/commits")
    public ResponseEntity<ApiResponse<?>> getCommits(
            @RequestParam String owner,
            @RequestParam String repo,
            @RequestParam String token
    ) {
        List<CommitResponseDTO> commits = githubService.getInitialCommits(owner, repo, token);
        return ResponseEntity.ok(ApiResponse.onSuccess(commits));
    }

}
