package com.wellnetworks.wellsecure.jwt;

import com.wellnetworks.wellsecure.config.SecurityProperties;
import com.wellnetworks.wellsecure.config.SecurityProperties;
import com.wellnetworks.wellsecure.service.WellUserDetailService;
import com.wellnetworks.wellsecure.service.WellUserDetailService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TokenProvider {
    private final SecurityProperties securityProperties;
    private final WellUserDetailService wellUserDetailService;
    private Key key;
    private Date tokenValidity;

    // 생성자 주입을 사용하여 필요한 의존성을 주입합니다.
    public TokenProvider(SecurityProperties securityProperties, WellUserDetailService wellUserDetailService) {
        this.securityProperties = securityProperties;
        this.wellUserDetailService = wellUserDetailService;
    }

    // 특정 날짜를 추가하는 메서드
    private Date add(Date date, int field, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, amount);
        return calendar.getTime();
    }

    // `@PostConstruct`는 의존성 주입 후 초기화를 수행하기 위해 실행해야하는 메서드에 사용됩니다.
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(securityProperties.getSecret().getBytes());
        this.tokenValidity = add(new Date(), Calendar.DAY_OF_MONTH, securityProperties.getExpirationTime());
    }


    // 주어진 인증 객체를 기반으로 JWT 토큰을 생성합니다.
    public String createToken(Authentication authentication) {
        List<String> authClaims = new ArrayList<>();
        authentication.getAuthorities().forEach(claim -> authClaims.add(claim.toString()));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authClaims)
                .setExpiration(tokenValidity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 주어진 토큰을 기반으로 인증 객체를 생성합니다.
    public Authentication getAuthentication(String token) {
        try {
            var claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.replace(securityProperties.getTokenPrefix(), ""));

            var userDetail = wellUserDetailService.loadUserByUsername(claims.getBody().getSubject());
            var principal = new User(userDetail.getUsername(), "", userDetail.getAuthorities());
            return new UsernamePasswordAuthenticationToken(principal, token, userDetail.getAuthorities());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

