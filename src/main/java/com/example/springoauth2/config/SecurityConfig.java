package com.example.springoauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable); // 개발 환경이므로 csrf 비활성화
        http.formLogin(AbstractHttpConfigurer::disable); // 자체 폼 로그인을 사용하지 않으므로 비활성화
        http.httpBasic(AbstractHttpConfigurer::disable); // http basic 방식도 사용하지 않으므로 비활성화

        http.oauth2Login(Customizer.withDefaults()); // oauth2 로그인 방식 사용 -> 추후 설정 변경 예정

        http.authorizeHttpRequests(
                (auth) -> auth
                        .requestMatchers("/", "/oauth2/**", "/login/**").permitAll() // 누구나 접근 가능
                        .anyRequest().authenticated() // 나머지는 전부 인증된 사용자만 접근 가능
        );

        return http.build();
    }
}
