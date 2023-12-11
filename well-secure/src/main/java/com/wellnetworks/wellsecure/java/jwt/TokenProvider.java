package com.wellnetworks.wellsecure.java.jwt;

import com.wellnetworks.wellsecure.java.config.SecurityProperties;
import com.wellnetworks.wellsecure.java.service.WellUserDetailService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;

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
    private static final Logger log = LoggerFactory.getLogger(TokenProvider.class);

    private Date accessTokenValidity; // acess 토큰 인증 만료 기간
    private Date refreshTokenValidity; // refresh 토큰 시간
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

        // 현재 시간으로부터 accessToken 만료 시간을 설정
        this.accessTokenValidity = new Date(System.currentTimeMillis() +
                (long) securityProperties.getAccessTokenExpirationTime() * 2 * 1000);


        // JWT 토큰 생성 및 반환
        return Jwts.builder()
                .setSubject(authentication.getName()) // 사용자 이름 설정
                .claim("auth", authClaims) // 권한 정보 설정
                .setIssuedAt(new Date()) // 토큰 발급 시간 설정
                .setExpiration(tokenValidity) // 토큰 만료 시간 설정
                .signWith(key, SignatureAlgorithm.HS512) // 암호화 알고리즘 및 키로 서명
                .compact();
    }



    // refreshToken 생성 메서드
    public String createRefreshToken(Authentication authentication) {
        // 현재 시간으로부터 refreshToken 만료 시간을 설정
        this.refreshTokenValidity = new Date(System.currentTimeMillis() +
                (long) securityProperties.getRefreshTokenExpirationTime() * 5 * 1000);

        // refreshToken 생성 코드...
        // refreshToken은 사용자의 권한이나 다른 정보 없이, 오직 사용자 이름만 포함하는 것이 일반적
        return Jwts.builder()
                .setSubject(authentication.getName()) // 사용자 이름 설정
                .setExpiration(refreshTokenValidity) // 토큰 만료 시간 설정
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
            log.error("Authentication error: {}", e.getMessage(), e);
            // 스프링 시큐리티의 인증 실패 예외를 던짐
            throw new BadCredentialsException("Invalid token", e);
        }
    }
    // 리프레시 토큰의 유효성을 검사하는 메서드
    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(refreshToken);
            return true;
        } catch (Exception e) {
            log.error("Refresh token validation error: {}", e.getMessage(), e);
            return false;
        }
    }

    // 토큰에서 사용자 이름을 추출하는 메서드
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}
