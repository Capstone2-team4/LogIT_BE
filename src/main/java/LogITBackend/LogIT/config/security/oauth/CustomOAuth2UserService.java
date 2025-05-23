package LogITBackend.LogIT.config.security.oauth;

import LogITBackend.LogIT.DTO.UserRequestDTO;
import LogITBackend.LogIT.converter.UserConverter;
import LogITBackend.LogIT.domain.Users;
import LogITBackend.LogIT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    //    깃허브로 부터 받은 userRequest 데이터에 대한 후처리 되는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
//        log.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
//        log.info("getAccessToken: {}", userRequest.getAccessToken());
//        log.info("getAttributes: {}", super.loadUser(userRequest).getAttributes());
//        System.out.println("userRequest:"+userRequest);
//        System.out.println("getClientRegistraion:"+userRequest.getClientRegistration());  //client에 대한 정보들이 받아짐
//        System.out.println("getAccessToken:"+userRequest.getAccessToken().getTokenValue());
//        System.out.println("getAttributes:"+super.loadUser(userRequest).getAttributes()); //유저 정보를 받아옴

        OAuth2User oAuth2User = super.loadUser(userRequest); // 실제 사용자 정보 요청

//        // 사용자 정보 map
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        // 정보 추출
//        String providerId = String.valueOf(attributes.get("id"));
//        String nickname = (String) attributes.get("login");
//
//        Users existUser = userRepository.findByProviderId(providerId).orElse(null);
//
//        if (existUser == null) {
//            // user dto 생성해서 user저장
//            Users newUser = UserConverter.githubDatatoUsers(
//                    UserRequestDTO.GithubSignUpRequestDTO.builder()
//                            .providerId(providerId)
//                            .nickname(nickname)
//                            .githubAccessToken(userRequest.getAccessToken().getTokenValue())
//                            .build()
//            );
//            userRepository.save(newUser);
//        }

        return oAuth2User;
    }
}
