package com.wellnetworks.wellsecure.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellnetworks.wellsecure.config.SecurityProperties;
import com.wellnetworks.wellsecure.config.SecurityPropertieskt;
import com.wellnetworks.wellsecure.request.UserLoginReq;
import com.wellnetworks.wellsecure.service.AppAuthenticationManager;
import com.wellnetworks.wellsecure.service.AppAuthenticationManagerkt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authManager;
    private final SecurityProperties securityProperties;
    private final TokenProvider tokenProvider;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
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

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        String token = tokenProvider.createToken(authentication);
        res.addHeader(securityProperties.getHeaderString(), securityProperties.getTokenPrefix() + token);
    }
}
