package LogITBackend.LogIT.config.security.oauth;

import LogITBackend.LogIT.domain.Users;
import LogITBackend.LogIT.jwt.JwtUtils;
import LogITBackend.LogIT.repository.UserRepository;
import LogITBackend.LogIT.service.UserCommandService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Value("${jwt.REDIRECT_URI}")
    private String redirectUri; // 프론트엔드로 Jwt 토큰을 리다이렉트할 URI
    @Value("${jwt.ACCESS_EXP_TIME}")
    private int accessExpTime;
    @Value("${jwt.REFRESH_EXP_TIME}")
    private int refreshExpTime;
    private final UserRepository userRepository;
    private final UserCommandService userCommandService;
    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 여기에 로그인 성공 후 처리할 내용을 작성하기!
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        log.info("Suceess!---------------------");

        // 프론트엔드에 보내줄 accessToken 및 refreshToken생성
        Users getUser = userRepository.findByProviderId(oAuth2User.getName()).orElseThrow(null);
        String accessToken = userCommandService.generateAccessToken(getUser.getId(), accessExpTime);
        String key = "users:" + getUser.getId().toString();
        String refreshToken = userCommandService.generateAndSaveRefreshToken(key, refreshExpTime);

        // db에 인가처리할 accessToken저장 (개발자 테스트용)
        getUser.updateAccessToken(accessToken);

        // 프론트엔드에 accessToken과 refreshToken를 redirect url로 보내주기
        // ✅ refreshToken 쿠키에 저장
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(7 * 24 * 60 * 60 * 2); // 14일
        response.addCookie(cookie);

        // ✅ accessToken 쿼리 파라미터로 전달
        String redirectUrl = UriComponentsBuilder
                .fromUriString(redirectUri)
                .queryParam("accessToken", accessToken)
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);
    }

//    private boolean isUser(DefaultOAuth2User oAuth2User) {
//        return oAuth2User.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
//    }
}

