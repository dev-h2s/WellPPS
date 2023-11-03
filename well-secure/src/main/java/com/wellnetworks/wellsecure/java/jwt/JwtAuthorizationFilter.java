package com.wellnetworks.wellsecure.java.jwt;


import com.wellnetworks.wellsecure.java.config.SecurityProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

/**
 * JWT를 활용한 사용자 인증 정보 검증 필터.
 * 요청에 포함된 JWT 토큰을 검증하고, 유효한 경우 해당 사용자의 인증 정보를 Security Context에 설정한다.
 */
public class JwtAuthorizationFilter  extends BasicAuthenticationFilter {

    private final SecurityProperties securityProperties; // 보안 관련 프로퍼티 정보
    private final TokenProvider tokenProvider; // JWT 토큰을 생성하거나 검증하는 제공자

    /**
     * JwtAuthorizationFilter 생성자.
     */
    public JwtAuthorizationFilter(
            AuthenticationManager authManager,
            SecurityProperties securityProperties,
            TokenProvider tokenProvider
    ) {
        super(authManager);
        this.securityProperties = securityProperties;
        this.tokenProvider = tokenProvider;
    }

    /**
     * 요청에 포함된 JWT 토큰을 검증하는 메서드.
     * 유효한 토큰인 경우 해당 사용자의 인증 정보를 Security Context에 설정한다.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        String header = req.getHeader(securityProperties.getHeaderString());

        // 헤더가 없거나 JWT 토큰 형식이 아닌 경우 필터 체인을 계속 진행한다.
        if (header == null || !header.startsWith(securityProperties.getTokenPrefix())) {
            chain.doFilter(req, res);
            return;
        }

        System.out.println("header : " + header);

        var authentication = tokenProvider.getAuthentication(header);

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(req, res);
    }
}
