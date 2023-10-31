package com.wellnetworks.secure.java.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;

/**
 *  클래스는 사용자의 인증을 처리하는 클래스
 * 주로 사용자가 제공한 사용자명과 비밀번호를 검증하고 해당 사용자에게 필요한 권한 정보를 반환
 */
@Component  // 스프링의 빈으로 등록하기 위한 어노테이션
public class AppAuthenticationManager implements AuthenticationManager {
    private final WellUserDetailService wellUserDetailService;  // 사용자의 상세 정보를 가져오는 서비스
    private final BCryptPasswordEncoder bCryptPasswordEncoder;  // 비밀번호 암호화를 위한 클래스

    // 생성자를 통한 의존성 주입
    public AppAuthenticationManager(WellUserDetailService wellUserDetailService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.wellUserDetailService = wellUserDetailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 사용자가 입력한 비밀번호
        String password = authentication.getCredentials().toString();

        // 사용자의 상세 정보를 가져옴
        var user = (org.springframework.security.core.userdetails.User) wellUserDetailService.loadUserByUsername(authentication.getName());

        // 저장된 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 확인
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Bad credentials");  // 비밀번호가 일치하지 않으면 예외 발생
        }

        // 인증 성공 시, 인증 객체 반환
        return new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

}
