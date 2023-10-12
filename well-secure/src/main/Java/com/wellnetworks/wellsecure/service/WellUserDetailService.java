package com.wellnetworks.wellsecure.service;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

// 스프링 컴포넌트로 지정하기
@Component
// 지정된 패키지에 대한 컴포넌트 스캔
@ComponentScan("com.wellnetworks.wellcore.java.repository")
public class WellUserDetailService implements UserDetailsService {
    // employeeUser의 레포지토리
    private final WellEmployeeUserRepository wellEmployeeUserRepository;

    // 의존성 주입을 위한 생성자
    public WellUserDetailService(WellEmployeeUserRepository wellEmployeeUserRepository) {
        this.wellEmployeeUserRepository = wellEmployeeUserRepository;
    }

    // loadUserByUsername 메서드 오버라이드하기
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // userID로 사용자 검색하기
        Optional<WellEmployeeUserEntity> userOptional = wellEmployeeUserRepository.findByemployeeIdentification(username);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("The username " + username + " doesn't exist");
        }
        WellEmployeeUserEntity userEntity = userOptional.get();

        // UserDetails 객체 반환하기
        return new User(userEntity.getEmployeeIdx(), userEntity.getEmployeeUserPwd(), userEntity.getAuthorities());
    }
}
