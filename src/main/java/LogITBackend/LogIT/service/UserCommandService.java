package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.UserRequestDTO;
import LogITBackend.LogIT.DTO.UserResponseDTO;
import LogITBackend.LogIT.domain.Users;

public interface UserCommandService {
    Users signUp(UserRequestDTO.SignUpRequestDTO request);
    UserResponseDTO.UserSignInResultDTO signIn(UserRequestDTO.SignInRequestDTO request);
    String generateAccessToken(Long userId, int accessExpTime);
    String generateAndSaveRefreshToken(String key, int refreshExpTime);
}
