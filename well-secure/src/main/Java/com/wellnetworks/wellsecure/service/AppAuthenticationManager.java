package com.wellnetworks.wellsecure.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component //// @Component 어노테이션은 해당 클래스를 Spring 컨테이너에서 관리되는 빈(Bean)으로 등록한다
public class AppAuthenticationManager implements AuthenticationManager  {

    // WellUserDetailService에 대한 참조 변수
    private final WellUserDetailService wellUserDetailService;

    // BCryptPasswordEncoder에 대한 참조 변수
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 생성자를 통한 의존성 주입
    // WellUserDetailService와 BCryptPasswordEncoder를 주입받아 초기화
    public AppAuthenticationManager(WellUserDetailService wellUserDetailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.wellUserDetailService = wellUserDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 입력받은 비밀번호를 가져온다
        String password = authentication.getCredentials().toString();

        // 입력받은 사용자 이름으로 사용자의 세부 정보를 로드
        UserDetails user = wellUserDetailService.loadUserByUsername(authentication.getName());

        // 입력받은 비밀번호와 저장된 비밀번호의 해시를 비교
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials"); // 비밀번호가 일치하지 않으면 예외를 발생
        }

        // 인증된 사용자에 대한 토큰을 생성하고 반환
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());

    }
}
