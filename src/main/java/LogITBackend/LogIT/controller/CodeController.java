package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.*;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.service.CodeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/codes")
@RequiredArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @Operation(summary = "플러그인 - 레디스 코드블럭 조회 ", description =
            "# 플러그인 관련 API입니다."
    )
    @GetMapping("/block")
    public ApiResponse<?> getCodeBlocks() {
        List<CodeResponseDTO> response = codeService.getCodeBlocks();
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "플러그인 - 레디스 코드블럭 추가", description =
            "# 플러그인 관련 API입니다."
    )
    @PostMapping("/block")
    public ApiResponse<?> addCodeBlock(@RequestBody CodeRequestDTO request) {
        CodeResponseDTO codeResponseDTO = codeService.addCodeBlock(request);
        return  ApiResponse.onSuccess(codeResponseDTO);
    }

    @Operation(summary = "플러그인 - 레디스 코드블럭 내용 업데이트", description =
            "# 플러그인 관련 API입니다."
    )
    @PutMapping("/block/{snippetId}")
    public ApiResponse<?> updateCodeBlock(@PathVariable String snippetId, @RequestBody SnippetUpdateRequest request) {
        SnippetUpdateResponse response = codeService.setCodeBlock(snippetId, request);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "플러그인 - 커밋 이벤트 발생시 DB 저장 API ", description =
            "# 플러그인 관련 API입니다."
    )
    @PostMapping("/blocks/commit")
    public ApiResponse<?> commitCodeBlock(@RequestBody CommitCodeBlocksRequest request) {
        String commitId = request.getCommitId();
        Map<String, CodeRequestDTO> bookmarksMap = request.getBookmarksMap();

        CommitCodeBlocksResponse response = codeService.commitCodeBlock(commitId, bookmarksMap);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "플러그인 - 코드블럭 상태 업데이트", description =
            "# 플러그인 관련 API입니다."
    )
    @PutMapping("/block/{snippetId}/status")
    public ApiResponse<?> updateCodeBlockStatus(@PathVariable String snippetId) {
        SnippetUpdateResponse response = codeService.setCodeBlockStatus(snippetId);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "플러그인 - snippetId(uuid)로 해당 코드블럭 Redis에서 삭제", description =
            "# 플러그인 관련 API입니다."
    )
    @DeleteMapping("/redis/blocks/{snippetId}")
    public ApiResponse<?> deleteCodeBlockFromRedis(@PathVariable String snippetId) {
        CodeResponseDTO.CodeDeleteResponseDTO response = codeService.deleteCodeBlockFromRedis(snippetId);
        return ApiResponse.onSuccess(response);
    }



    @Operation(summary = "커밋id로 코드블럭 조회", description =
            "# 커밋id로 관련 코드블럭 조회 API 입니다. 아이디와 패스워드를 body에 입력해주세요."
    )
    @GetMapping("/blocks/{commitId}")
    public ApiResponse<?> getCodeBlockList(@PathVariable String commitId) {
        CodeBlockListResponse response = codeService.getCodeBlockList(commitId);
        return ApiResponse.onSuccess(response);
    }

    @Operation(summary = "snippetId(uuid)로 해당 코드블럭 DB에서 삭제", description =
            "# snippetId(uuid)로 관련 코드블럭 삭제 API 입니다. 아이디와 패스워드를 body에 입력해주세요."
    )
    @DeleteMapping("/blocks/{snippetId}")
    public ApiResponse<?> deleteCodeBlock(@PathVariable String snippetId) {
        CodeResponseDTO.CodeDeleteResponseDTO response = codeService.deleteCodeBlock(snippetId);
        return ApiResponse.onSuccess(response);
    }


}
