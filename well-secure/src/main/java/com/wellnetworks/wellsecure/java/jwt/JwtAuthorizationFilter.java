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
// 요청 헤더에서 'Authorization' (또는 다른 사용자 정의 헤더) 값을 가져온다.
        String header = req.getHeader(securityProperties.getHeaderString());

        // 헤더가 없거나 JWT 토큰 형식이 아닌 경우 (예: 토큰이 'Bearer '로 시작하지 않음),
        // 필터 체인을 계속 진행하여 다음 필터 또는 리소스에 요청을 전달한다.
        if (header == null || !header.startsWith(securityProperties.getTokenPrefix())) {
            chain.doFilter(req, res);
            return;
        }
        // 디버깅을 위해 헤더의 값을 콘솔에 출력한다. 실제 프로덕션 코드에서는 보안을 위해 제거할 것.
        System.out.println("header : " + header);

        // TokenProvider를 사용하여 JWT 토큰을 해석하고 인증 객체를 얻는다.
        // 이 메서드는 토큰의 유효성을 검증하고, 유효한 경우에만 인증 객체를 반환한다.
        var authentication = tokenProvider.getAuthentication(header);

        // 인증 객체가 null이 아니면, 즉 토큰이 유효하면,
        // 인증 객체를 SecurityContext에 설정하여, 현재 사용자가 인증되었음을 시스템에 알린다.
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
// 요청을 필터 체인의 다음 단계로 넘긴다.
        chain.doFilter(req, res);
    }
}
