package com.wellnetworks.secure.java.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellnetworks.secure.java.config.SecurityProperties;
import com.wellnetworks.secure.java.request.UserLoginReq;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.core.AuthenticationException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * JWT를 활용한 사용자 인증 필터.
 * 사용자 로그인 요청을 받아 인증을 시도하고, 성공한 경우 JWT 토큰을 생성하고 응답 헤더에 추가한다.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authManager; // Spring Security의 인증 매니저
    private final SecurityProperties securityProperties; // 보안 관련 프로퍼티 정보
    private final TokenProvider tokenProvider; // JWT 토큰을 생성하거나 검증하는 제공자

    /**
     * 생성자. 필요한 컴포넌트들을 주입받는다.
     */
    public JwtAuthenticationFilter(
            AuthenticationManager authManager,
            SecurityProperties securityProperties,
            TokenProvider tokenProvider
    ) {
        this.authManager = authManager;
        this.securityProperties = securityProperties;
        this.tokenProvider = tokenProvider;
    }

    /**
     * 사용자의 로그인 요청을 처리하여 인증을 시도한다.
     * 요청 본문에서 사용자 이름과 비밀번호를 추출하고, 그 정보를 가지고 인증 매니저를 통해 인증을 시도한다.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException {
        try {
            // JSON 요청 본문을 Java 객체로 변환하기 위한 ObjectMapper
            ObjectMapper mapper = new ObjectMapper();
            // 요청 본문에서 사용자 로그인 정보를 가져온다.
            UserLoginReq creds = mapper.readValue(req.getInputStream(), UserLoginReq.class);

            // 추출된 사용자 이름과 비밀번호를 가지고 인증을 시도한다.
            return authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    /**
     * 인증이 성공하면 호출되는 메서드.
     * 성공적으로 인증된 사용자에 대해 JWT 토큰을 생성하고 응답 헤더에 추가한다.
     */
    @Override
    public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                         FilterChain chain, Authentication authentication)
            throws IOException, ServletException {
        // 인증된 사용자의 정보를 기반으로 JWT 토큰을 생성한다.
        String token = tokenProvider.createToken(authentication);
        // 응답 헤더에 토큰을 추가한다.
        res.addHeader(securityProperties.getHeaderString(), securityProperties.getTokenPrefix() + token);
    }



}
