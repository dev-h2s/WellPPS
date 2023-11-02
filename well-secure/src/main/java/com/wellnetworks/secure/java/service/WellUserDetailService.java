package com.wellnetworks.secure.java.service;

import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import org.apache.catalina.User;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * WellUserDetailService 클래스는 사용자의 세부 정보를 조회하는 서비스
 * Spring Security에서 사용자 인증을 위해 UserDetailsService 인터페이스를 구현
 */
@Component  // 스프링의 빈으로 등록하기 위한 어노테이션
@ComponentScan("com.wellnetworks.wellcore.repository")  // 지정된 패키지에서 스프링 빈을 검색하는 어노테이션
public class WellUserDetailService implements UserDetailsService {
    private final WellEmployeeUserRepository wellUserRepository;  // 사용자 정보를 조회하는 레포지토리

    public WellUserDetailService(WellEmployeeUserRepository wellUserRepository) {
        this.wellUserRepository = wellUserRepository;
    }

    /**
     * username을 기반으로 사용자의 세부 정보를 조회하는 메서드
     * @param username 사용자 이름
     * @return UserDetails 사용자의 세부 정보를 포함하는 객체
     * @throws UsernameNotFoundException 사용자 이름을 찾을 수 없을 때 발생하는 예외
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userEntity = wellUserRepository.findByEmployeeIdentification(username)
                .orElseThrow(() -> new UsernameNotFoundException("The username " + username + " doesn't exist"));

        return new EmployeeUserDetails(
                userEntity.getEmployeeIdentification(),
                userEntity.getEmployeeUserPwd(),
                userEntity.getAuthorities()
        );

}
}
