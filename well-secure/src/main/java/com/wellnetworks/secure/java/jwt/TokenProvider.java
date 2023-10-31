package com.wellnetworks.secure.java.jwt;

import com.wellnetworks.secure.java.config.SecurityProperties;
import com.wellnetworks.secure.java.service.WellUserDetailService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// 클래스에 대한 주석: JWT 토큰 생성 및 검증 역할을 하는 클래스
@Component
public class TokenProvider {

    // 보안 관련 설정 정보
    private final SecurityProperties securityProperties;
    // 사용자 정보를 불러오는 서비스
    private final WellUserDetailService wellUserDetailService;
    // JWT 토큰 생성 및 검증에 사용될 키
    private Key key;
    // JWT 토큰의 유효 기간
    private Date tokenValidity;
    // 생성자에서 의존성 주입
    public TokenProvider(SecurityProperties securityProperties, WellUserDetailService wellUserDetailService) {
        this.securityProperties = securityProperties;
        this.wellUserDetailService = wellUserDetailService;
    }

    // 객체 생성 후 초기 설정을 수행하는 메서드
    @PostConstruct
    public void init() {
        // 키 생성
        this.key = Keys.hmacShaKeyFor(securityProperties.getSecret().getBytes());

        // 토큰 유효 기간 설정
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, securityProperties.getExpirationTime());
        this.tokenValidity = calendar.getTime();
    }

    // 사용자 인증 정보를 기반으로 JWT 토큰을 생성하는 메서드
    public String createToken(Authentication authentication) {
        List<String> authClaims = new ArrayList<>();
        // 권한 정보를 리스트에 저장
        authentication.getAuthorities().forEach(claim -> authClaims.add(claim.toString()));

        // JWT 토큰 생성 및 반환
        return Jwts.builder()
                .setSubject(authentication.getName()) // 사용자 이름 설정
                .claim("auth", authClaims) // 권한 정보 설정
                .setExpiration(tokenValidity) // 토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS512) // 암호화 알고리즘 및 키로 서명
                .compact();
    }

    /**
     * JWT 토큰을 기반으로 사용자의 인증 정보를 추출하고 반환하는 메서드.
     *
     * @param token 사용자의 JWT 토큰
     * @return 사용자의 인증 정보를 포함하는 Authentication 객체.
     *         토큰 검증에 실패한 경우 null 반환.
     */
    public Authentication getAuthentication(String token) {
        try {
            // JWT 파서를 사용하여 토큰에서 서명키를 검증하고 클레임을 추출
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(securityProperties.getTokenPrefix(), ""));

            // 추출된 클레임에서 사용자 이름을 가져와서 사용자의 상세 정보를 로드
            var userDetail = wellUserDetailService.loadUserByUsername(claims.getBody().getSubject());

            // 사용자의 상세 정보를 기반으로 인증 정보를 생성
            var principal = new User(userDetail.getUsername(), "", userDetail.getAuthorities());
            return new UsernamePasswordAuthenticationToken(principal, token, userDetail.getAuthorities());
        } catch (Exception e) {
            // 예외가 발생한 경우, 에러 메시지를 출력하고 null 반환.
            System.out.println(e.getMessage());
            return null;
        }
}
}