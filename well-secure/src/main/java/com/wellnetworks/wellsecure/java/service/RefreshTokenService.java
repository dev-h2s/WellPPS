package com.wellnetworks.wellsecure.java.service;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.domain.refreshtoken.EmployeeRefreshTokenEntity;
import com.wellnetworks.wellcore.java.domain.refreshtoken.PartnerRefreshTokenEntity;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerUserRepository;
import com.wellnetworks.wellcore.java.repository.member.EmployeeRefreshTokenRepository;
import com.wellnetworks.wellcore.java.repository.member.PartnerRefreshTokenRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import com.wellnetworks.wellsecure.java.config.SecurityProperties;
import com.wellnetworks.wellsecure.java.jwt.TokenProvider;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@EnableScheduling // 스캐쥴링을 쓰기위함
public class RefreshTokenService {
    @Autowired
    private EmployeeRefreshTokenRepository employeeRefreshTokenRepository;

    @Autowired
    private PartnerRefreshTokenRepository partnerRefreshTokenRepository;

    @Autowired
    private WellEmployeeUserRepository wellEmployeeUserRepository;

    @Autowired
    private WellPartnerUserRepository wellPartnerUserRepository;

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private SecurityProperties securityProperties;

    // 생성자 주입
    @Autowired
    public RefreshTokenService(EmployeeRefreshTokenRepository employeeRefreshTokenRepository,
                               PartnerRefreshTokenRepository partnerRefreshTokenRepository,
                               TokenProvider tokenProvider,
                               SecurityProperties securityProperties) {
        this.employeeRefreshTokenRepository = employeeRefreshTokenRepository;
        this.partnerRefreshTokenRepository = partnerRefreshTokenRepository;
        this.tokenProvider = tokenProvider;
        this.securityProperties = securityProperties;
    }

    // 리프레쉬 토큰 생성 밑 기존 idx있을때 수정하는 메서드
    @Transactional
    public String createAndPersistRefreshToken(UserDetails userDetails) {
        // Authentication 객체를 생성하여 리프레쉬 토큰 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), null, userDetails.getAuthorities());
        String newRefreshTokenValue = tokenProvider.createRefreshToken(authenticationToken);
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
            employeeRefreshTokenRepository.save(employeeTokenEntity);
        }
        else if (userDetails instanceof PartnerUserDetails) {
            WellPartnerUserEntity partner = ((PartnerUserDetails) userDetails).getPartner();
            PartnerRefreshTokenEntity partnerTokenEntity = partnerRefreshTokenRepository
                    .findByPartnerUser(partner)
                    .orElseGet(() -> new PartnerRefreshTokenEntity(partner, newRefreshTokenValue, expiryDate));
            // 토큰 업데이트 또는 새로 생성
            partnerTokenEntity.updateToken(newRefreshTokenValue, expiryDate);
            partnerRefreshTokenRepository.save(partnerTokenEntity);

        } else {
            throw new IllegalArgumentException("존재하지 않는 타입의 유저입니다.");
        }

        return newRefreshTokenValue;
    }


    // 토큰 삭제 메서드
    @Transactional
    public void deleteRefreshToken(UserDetails userDetails) {
        if (userDetails instanceof EmployeeUserDetails) {
            WellEmployeeUserEntity employee = ((EmployeeUserDetails) userDetails).getEmployee();
            employeeRefreshTokenRepository.deleteByEmployeeUser(employee);
        } else if (userDetails instanceof PartnerUserDetails) {
            WellPartnerUserEntity partner = ((PartnerUserDetails) userDetails).getPartner();
            partnerRefreshTokenRepository.deleteByPartnerUser(partner);
        }
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
        calendar.add(Calendar.SECOND, refreshTokenExpirationTime); // 현재 시간에 유효 시간을 더함
        return calendar.getTime(); // 새로운 만료 시간을 가진 Date 객체를 반환
    }



}
