package com.wellnetworks.wellsecure.java.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
// com.wellnetworks.wellsecure.config 패키지 내의 SecurityProperties 클래스
// 이 클래스는 JSON Web Token(JWT) 보안 설정을 위한 구성 속성을 보유
// @ConfigurationProperties 어노테이션을 사용하여 외부 구성 파일(예: application.properties 또는 application.yml)
// 에서 설정 값을 자동으로 바인딩
// @Validated 어노테이션을 사용하여 이 클래스의 필드 값들이 제약 조건을 충족하는지 검증

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Component
@ConfigurationProperties(prefix = "jwt-security")  // 'jwt-security' 접두사를 가진 설정 값을 이 클래스의 필드와 매핑
@Validated  // 이 클래스의 필드 값들이 제약 조건을 충족하는지 검증
public class SecurityProperties {

    @NotBlank  // 이 필드는 빈 문자열이거나 null이 아니어야함
    @Size(min = 64)  // 이 필드의 길이는 최소 64글자여야 함
    private String secret = "vgqwUvZ_XfrsWNGtxUse_crb7@jx4F6KXbEhqNM2mPrAaRbZ6XvA8QWJYtkJzUyP";

    @Positive  // 이 필드는 양수여야 함
    private int expirationTime = 31;  // 단위: 일(days)

    @Positive  // 이 필드는 양수여야 함 복잡성 정도
    private int strength = 10;

    @Positive
    private int accessTokenExpirationTime = 1;

    @Positive
    private int refreshTokenExpirationTime = 1; // 2주

    private final String tokenPrefix = "Bearer ";
    private final String headerString = "Authorization";



}
