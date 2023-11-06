package com.wellnetworks.wellsecure.java.service;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import org.apache.logging.log4j.Logger;
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
    private final WellEmployeeUserRepository employeeUserRepository;  // 사용자 정보를 조회하는 레포지토리


    // WellUserDetailService 클래스의 생성자
    // @param employeeUserRepository 사용자 정보를 조회하는 레포지토리
    public WellUserDetailService(WellEmployeeUserRepository employeeUserRepository) {
        this.employeeUserRepository = employeeUserRepository;
    }



    /**
     * username을 기반으로 사용자의 세부 정보를 조회하는 메서드
     *
     * @param username 사용자 이름
     * @return UserDetails 사용자의 세부 정보를 포함하는 객체
     * @throws UsernameNotFoundException 사용자 이름을 찾을 수 없을 때 발생하는 예외
     */
    //사원 로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 사용자 이름(username)을 기반으로 해당 사용자의 정보를 데이터베이스에서 조회
        var userEntity = employeeUserRepository.findByEmployeeIdentification(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자 이름 " + username + "을 찾을 수 없습니다."));

        // 임시 비밀번호가 존재하고 사용해야 하는지 확인
        String effectivePassword = userEntity.getIsPasswordResetRequired() ?
                userEntity.getTmpPwd() : userEntity.getEmployeeUserPwd();

        // 비밀번호 로그 출력 (보안 상의 이유로 실제 비밀번호를 로그에 출력하는 것은 권장되지 않습니다.)
        // logger.info("Effective password: {}", effectivePassword);
        // 결정된 실제 사용할 비밀번호로 UserDetails 객체를 생성
        return new EmployeeUserDetails(
                userEntity.getEmployeeIdentification(),
                effectivePassword,
                userEntity.getAuthorities()
        );
    }




    }
