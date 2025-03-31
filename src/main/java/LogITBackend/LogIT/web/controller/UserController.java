package LogITBackend.LogIT.web.controller;

import LogITBackend.LogIT.converter.UserConverter;
import LogITBackend.LogIT.web.dto.UserRequestDTO;
import LogITBackend.LogIT.web.dto.UserResponseDTO;
import LogITBackend.LogIT.apiPayload.ApiResponse;
import LogITBackend.LogIT.domain.Users;
import LogITBackend.LogIT.service.UserCommandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserCommandService userCommandService;

    @Operation(summary = "회원가입", description =
            "# 회원가입 API 입니다. 닉네임과 아이디, 패스워드를 body에 입력해주세요."
    )
    @PostMapping("/signup")
    public ApiResponse<UserResponseDTO.UserSignUpResultDTO> signUp(
            @RequestBody @Valid UserRequestDTO.SignUpRequestDTO request
    ) {
        Users newUser = userCommandService.signUp(request);
        return ApiResponse.onSuccess(
                UserConverter.toUserSignUpResultDTO(
                        newUser
                )
        );
    }
}
