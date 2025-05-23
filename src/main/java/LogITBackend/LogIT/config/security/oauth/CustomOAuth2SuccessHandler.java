package LogITBackend.LogIT.config.security.oauth;

import LogITBackend.LogIT.DTO.UserRequestDTO;
import LogITBackend.LogIT.converter.UserConverter;
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
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

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
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oauthUser = oauthToken.getPrincipal();

        // 클라이언트 정보 (깃허브, 구글 등)
        String registrationId = oauthToken.getAuthorizedClientRegistrationId();

        // accessToken 꺼내기
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                registrationId,
                oauthToken.getName()
        );
        OAuth2AccessToken accessToken = client.getAccessToken();

        // 사용자 정보 Map (GitHub의 경우 login, id, name 등 포함)
        Map<String, Object> attributes = oauthUser.getAttributes();

        // GitHub 기준 예시 (플랫폼마다 다름)
        String providerId = String.valueOf(attributes.get("id"));
        String nickname = (String) attributes.get("login");

        // 여기에 로그인 성공 후 처리할 내용을 작성하기!
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        log.info("Suceess!---------------------");

        Users existUser = userRepository.findByProviderId(providerId).orElse(null);

        // user dto 생성해서 user저장
        if(existUser == null) {
            Users newUser = UserConverter.githubDatatoUsers(
                    UserRequestDTO.GithubSignUpRequestDTO.builder()
                            .providerId(providerId)
                            .nickname(nickname)
                            .githubAccessToken(accessToken.getTokenValue())
                            .build()
            );
            userRepository.save(newUser);
        }

        // ✅ provideId 쿼리 파라미터로 전달
        String redirectUrl = UriComponentsBuilder
                .fromUriString(redirectUri)
                .queryParam("providerId", providerId)
                .build()
                .toUriString();

        response.sendRedirect(redirectUrl);
    }

//    private boolean isUser(DefaultOAuth2User oAuth2User) {
//        return oAuth2User.getAuthorities().stream()
//                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
//    }
}

