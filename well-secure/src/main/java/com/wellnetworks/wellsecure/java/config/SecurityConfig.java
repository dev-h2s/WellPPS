package com.wellnetworks.wellsecure.java.config;

import com.wellnetworks.wellsecure.java.jwt.JwtAuthenticationFilter;
import com.wellnetworks.wellsecure.java.jwt.JwtAuthorizationFilter;
import com.wellnetworks.wellsecure.java.jwt.TokenProvider;
import com.wellnetworks.wellsecure.java.service.AppAuthenticationManager;
//import com.wellnetworks.wellsecure.java.service.CustomLogoutHandler;
import com.wellnetworks.wellsecure.java.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * SecurityConfig 클래스는 스프링 시큐리티의 설정을 담당하는 클래스
 * 이 클래스는 웹 보안을 활성화하고, 메소드 레벨의 보안을 활성화
 * 또한, JWT를 사용한 인증과 인가를 처리하는 필터를 설정
 */
@Configuration  // 이 클래스를 스프링의 설정 클래스로 선언
@EnableWebSecurity  // 웹 보안을 활성화
@EnableMethodSecurity  // 메소드 레벨의 보안을 활성화
@ComponentScan("com.wellnetworks.wellsecure")
public class SecurityConfig {
    private final SecurityProperties securityProperties;
    private final AppAuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

//    @Autowired private CustomLogoutHandler customLogoutHandler;

    public SecurityConfig(SecurityProperties securityProperties, AppAuthenticationManager authenticationManager,
                          TokenProvider tokenProvider,
//                          CustomLogoutHandler customLogoutHandler,
                          RefreshTokenService refreshTokenService) {
        this.securityProperties = securityProperties;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
//        this.customLogoutHandler = customLogoutHandler;
    }
//    @Bean
//    public CustomLogoutHandler customLogoutHandler(RefreshTokenService refreshTokenService) {
//        return new CustomLogoutHandler(refreshTokenService);
//    }

    /**
     * Spring Security의 Filter Chain 설정을 제공합니다.
     */
    @Bean // 이 메서드가 반환하는 객체를 Spring IoC 컨테이너에 빈으로 등록합니다.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LogoutConfigurer<HttpSecurity> httpSecurityLogoutConfigurer = http
                .cors().and()  // CORS 설정 활성화
                .csrf().disable()  // CSRF 방지 기능 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()  // 세션을 사용하지 않도록 설정
                .addFilter(new JwtAuthenticationFilter(authenticationManager, securityProperties, tokenProvider, refreshTokenService))  // JWT 인증 필터 추가
                .addFilter(new JwtAuthorizationFilter(authenticationManager, securityProperties, tokenProvider))  // JWT 권한 확인 필터 추가
                .logout()
                .logoutUrl("/logout")  // 로그아웃 경로 설정
                .logoutSuccessUrl("/loginTest.html")  // 로그아웃 후 리다이렉트할 경로 설정
                .invalidateHttpSession(true)
//                .logoutSuccessHandler((request, response, authentication) -> {
//                    // 로그아웃 시 쿠키 삭제
//                    CookieUtil.clearCookie(request, response, "access_token");
//                    CookieUtil.clearCookie(request, response, "refresh_token");
//                }) -->
                .deleteCookies("JSESSIONID", "access_token", "refresh_token");// 로그아웃 시 삭제할 쿠키 설정


        http.authorizeRequests()
                .requestMatchers("/init/**").permitAll()// "/init/**" 경로는 누구나 접근 가능
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/", "/**", "/init/**").permitAll()
                .requestMatchers("/admin/hr/business/**").permitAll()
                .requestMatchers("/admin/hr/employee/**").permitAll()
                .requestMatchers("/admin/hr/user/**").permitAll()
                .requestMatchers("/file/**").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/logout").permitAll()
                .requestMatchers("/signup").permitAll()
//                .requestMatchers("/**").permitAll() // 나머지 모든 요청은 누구나 접근 가능
                .anyRequest().authenticated();// 그 외 나머지 요청은 인증된 사용자만 접근 가능

        return http.build();
    }

    /**
     * CORS 설정을 위한 CorsConfigurationSource 빈을 생성
     * CORS(Cross-Origin Resource Sharing)는 추가적인 HTTP 헤더를 사용하여,
     * 한 출처에서 실행 중인 웹 페이지가 다른 출처의 선택한 자원과 상호 작용할 수 있는 권한을 부여하도록 브라우저에 알려주는 방법
     * 이 설정을 통해 특정 도메인에서의 접근을 허용하거나, 특정 HTTP 메서드, 헤더에 대한 요청을 허용하도록 설정할 수 있다

     * @return CORS 설정 정보를 담고 있는 CorsConfigurationSource 객체
     */
    @Bean // 이 메서드가 반환하는 객체를 Spring IoC 컨테이너에 빈으로 등록
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();// CORS 설정을 위한 객체 생성
        CorsConfiguration config = new CorsConfiguration();// CORS 설정 객체 생성

        config.setAllowedOrigins(List.of("*", "*/*")); // 모든 도메인에서의 접근 허용
        config.setAllowedMethods(List.of("POST", "PUT", "DELETE", "GET", "OPTIONS", "HEAD")); // 허용할 HTTP 메서드 설정
        config.setAllowedHeaders(List.of("*"));  // 허용할 헤더 설정
        config.setExposedHeaders(List.of("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "Authorization", "Content-Disposition")); // 클라이언트에게 노출할 헤더 설정
        config.setMaxAge(3600L);  // pre-flight 응답의 캐시 시간 설정

        source.registerCorsConfiguration("/**", config);  // 모든 경로에 대한 CORS 설정 적용
        return source;  // CORS 설정 객체 반환
    }

}
