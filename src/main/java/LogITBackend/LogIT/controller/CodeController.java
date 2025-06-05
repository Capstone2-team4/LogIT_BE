package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.*;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.service.CodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/codes")
@RequiredArgsConstructor
public class CodeController {

    private final CodeService codeService;

//    @PostMapping("")
//    public ResponseEntity<ApiResponse<CodeResponseDTO>> addCode(@RequestBody CodeRequestDTO request) {
//        CodeResponseDTO codeResponseDTO = codeService.addCode(request);
//        return ResponseEntity.ok(ApiResponse.onSuccess(codeResponseDTO));
//    }

    @GetMapping("/block")
    public ApiResponse<?> getCodeBlocks() {
        List<CodeResponseDTO> response = codeService.getCodeBlocks();
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/block")
    public ApiResponse<?> addCodeBlock(@RequestBody CodeRequestDTO request) {
        CodeResponseDTO codeResponseDTO = codeService.addCodeBlock(request);
        return  ApiResponse.onSuccess(codeResponseDTO);
    }

    @PutMapping("/block/{snippetId}")
    public ApiResponse<?> updateCodeBlock(@PathVariable String snippetId, @RequestBody SnippetUpdateRequest request) {
        SnippetUpdateResponse response = codeService.setCodeBlock(snippetId, request);
        return ApiResponse.onSuccess(response);
    }

    @PostMapping("/blocks/commit")
    public ApiResponse<?> commitCodeBlock(@RequestBody CommitCodeBlocksRequest request) {
        String commitId = request.getCommitId();
        Map<String, CodeRequestDTO> bookmarksMap = request.getBookmarksMap();

        CommitCodeBlocksResponse response = codeService.commitCodeBlock(commitId, bookmarksMap);
        return ApiResponse.onSuccess(response);
    }

    @PutMapping("/block/{snippetId}/status")
    public ApiResponse<?> updateCodeBlockStatus(@PathVariable String snippetId) {
        SnippetUpdateResponse response = codeService.setCodeBlockStatus(snippetId);
        return ApiResponse.onSuccess(response);
    }



}
