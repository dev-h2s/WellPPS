package com.wellnetworks.wellsecure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class) // 사용자 정의 보안 속성 활성화
public class AppConfiguration {

    // SecurityProperties에 대한 생성자 기반의 의존성 주입
    private final SecurityProperties securityProperties;

    public AppConfiguration(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
    // 비밀번호 인코더를 위한 Bean
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(securityProperties.getStrength());
    }
}
