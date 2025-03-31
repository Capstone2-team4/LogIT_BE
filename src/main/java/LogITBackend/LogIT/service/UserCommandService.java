package LogITBackend.LogIT.service;

import LogITBackend.LogIT.web.dto.UserRequestDTO;
import LogITBackend.LogIT.domain.Users;

public interface UserCommandService {
    Users signUp(UserRequestDTO.SignUpRequestDTO request);
}
