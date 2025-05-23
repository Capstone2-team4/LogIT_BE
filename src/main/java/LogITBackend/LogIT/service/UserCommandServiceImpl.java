package LogITBackend.LogIT.service;

import LogITBackend.LogIT.DTO.UserResponseDTO;
import LogITBackend.LogIT.apiPayload.code.status.ErrorStatus;
import LogITBackend.LogIT.apiPayload.exception.handler.ExceptionHandler;
import LogITBackend.LogIT.config.security.SecurityConfig;
import LogITBackend.LogIT.config.security.SecurityUtil;
import LogITBackend.LogIT.converter.UserConverter;
import LogITBackend.LogIT.jwt.JwtUtils;
import LogITBackend.LogIT.repository.UserRepository;
import LogITBackend.LogIT.DTO.UserRequestDTO;
import LogITBackend.LogIT.domain.Users;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCommandServiceImpl implements UserCommandService {
    @Value("${jwt.ACCESS_EXP_TIME}")
    private int accessExpTime;
    @Value("${jwt.REFRESH_EXP_TIME}")
    private int refreshExpTime;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional // ???
    public Users signUp(UserRequestDTO.SignUpRequestDTO request) {
        Users newUser = UserConverter.toUsers(request);
        newUser.encodePassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(newUser);
    }

    @Override
    @Transactional // ???
    public UserResponseDTO.UserSignInResultDTO signIn(UserRequestDTO.SignInRequestDTO request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication); // 로그인을 한 후 인증 정보를 사용할 일은 없을 것 같다.

        Users getUser = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new ExceptionHandler(ErrorStatus.USER_NOT_FOUND));
        String key = "users:" + getUser.getId().toString();
        String accessToken = generateAccessToken(getUser.getId(), accessExpTime);
        String refreshToken = generateAndSaveRefreshToken(key, refreshExpTime);

        return UserConverter.toUserSignInResultDTO(getUser, accessToken, refreshToken);
    }

    @Override
    public void register(UserRequestDTO.GithubRegisterRequestDTO request) {
        Users getGithubInfo = userRepository.findByProviderId(request.getProviderId())
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.USER_NOT_FOUND)); // 후에 errorstatus바꾸기
        Long userId = SecurityUtil.getCurrentUserId();
        Users getUser = userRepository.findById(userId)
                .orElseThrow(() -> new ExceptionHandler(ErrorStatus.USER_NOT_FOUND));
        getUser.updateGithubAccessToken(getGithubInfo.getGithubAccesstoken());
        getUser.updateProviderId(getGithubInfo.getProviderId());
        getUser.updateGithubNickname(getGithubInfo.getNickname());
        userRepository.delete(getGithubInfo);
    }

    public String generateAccessToken(Long userId, int accessExpTime) {
        // 인증 완료 후 jwt토큰(accessToken) 생성
        Map<String, Object> valueMap = Map.of(
                "userId", userId // String으로 저장??? 그래서 SecurityUtil에서 Long으로 타입변환 해주나?
        );
        return jwtUtils.generateToken(valueMap, accessExpTime);
    }

    public String generateAndSaveRefreshToken(String key, int refreshExpTime) {
        // 인증 완료 후 jwt토큰(refreshToken) 생성
        String refreshToken = jwtUtils.generateToken(Collections.emptyMap(), refreshExpTime);
        redisTemplate.opsForValue().set(key, refreshToken, refreshExpTime, TimeUnit.MINUTES);
        return refreshToken;
    }
}
