package LogITBackend.LogIT.service;

import LogITBackend.LogIT.converter.UserConverter;
import LogITBackend.LogIT.repository.UserRepository;
import LogITBackend.LogIT.DTO.UserRequestDTO;
import LogITBackend.LogIT.domain.Users;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional // ???
    public Users signUp(UserRequestDTO.SignUpRequestDTO request) {
        Users newUser = UserConverter.toUsers(request);
        newUser.encodePassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(newUser);
    }
}
