package LogITBackend.LogIT.config.security;

import LogITBackend.LogIT.config.security.oauth.CustomAuthExceptionHandler;
import LogITBackend.LogIT.config.security.oauth.CustomOAuth2SuccessHandler;
import LogITBackend.LogIT.config.security.oauth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final CustomAuthExceptionHandler customAuthExceptionHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) // CORS 설정
                .csrf(AbstractHttpConfigurer::disable) // 추가해주어야함.
                // 폼 로그인 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                // HTTP Basic 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 Stateless로 설정
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/**").permitAll())
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler(customOAuth2SuccessHandler)
                        .failureHandler(customAuthExceptionHandler)
                );
        return http.build();
    }
}
