package com.example.springoauth2.dto;

public interface OAuth2Response {

    String getProvider(); // naver || google

    String getProviderId(); // 사용자 고유 ID

    String getEmail(); // 이메일

    String getName(); // 이름
}
