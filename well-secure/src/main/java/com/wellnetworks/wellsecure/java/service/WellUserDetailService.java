package com.wellnetworks.wellsecure.java.service;

import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerUserRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * WellUserDetailService 클래스는 사용자의 세부 정보를 조회하는 서비스
 * Spring Security에서 사용자 인증을 위해 UserDetailsService 인터페이스를 구현
 */
@Component  // 스프링의 빈으로 등록하기 위한 어노테이션
@ComponentScan("com.wellnetworks.wellcore.repository")  // 지정된 패키지에서 스프링 빈을 검색하는 어노테이션
public class WellUserDetailService implements UserDetailsService {
    private final WellEmployeeUserRepository employeeUserRepository;  // 사용자 정보를 조회하는 레포지토리
    private final WellPartnerUserRepository partnerUserRepository;

    // WellUserDetailService 클래스의 생성자
    // @param employeeUserRepository 사용자 정보를 조회하는 레포지토리
    public WellUserDetailService(WellEmployeeUserRepository employeeUserRepository, WellPartnerUserRepository partnerUserRepository) {
        this.employeeUserRepository = employeeUserRepository;
        this.partnerUserRepository = partnerUserRepository;
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

        // 먼저 사원 저장소에서 사용자 조회
        Optional<UserDetails> userDetails = employeeUserRepository.findByEmployeeIdentification(username)
                .map(user -> {
                    String effectivePassword = user.getIsPasswordResetRequired() ?
                            user.getTmpPwd() : user.getEmployeeUserPwd();
                    return new EmployeeUserDetails(
                            user.getEmployeeIdentification(),
                            effectivePassword,
                            user.getIsFirstLogin(),
                            user.getAuthorities());

                });

        // 사원이 존재하면 반환, 없으면 파트너 저장소에서 조회
        return userDetails.orElseGet(() -> partnerUserRepository.findByPartnerIdentification(username)
                .map(partner -> {
                    String effectivePassword = partner.getIsPasswordResetRequired() ?
                            partner.getTmpPwd() : partner.getPartnerUserPwd();
                    return new PartnerUserDetails(
                            partner.getPartnerIdentification(),
                            effectivePassword,
                            partner.getIsFirstLogin(),
                            partner.getAuthorities());
                }).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username)));
    }




    }
