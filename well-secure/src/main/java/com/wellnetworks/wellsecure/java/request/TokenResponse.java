package com.wellnetworks.wellsecure.java.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    // 생성자, getter, setter 생략

    public TokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }


}

