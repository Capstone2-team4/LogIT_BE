package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.ErrorRequestDTO;
import LogITBackend.LogIT.DTO.ErrorResponseDTO;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.service.ErrorCommandService;
import LogITBackend.LogIT.service.ErrorQueryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/errors")
public class ErrorController {

    private final ErrorCommandService errorCommandService;
    private final ErrorQueryService errorQueryService;

    @Operation(summary = "플러그인 API: 에러, 에러코드, 에러해결 코드 저장 API", description =
            "# 에러, 에러코드, 에러해결 코드 저장 API 입니다. 에러, 에러코드, 에러해결 코드를 body에 입력해주세요."
    )
    @PostMapping("/save/errorInfo")
    public ApiResponse<?> saveErrorInfo(
            @RequestBody ErrorRequestDTO.ErrorListWrapperDTO request
    ) {
        errorCommandService.saveErrorInfo(request);
        return ApiResponse.onSuccess(null);
    }

    @Operation(summary = "에러 리스트 조회 API", description =
            "# 에러 리스트 조회 API 입니다. commit hash값을 body에 입력해주세요."
    )
    @GetMapping("/errorInfoList/{commitId}")
    public ApiResponse<ErrorResponseDTO.GetErrorInfoListResultDTO> getErrorInfoList(
            @PathVariable String commitId
    ) {
        return ApiResponse.onSuccess(
                errorQueryService.getErrorInfoList(commitId)
        );
    }

    @Operation(summary = "에러 코드 리스트 조회 API", description =
            "# 에러 코드 리스트 조회 API 입니다. errorInfoId값을 body에 입력해주세요."
    )
    @GetMapping("/errorCodeList/{errorInfoId}")
    public ApiResponse<ErrorResponseDTO.GetErrorCodeListDTO> getErrorCodeList(
            @PathVariable Long errorInfoId
    ) {
        return ApiResponse.onSuccess(
                errorQueryService.getErrorCodeList(errorInfoId)
        );
    }

    @Operation(summary = "에러 해결 코드 리스트 조회 API", description =
            "# 에러 해결 코드 리스트 조회 API 입니다. errorInfoId값을 body에 입력해주세요."
    )
    @GetMapping("/errorSolvedCodeList/{errorInfoId}")
    public ApiResponse<ErrorResponseDTO.GetErrorSolvedCodeListDTO> getErrorSolvedCodeList(
            @PathVariable Long errorInfoId
    ) {
        return ApiResponse.onSuccess(
                errorQueryService.getErrorSolvedCodeList(errorInfoId)
        );
    }
}
