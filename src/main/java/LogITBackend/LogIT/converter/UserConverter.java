package LogITBackend.LogIT.converter;

import LogITBackend.LogIT.DTO.UserRequestDTO;
import LogITBackend.LogIT.DTO.UserResponseDTO;
import LogITBackend.LogIT.domain.Users;

public class UserConverter {
    public static Users toUsers(UserRequestDTO.SignUpRequestDTO request) {
        return Users.builder()
//                .name(request.getName())
//                .email(request.getEmail())
                .nickname(request.getNickname())
                .username(request.getUsername())
//                .loginType(LoginType.REGULAR)
                .build();
    }
    public static UserResponseDTO.UserSignUpResultDTO toUserSignUpResultDTO(Users users) {
        return UserResponseDTO.UserSignUpResultDTO.builder()
                .userId(users.getId())
                .nickname(users.getNickname())
                .loginType(users.getLoginType())
                .createdAt(users.getCreatedAt())
                .build();
    }

    public static UserResponseDTO.UserSignInResultDTO toUserSignInResultDTO(Users users, String accessToken, String refreshToken) {
        return UserResponseDTO.UserSignInResultDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .nickname(users.getNickname())
                .createdAt(users.getCreatedAt())
                .build();
    }
}
