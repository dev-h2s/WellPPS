package com.wellnetworks.wellsecure.java.service;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.dto.member.ChangePasswordRequest;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerUserRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
    private PasswordEncoder passwordEncoder;

    // WellUserDetailService 클래스의 생성자
    // @param employeeUserRepository 사용자 정보를 조회하는 레포지토리
    public WellUserDetailService(WellEmployeeUserRepository employeeUserRepository, WellPartnerUserRepository partnerUserRepository, PasswordEncoder passwordEncoder) {
        this.employeeUserRepository = employeeUserRepository;
        this.partnerUserRepository = partnerUserRepository;
        this.passwordEncoder = passwordEncoder;
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

        // 먼저 사원 저장소에서 사용자를 조회합니다.
        WellEmployeeUserEntity employee = employeeUserRepository.findByEmployeeIdentification(username)
                .orElse(null);

        if (employee != null) {
            String effectivePassword = employee.getIsPasswordResetRequired() ?
                    employee.getTmpPwd() : employee.getEmployeeUserPwd();
            // EmployeeUserDetails 객체를 생성하고, 조회된 employee 객체를 포함합니다.
            return new EmployeeUserDetails(
                    employee.getEmployeeIdentification(),
                    effectivePassword,
                    employee.getIsFirstLogin(),
                    employee.getAuthorities(),
                    employee
            );
        }

        // 사원이 없으면 파트너 저장소에서 사용자를 조회합니다.
        WellPartnerUserEntity partner = partnerUserRepository.findByPartnerIdentification(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        String effectivePassword = partner.getIsPasswordResetRequired() ?
                partner.getTmpPwd() : partner.getPartnerUserPwd();
        // PartnerUserDetails 객체를 생성하고, 조회된 partner 객체를 포함합니다.
        return new PartnerUserDetails(
                partner.getPartnerIdentification(),
                effectivePassword,
                partner.getIsFirstLogin(),
                partner.getAuthorities(),
                partner
        );
    }



//패스워드 변경
    @Transactional  // 데이터베이스 변경 사항을 하나의 트랜잭션으로 처리
    public void changePassword(ChangePasswordRequest request) {
        // 새 비밀번호와 확인 비밀번호가 일치하는지 검증
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new IllegalArgumentException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        }

        // 사용자 이름으로 사용자 세부 정보를 불러옴
        UserDetails userDetails = loadUserByUsername(request.getUsername());

        // 사용자가 사원인 경우
        if (userDetails instanceof EmployeeUserDetails) {
            WellEmployeeUserEntity employee = employeeUserRepository.findByEmployeeIdentification(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
            // 비밀번호 변경 메서드 호출
            employee.changePasswordAndInvalidateTempPassword(request.getNewPassword(), passwordEncoder);
            employeeUserRepository.save(employee); // 변경 사항을 데이터베이스에 저장
        }
        // 사용자가 파트너인 경우
        else if (userDetails instanceof PartnerUserDetails) {
            WellPartnerUserEntity partner = partnerUserRepository.findByPartnerIdentification(request.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
            // 비밀번호 변경 메서드 호출
            partner.changePasswordAndInvalidateTempPassword(request.getNewPassword(), passwordEncoder);
            partnerUserRepository.save(partner); // 변경 사항을 데이터베이스에 저장
        }
        else {
            throw new UsernameNotFoundException("사용자 이름에 해당하는 사용자를 찾을 수 없습니다.: " + request.getUsername());
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // 현재 사용자의 인증 정보를 가져옵니다.
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
    }




    }
