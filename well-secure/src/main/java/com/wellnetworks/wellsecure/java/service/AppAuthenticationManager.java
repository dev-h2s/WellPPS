package com.wellnetworks.wellsecure.java.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;

/**
 * AppAuthenticationManager 클래스는 스프링 시큐리티에서 정의된 AuthenticationManager 인터페이스의 구현체이다.
 * 사용자 인증을 담당하며, 사용자가 제공한 사용자명(username)과 비밀번호(password)를 검증한다.
 * 사용자명과 비밀번호가 유효한 경우, 해당 사용자의 권한과 상세 정보를 포함하는 Authentication 객체를 반환한다.
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

        String username = authentication.getName(); // 인증 요청에서 제공된 사용자명

        String password = authentication.getCredentials().toString(); // 사용자가 입력한 비밀번호

        UserDetails userDetails = wellUserDetailService.loadUserByUsername(username);
        // 제공된 비밀번호와 저장된 비밀번호를 비교한다.
        if (!bCryptPasswordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("비밀번호가 일치하지 않습니다");
        }


        // 인증 성공 시, 인증 객체에 EmployeeUserDetails를 포함하여 반환
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
