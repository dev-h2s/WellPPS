package com.wellnetworks.wellsecure.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellnetworks.wellsecure.config.SecurityProperties;
import com.wellnetworks.wellsecure.request.UserLoginReq;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.core.AuthenticationException;
import javax.servlet.FilterChain; // 추가된 임포트
import javax.servlet.ServletException; // 추가된 임포트
import java.io.IOException;
import java.util.ArrayList;

//주요 역할: JWT 기반의 사용자 인증을 처리, 사용자인증 정보 추출,토큰 생성 후 인증매니저에게 전달 실제 인증 시도
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final SecurityProperties securityProperties;
    private final TokenProvider tokenProvider;

    /**
     * 생성자를 통한 필요한 객체 초기화
     *
     * @param authManager 인증 관리자
     * @param securityProperties 보안 프로퍼티
     * @param tokenProvider 토큰 제공자
     */
    public JwtAuthenticationFilter(AuthenticationManager authManager, SecurityProperties securityProperties, TokenProvider tokenProvider) {
        this.authManager = authManager;
        this.securityProperties = securityProperties;
        this.tokenProvider = tokenProvider;
    }

    /**
     * 인증 시도
     *
     * @param req 요청 객체
     * @param res 응답 객체
     * @return 인증 객체
     * @throws AuthenticationException 인증 예외
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            // ObjectMapper 객체 생성
            ObjectMapper mapper = new ObjectMapper();

            // HTTP 요청의 입력 스트림에서 UserLoginReq 객체로 변환
            UserLoginReq creds = mapper.readValue(req.getInputStream(), UserLoginReq.class);

            // 인증 객체 생성 및 반환
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
     * 인증 성공 시 행동 정의
     *
     * @param req 요청 객체
     * @param res 응답 객체
     * @param chain 필터 체인
     * @param authentication 인증 객체
     * @throws IOException 입출력 예외
     */
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // 토큰 생성
        String token = tokenProvider.createToken(authentication);
        // 응답 헤더에 토큰 추가
        res.addHeader(securityProperties.getHeaderString(), securityProperties.getTokenPrefix() + token);
    }
}
