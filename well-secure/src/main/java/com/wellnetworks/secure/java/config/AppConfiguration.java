package com.wellnetworks.secure.java.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 이 클래스는 스프링 부트 어플리케이션의 설정을 담당
// 스프링 시큐리티를 사용하여 비밀번호 암호화에 사용되는 BCryptPasswordEncoder 빈을 설정하고,
// 외부 설정 파일에서 설정 값을 가져오기 위해 SecurityProperties 클래스를 사용

    // 이 클래스를 스프링의 설정 클래스로 선언
    @Configuration
// @EnableConfigurationProperties 어노테이션을 사용하여 SecurityProperties 클래스가
// 구성 속성으로 사용되도록 지정
    @EnableConfigurationProperties(SecurityProperties.class)
    public class AppConfiguration {
        // SecurityProperties 타입의 필드를 선언
        // 생성자를 통해 주입
        private final SecurityProperties securityProperties;

        // 생성자를 통해 SecurityProperties 객체를 주입
        // @Autowired 어노테이션은 생략 가능
        // (생성자가 하나만 있고 해당 타입의 빈이 스프링 컨텍스트에 존재하면 자동으로 주입)
        public AppConfiguration(SecurityProperties securityProperties) {
            this.securityProperties = securityProperties;
        }

        // BCryptPasswordEncoder 타입의 빈을 생성하는 메서드입니다.
        // @Bean 어노테이션을 사용하여 이 메서드의 반환값을 스프링 빈으로 등록
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            // SecurityProperties 객체의 strength 속성 값을 사용하여
            // BCryptPasswordEncoder 객체를 생성하고 반환
            return new BCryptPasswordEncoder(securityProperties.getStrength());
        }
    }