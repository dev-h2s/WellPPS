package com.wellnetworks.wellcore.java.service.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    // 생성자, getter, setter 생략
}

