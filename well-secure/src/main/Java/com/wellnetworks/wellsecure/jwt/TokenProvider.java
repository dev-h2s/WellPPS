package com.wellnetworks.wellsecure.jwt;

import com.wellnetworks.wellsecure.config.SecurityPropertieskt;
import com.wellnetworks.wellsecure.service.WellUserDetailServicekt;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class TokenProvider {
    private final SecurityPropertieskt securityProperties;
    private final WellUserDetailServicekt wellUserDetailService;
    private Key key;
    private Date tokenValidity;

    // 생성자 주입을 사용하여 필요한 의존성을 주입합니다.
    public TokenProvider(SecurityPropertieskt securityProperties, WellUserDetailServicekt wellUserDetailService) {
        this.securityProperties = securityProperties;
        this.wellUserDetailService = wellUserDetailService;
    }

    // 특정 날짜에 일자를 추가하는 도우미 메서드
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

    // 주어진 인증을 기반으로 토큰을 생성하는 메서드
    public String createToken(Authentication authentication) {
        List<String> authClaims = new ArrayList<>();
        if (authentication.getAuthorities() != null) {
            authentication.getAuthorities().forEach(claim -> authClaims.add(claim.toString()));
        }
        return null;
    }

}

