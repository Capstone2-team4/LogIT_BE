package LogITBackend.LogIT.DTO;

import LogITBackend.LogIT.domain.enums.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class UserResponseDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSignUpResultDTO {
        Long userId;
        String nickname;
        LoginType loginType;
        LocalDateTime createdAt;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserSignInResultDTO {
        String accessToken;
        String refreshToken;
        String nickname;
        LocalDateTime createdAt;
    }
}
