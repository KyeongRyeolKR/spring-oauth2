package com.example.springoauth2.service;

import com.example.springoauth2.dto.CustomOAuth2User;
import com.example.springoauth2.dto.GoogleResponse;
import com.example.springoauth2.dto.NaverResponse;
import com.example.springoauth2.dto.OAuth2Response;
import com.example.springoauth2.entity.User;
import com.example.springoauth2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User={}", oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;
        if(registrationId.equals("naver")) {
            // 네이버
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        } else if(registrationId.equals("google")) {
            // 구글
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();
        User findUser = userRepository.findByUsername(username);
        String role = "ROLE_USER";
        if(findUser == null) { // 첫 로그인(회원가입)
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(oAuth2Response.getEmail());
            newUser.setRole(role);

            userRepository.save(newUser);
        } else { // 이미 회원가입 된 사용자
            role = findUser.getRole();
            findUser.setEmail(oAuth2Response.getEmail()); // 사용자 이메일이 바뀌었을수도 있기 떄문에 업데이트 해줌
            userRepository.save(findUser);
        }

        return new CustomOAuth2User(oAuth2Response, role);
    }
}
