package LogITBackend.LogIT.controller;

import LogITBackend.LogIT.DTO.ErrorRequestDTO;
import LogITBackend.LogIT.DTO.ErrorResponseDTO;
import LogITBackend.LogIT.DTO.UserRequestDTO;
import LogITBackend.LogIT.DTO.UserResponseDTO;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.converter.UserConverter;
import LogITBackend.LogIT.domain.Users;
import LogITBackend.LogIT.service.ErrorCommandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/errors")
public class ErrorController {

    private final ErrorCommandService errorCommandService;

    @Operation(summary = "플러그인 API: 에러, 에러코드, 에러해결 코드 저장 API", description =
            "# 에러, 에러코드, 에러해결 코드 저장 API 입니다. 에러, 에러코드, 에러해결 코드를 body에 입력해주세요."
    )
    @PostMapping("/save/errorInfo")
    public ApiResponse<?> saveErrorInfo(
            @RequestBody ErrorRequestDTO.SaveErrorInfoRequestDTO request
    ) {
        errorCommandService.saveErrorInfo(request);
        return ApiResponse.onSuccess(null);
    }
}
