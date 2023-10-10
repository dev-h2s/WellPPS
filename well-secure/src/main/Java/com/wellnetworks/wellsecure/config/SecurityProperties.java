package com.wellnetworks.wellsecure.config;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

// 프로퍼티 설정 파일에서 "jwt-security" 접두사를 사용하는 설정을 바인딩


// 유효성 검사를 위한 어노테이션
@Validated
@Component
@ConfigurationProperties(prefix = "jwt-security")
public class SecurityProperties {
    // JWT 비밀 키. 최소 64 문자여야 합니다.
    @NotBlank // 값이 비어 있지 않아야 함을 나타냄
    @Size(min = 64)
    private String secret = "vgqwUvZ_XfrsWNGtxUse_crb7@jx4F6KXbEhqNM2mPrAaRbZ6XvA8QWJYtkJzUyP";

    // 토큰의 만료 기간. 기본값은 31일
    @Positive // 값이 양수임을 나타냄
    private int expirationTime = 31;  // in Days

    // 비밀번호 해싱 강도. 기본값은 10
    @Positive
    private int strength = 10;

    // 토큰 접두사
    private final String tokenPrefix = "Bearer ";
    // HTTP 헤더에서 사용될 문자열
    private final String headerString = "Authorization";

    // secret의 getter
    public String getSecret() {
        return secret;
    }

    // secret의 setter
    public void setSecret(String secret) {
        this.secret = secret;
    }

    // expirationTime의 getter
    public int getExpirationTime() {
        return expirationTime;
    }

    // expirationTime의 setter
    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    // strength의 getter
    public int getStrength() {
        return strength;
    }

    // strength의 setter
    public void setStrength(int strength) {
        this.strength = strength;
    }

    // tokenPrefix의 getter
    public String getTokenPrefix() {
        return tokenPrefix;
    }

    // headerString의 getter
    public String getHeaderString() {
        return headerString;
    }
}
