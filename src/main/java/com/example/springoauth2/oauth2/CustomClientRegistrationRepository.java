package com.example.springoauth2.oauth2;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

//@Configuration
@RequiredArgsConstructor
public class CustomClientRegistrationRepository {

    private final SocialClientRegistration socialClientRegistration;

    /**
     * 인메모리 방식
     * - 보통 서비스 정보들이 많지 않기 때문에 인메모리 방식을 사용해서 저장하더라도 크게 문제가 되지 않는다!
     * - 다른 방식은 JDBC를 사용한 방식이 있다.
     */
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(socialClientRegistration.naverClientRegistration(), socialClientRegistration.googleClientRegistration());
    }
}
