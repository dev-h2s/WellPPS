package com.wellnetworks.wellsecure.config;
import com.wellnetworks.wellsecure.jwt.JwtAuthenticationFilter;
import com.wellnetworks.wellsecure.jwt.JwtAuthorizationFilter;
import com.wellnetworks.wellsecure.jwt.TokenProvider;
import com.wellnetworks.wellsecure.service.AppAuthenticationManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration  // Spring Configuration을 나타내는 어노테이션 클래스는 빈을 설정하거나 구성에 관한 정보를 포함
@EnableWebSecurity  // Spring Security를 활성화하는 어노테이션
@EnableMethodSecurity   // 메서드 수준의 보안을 활성화

public class SecurityConfig {

    // 필요한 의존성을 주입받기 위한 멤버 변수.
    private final SecurityProperties securityProperties;
    private final AppAuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    // 생성자를 통한 의존성 주입
    public SecurityConfig(SecurityProperties securityProperties,
                          AppAuthenticationManager authenticationManager,
                          TokenProvider tokenProvider) {
        this.securityProperties = securityProperties;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Bean  // Spring 빈으로 등록될 메서드를 나타냄.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()  // CORS 설정을 활성화합니다.
                .and()
                .csrf().disable()  // CSRF 공격 방어 기능을 비활성화합니다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // 세션 관리 전략을 설정합니다. 여기서는 세션을 사용하지 않도록 설정했습니다.
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager, securityProperties, tokenProvider))  // JWT 인증 필터를 추가합니다.
                .addFilter(new JwtAuthorizationFilter(authenticationManager, securityProperties, tokenProvider))  // JWT 인가 필터를 추가합니다.
                .logout()  // 로그아웃 설정을 시작합니다.
                .logoutUrl("/logout")  // 로그아웃 경로를 설정합니다.
                .logoutSuccessUrl("/login")  // 로그아웃 후 리다이렉트될 URL을 설정합니다.
                .invalidateHttpSession(true)  // 로그아웃 시 HTTP 세션을 무효화합니다.
                .deleteCookies("JSESSIONID", "remember-me");  // 로그아웃 시 특정 쿠키를 삭제합니다.

        http.authorizeRequests()  // HTTP 요청에 대한 보안 설정을 시작
                .antMatchers("/init/**").permitAll()
                // ... 여기에 다른 경로 패턴들이 추가됩니다 ...
                .anyRequest().authenticated();  // 그 외의 모든 요청은 인증이 필요하다고 명시합니다.

        return http.build();  // 구성된 HttpSecurity 객체를 반환합니다.
    }

    @Bean  // Spring 빈으로 등록될 메서드를 나타냅니다.
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("*", "*/*"));  // 모든 출처로부터의 요청을 허용합니다.
        // ... 여기에 추가적인 CORS 설정을 추가할 수 있습니다 ...

        source.registerCorsConfiguration("/**", config);  // CORS 설정을 전체 경로에 적용합니다.
        return source;  // 구성된 CORS 소스 객체를 반환합니다.
    }
}