package com.balancegame.balancegameproject.config;

import com.balancegame.balancegameproject.dto.UserinfoDTO;
import com.balancegame.balancegameproject.jwt.JwtService;
import com.balancegame.balancegameproject.service.MainService;
import com.balancegame.balancegameproject.service.OAuth2UserService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private OAuth2UserService oAuth2UserService;
    private MainService mainService;
    @Value("${kakao.CilentId}") // 변수 파일에 등록된 java.file.test 값 가져오기
    String kakaoClientId;

    @Autowired
    SecurityConfig(OAuth2UserService oAuth2UserService, MainService mainService) {
        this.oAuth2UserService = oAuth2UserService;
        this.mainService = mainService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrfConfig) ->
                csrfConfig.disable());
        http.authorizeHttpRequests(config -> config.anyRequest().permitAll());
        http.logout(logout->logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("http://localhost:3000")
                .deleteCookies("token")
                .invalidateHttpSession(true)
                .permitAll()
        );
        http.oauth2Login(oauth2Configurer -> oauth2Configurer
                .successHandler(successHandler())
                .userInfoEndpoint((userInfoEndpoint)->
                        userInfoEndpoint.userService(oAuth2UserService)));
        return http.build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = new ArrayList<>();
        registrations.add(getClientRegistration());
        return new InMemoryClientRegistrationRepository(registrations);
    }
    private ClientRegistration getClientRegistration() {
        return ClientRegistration.withRegistrationId("kakao")
                .clientId(kakaoClientId)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationUri("https://kauth.kakao.com/oauth/authorize")
                .tokenUri("https://kauth.kakao.com/oauth/token")
                .userInfoUri("https://kapi.kakao.com/v2/user/me")
                .redirectUri("http://localhost:8080/login/oauth2/code/kakao")
                .userNameAttributeName("id")
                .clientName("Kakao Client")
                .build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return ((request, response, authentication) -> {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
            Map<String, Object> kakaoAccount = (Map<String, java.lang.Object>) defaultOAuth2User.getAttributes().get("kakao_account");
            Map<String, Object> properties = (Map<String, java.lang.Object>) defaultOAuth2User.getAttributes().get("properties");
            String nickname = properties.get("nickname").toString();
            String email = kakaoAccount.get("email").toString();
            UserinfoDTO userinfoDTO = mainService.userLogin(email,nickname);
            JwtService jwtService = new JwtService();

            // 쿠키 생성 및 설정
            Cookie cookie = new Cookie("token", jwtService.createToken(userinfoDTO.getId().toString(),3600000)); // "jwtToken"은 쿠키의 이름입니다.
            cookie.setMaxAge(3600);
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect("http://localhost:3000");

        });
    }
}

