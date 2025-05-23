package LogITBackend.LogIT.config.security.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthExceptionHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // 여기에 로그인 실패 후 처리할 내용을 작성하기!
//        response.sendRedirect("/login-failure");
        log.info("Fail!----------------");
        log.info("❌ OAuth2 로그인 실패: {}", exception.getMessage()); // ✅ 여기!
    }
}
