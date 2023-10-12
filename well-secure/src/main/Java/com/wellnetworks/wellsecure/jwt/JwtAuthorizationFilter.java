package com.wellnetworks.wellsecure.jwt;

import com.wellnetworks.wellsecure.config.SecurityProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final SecurityProperties securityProperties;
    private final TokenProvider tokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authManager, SecurityProperties securityProperties, TokenProvider tokenProvider) {
        super(authManager);
        this.securityProperties = securityProperties;
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        // HTTP 요청 헤더에서 JWT 토큰을 추출합니다.
        String header = req.getHeader(securityProperties.getHeaderString());

        // JWT 토큰이 존재하지 않거나 토큰 접두사로 시작하지 않는 경우 필터 체인을 계속 진행합니다.
        if (header == null || !header.startsWith(securityProperties.getTokenPrefix())) {
            chain.doFilter(req, res);
            return;
        }

        // 디버깅을 위해 헤더 내용을 콘솔에 출력
        System.out.println("header : " + header);

        // TokenProvider를 사용하여 헤더의 JWT 토큰에서 인증 객체를 가져옵니다.
        Authentication authentication = tokenProvider.getAuthentication(header);
        if (authentication != null) {
            // 인증 객체를 현재 보안 컨텍스트에 설정합니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 필터 체인을 계속 진행합니다.
        chain.doFilter(req, res);
    }
}
