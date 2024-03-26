package com.example.springoauth2.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.oauth2.client.JdbcOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

/**
 * OAuth2 사용자의 액세스 토큰과 정보를 외부 DB에 저장하기 위한 서비스
 * - 문제) OAuth2AuthorizedClientService를 구현한 JdbcOAuth2AuthorizedClientService는 사용자 정보를 저장할 때 PK 값으로 client_registration_id와 principal_name를 사용한다.
 *        이는 PK 중복으로 이어질 수 있고 중복된 값이 올 경우에는 해당 데이터는 덮어쓰기가 된다.
 *        그러므로 OAuth2AuthorizedClientService를 직접 구현하여 PK 중복 문제를 해결해야 한다!
 */
//@Configuration
public class CustomOAuth2AuthorizedClientService {

    @Bean
    public OAuth2AuthorizedClientService oAuth2AuthorizedClientService(JdbcTemplate jdbcTemplate, ClientRegistrationRepository clientRegistrationRepository) {
        return new JdbcOAuth2AuthorizedClientService(jdbcTemplate, clientRegistrationRepository);
    }
}
