package com.wellnetworks.wellsecure.java.service;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.domain.refreshtoken.EmployeeRefreshTokenEntity;
import com.wellnetworks.wellcore.java.domain.refreshtoken.PartnerRefreshTokenEntity;
import com.wellnetworks.wellcore.java.repository.member.EmployeeRefreshTokenRepository;
import com.wellnetworks.wellcore.java.repository.member.PartnerRefreshTokenRepository;
import com.wellnetworks.wellsecure.java.config.SecurityProperties;
import com.wellnetworks.wellsecure.java.jwt.TokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@EnableScheduling // 스캐쥴링을 쓰기위함
@RequiredArgsConstructor
public class RefreshTokenService {

    private final EmployeeRefreshTokenRepository employeeRefreshTokenRepository;

    private final PartnerRefreshTokenRepository partnerRefreshTokenRepository;

    private UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;
    private final SecurityProperties securityProperties;

    // 생성자 주입



    // 리프레쉬 토큰 생성 밑 기존 idx있을때 수정하는 메서드
    @Transactional
    public String createAndPersistRefreshToken(UserDetails userDetails) {
        // Authentication 객체를 생성하여 리프레쉬 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), null, userDetails.getAuthorities());
        String newRefreshTokenValue = tokenProvider.createToken(authenticationToken);
        // 리프레쉬 토큰의 만료 날짜를 계산
        Date expiryDate = calculateExpiryDate(securityProperties.getRefreshTokenExpirationTime());

        if (userDetails instanceof EmployeeUserDetails) {
            // EmployeeUserDetails 타입인 경우, 해당 유저의 정보를 가져옴
            WellEmployeeUserEntity employee = ((EmployeeUserDetails) userDetails).getEmployee();
            // 해당 유저에 대한 리프레쉬 토큰 엔티티를 데이터베이스에서 찾거나 생성
            EmployeeRefreshTokenEntity employeeTokenEntity = employeeRefreshTokenRepository
                    .findByEmployeeUser(employee) // 'employee' 객체를 전달
                    .orElseGet(() -> new EmployeeRefreshTokenEntity(employee, newRefreshTokenValue, expiryDate)); // 부분 생성자 사용

            // 토큰 업데이트 또는 새로 생성
            employeeTokenEntity.updateToken(newRefreshTokenValue, expiryDate);
            employeeRefreshTokenRepository.save(employeeTokenEntity); // 레디스
        } else if (userDetails instanceof PartnerUserDetails) {
            WellPartnerUserEntity partner = ((PartnerUserDetails) userDetails).getPartner();
            PartnerRefreshTokenEntity partnerTokenEntity = partnerRefreshTokenRepository
                    .findByPartnerUser(partner)
                    .orElseGet(() -> new PartnerRefreshTokenEntity(partner, newRefreshTokenValue, expiryDate));
            // 토큰 업데이트 또는 새로 생성
            partnerTokenEntity.updateToken(newRefreshTokenValue, expiryDate);
            partnerRefreshTokenRepository.save(partnerTokenEntity); // 레디스

        } else {
            throw new IllegalArgumentException("존재하지 않는 타입의 유저입니다.");
        }

        return newRefreshTokenValue;
    }


    // 리프레쉬 토큰을 사용하여 새로운 액세스 토큰을 생성하는 메서드
    @Transactional
    public String refreshAccessToken(String refreshToken) {
        // refreshToken을 검증하고 해당하는 사용자 이름을 얻는 로직 (가정)
        String username = getUsernameFromRefreshToken(refreshToken);

        // 사용자 상세 정보 로드
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // 새로운 액세스 토큰 생성
        Authentication newAuthentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null, userDetails.getAuthorities());

        return tokenProvider.createToken(newAuthentication);
    }

    private String getUsernameFromRefreshToken(String refreshToken) {
        // 리프레시 토큰이 null이거나 유효하지 않은 경우 예외를 발생시킵니다.
        if (refreshToken == null || !tokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않거나 null인 리프레시 토큰입니다.");
        }
        // TokenProvider를 사용하여 리프레시 토큰에서 사용자 이름을 추출합니다.
        Authentication username = tokenProvider.getAuthentication(refreshToken);
        if (username == null || !username.isAuthenticated()) {
            throw new IllegalArgumentException("리프레시 토큰에서 사용자 이름을 찾을 수 없습니다.");
        }
        return username.getName();
    }


    // 토큰 삭제 메서드
    @Transactional
    public void deleteRefreshToken(UserDetails userDetails) {
        if (userDetails instanceof EmployeeUserDetails) {
            WellEmployeeUserEntity employee = ((EmployeeUserDetails) userDetails).getEmployee();
            System.out.println("사원의 리프레쉬 토큰을 삭제합니다: " + employee.getEmployeeIdx()); // 로그 추가
            employeeRefreshTokenRepository.deleteByEmployeeUser(employee);
        } else if (userDetails instanceof PartnerUserDetails) {
            WellPartnerUserEntity partner = ((PartnerUserDetails) userDetails).getPartner();
            System.out.println("파트너의 리프레쉬 토큰을 삭제합니다: " + partner.getPartnerIdx()); // 로그 추가
            partnerRefreshTokenRepository.deleteByPartnerUser(partner);
        }
        // 로그를 추가하여 실제로 삭제가 일어났는지 확인합니다.
        System.out.println("리프레쉬 토큰이 성공적으로 삭제되었습니다.");
    }


    // 매일 자정에 만료된 리프레쉬 토큰을 삭제하는 스케줄러 메서드
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void purgeExpiredRefreshTokens() {
        employeeRefreshTokenRepository.deleteAllExpiredTokens();
        partnerRefreshTokenRepository.deleteAllExpiredTokens();
    }


    //      리프레쉬 토큰의 만료 시간을 계산하는 메서드.
    private Date calculateExpiryDate(int refreshTokenExpirationTime) {
        Calendar calendar = Calendar.getInstance(); // 현재 시간을 기준으로 Calendar 객체를 생성
        calendar.add(Calendar.MINUTE, refreshTokenExpirationTime); // 현재 시간에 유효 시간을 더함
        return calendar.getTime(); // 새로운 만료 시간을 가진 Date 객체를 반환
    }


}
