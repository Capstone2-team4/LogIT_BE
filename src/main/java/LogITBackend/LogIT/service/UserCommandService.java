package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.UserRequestDTO;
import LogITBackend.LogIT.DTO.UserResponseDTO;
import LogITBackend.LogIT.domain.Users;

public interface UserCommandService {
    Users signUp(UserRequestDTO.SignUpRequestDTO request);
    UserResponseDTO.UserSignInResultDTO signIn(UserRequestDTO.SignInRequestDTO request);
}
