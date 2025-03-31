package LogITBackend.LogIT.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDTO {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SignUpRequestDTO {
//        @NotBlank(message = "이름은 빈값일 수 없습니다.")
//        private String name;
//        @NotBlank(message = "이메일은 빈값일 수 없습니다.")
//        private String email;
        @NotBlank(message = "닉네임은 빈값일 수 없습니다.")
        @Size(max = 8, message = "닉네임은 1 ~ 8자이어야 합니다.")
        private String nickname;
        @NotBlank(message = "아이디는 빈값일 수 없습니다.")
        @Size(min = 6, max = 15, message = "아이디는 6 ~ 15자이어야 합니다.")
        private String username;
        @NotBlank(message = "비밀번호는 빈값일 수 없습니다.")
        private String password;
    }
}
