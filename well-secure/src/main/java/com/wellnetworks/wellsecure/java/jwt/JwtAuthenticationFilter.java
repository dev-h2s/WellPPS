package com.wellnetworks.wellsecure.java.jwt;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellnetworks.wellsecure.java.config.CookieUtil;
import com.wellnetworks.wellsecure.java.config.SecurityProperties;
import com.wellnetworks.wellsecure.java.request.UserLoginReq;
import com.wellnetworks.wellsecure.java.service.EmployeeUserDetails;
import com.wellnetworks.wellsecure.java.service.PartnerUserDetails;
import com.wellnetworks.wellsecure.java.service.RefreshTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * JWT를 활용한 사용자 인증 필터.
 * 사용자 로그인 요청을 받아 인증을 시도하고, 성공한 경우 JWT 토큰을 생성하고 응답 헤더에 추가한다.
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authManager; // Spring Security의 인증 매니저
    private final SecurityProperties securityProperties; // 보안 관련 프로퍼티 정보
    private final TokenProvider tokenProvider; // JWT 토큰을 생성하거나 검증하는 제공자

    private final RefreshTokenService refreshTokenService;


    /**
     * 생성자. 필요한 컴포넌트들을 주입받는다.
     */
    public JwtAuthenticationFilter(
            AuthenticationManager authManager,
            SecurityProperties securityProperties,
            TokenProvider tokenProvider,
            RefreshTokenService refreshTokenService) {
        this.authManager = authManager;
        this.securityProperties = securityProperties;
        this.tokenProvider = tokenProvider;
        this.refreshTokenService = refreshTokenService;
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

            // 로그: 인증 시도 전
            System.out.println("attemptAuthentication 호출 - 인증 시도 시작");

            // 추출된 사용자 이름과 비밀번호를 가지고 인증을 시도한다.
            Authentication auth = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );

            // 로그: 인증 시도 후
            System.out.println("attemptAuthentication 호출 - 인증 시도 완료");
            return auth;
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
        String accessToken = tokenProvider.createToken(authentication);
//        String refreshToken = tokenProvider.createRefreshToken(authentication);

        // Authentication 객체에서 UserDetails 객체를 추출
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String refreshToken;
        int maxAge = 604800; // 7일(초)
        // 쿠키에 accessToken 설정
        CookieUtil.setAccessTokenCookie(res, accessToken, maxAge);
        // userDetails 인스턴스의 타입에 따라 적절한 리프레쉬 토큰 처리 로직을 호출
        if (userDetails instanceof EmployeeUserDetails) {
            // EmployeeUserDetails의 경우
            refreshToken = refreshTokenService.createAndPersistRefreshToken((EmployeeUserDetails) userDetails);
        } else if (userDetails instanceof PartnerUserDetails) {
            // PartnerUserDetails의 경우
            refreshToken = refreshTokenService.createAndPersistRefreshToken((PartnerUserDetails) userDetails);
        } else {
            // 예외 처리: 알 수 없는 사용자 유형
            throw new AuthenticationServiceException("알수 없는 사용자입니다.");
        }

        // 응답 헤더에 토큰을 추가한다.
        res.addHeader(securityProperties.getHeaderString(), securityProperties.getTokenPrefix() + accessToken);
        // 최종 응답 객체를 생성
        Map<String, Object> responseMap = new LinkedHashMap<>(); // 순서 보장
        responseMap.put("message", "로그인 성공");
        Map<String, String> tokenMap = new LinkedHashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);
        responseMap.put("data", tokenMap);


        // 로그: 인증 성공
        System.out.println("successfulAuthentication 호출 - 인증 성공");
        // JSON으로 변환하여 응답
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(new ObjectMapper().writeValueAsString(responseMap));
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {


        // 인증 실패 시 응답에 포함될 데이터를 담을 맵을 생성
        Map<String, Object> responseData = new HashMap<>();
        // 현재 시간을 ISO 8601 형식으로 설정합니다.
        responseData.put("timestamp", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date()));
        // HTTP 응답 상태 코드로 '401 Unauthorized'를 설정
        responseData.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        // 오류 메시지로 'Unauthorized'를 설정
        responseData.put("error", "Unauthorized");
        // 요청된 URI를 응답에 포함
        responseData.put("path", request.getRequestURI());

        // 인증 실패의 구체적 원인에 따라 오류 메시지를 설정
        String errorMessage;
        if (failed instanceof UsernameNotFoundException) {
            errorMessage = "존재하지 않는 아이디 입니다."; // 사용자를 찾을 수 없을 때의 오류 메시지
        } else if (failed instanceof BadCredentialsException) {
            errorMessage = "비밀번호가 일치하지 않습니다."; // 비밀번호가 틀렸을 때의 오류 메시지
        } else {
            errorMessage = "인증에 실패했습니다."; // 그 외 다른 인증 오류에 대한 일반적인 메시지
        }
        responseData.put("message", errorMessage);

        // HTTP 응답 상태 코드를 '401 Unauthorized'로 설정
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 응답의 Content-Type을 'application/json'으로 설정하고, 문자 인코딩은 'UTF-8'로 설정
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // JSON 형태로 변환하여 응답 데이터를 작성하고 클라이언트에게 보낸다
        new ObjectMapper().writeValue(response.getWriter(), responseData);
    }


}
