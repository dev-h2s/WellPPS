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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
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

    @Transactional
    public String createAndPersistRefreshToken(UserDetails userDetails) {
        String refreshToken = tokenProvider.createRefreshToken(new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(), null, userDetails.getAuthorities()));

        Date expiryDate = calculateExpiryDate(securityProperties.getRefreshTokenExpirationTime());

        if (userDetails instanceof EmployeeUserDetails) {
            WellEmployeeUserEntity employee = ((EmployeeUserDetails) userDetails).getEmployee();
            EmployeeRefreshTokenEntity tokenEntity = new EmployeeRefreshTokenEntity(null, employee, refreshToken, expiryDate);
            employeeRefreshTokenRepository.save(tokenEntity);
        } else if (userDetails instanceof PartnerUserDetails) {
            WellPartnerUserEntity partner = ((PartnerUserDetails) userDetails).getPartner();
            PartnerRefreshTokenEntity tokenEntity = new PartnerRefreshTokenEntity(null, partner, refreshToken, expiryDate);
            partnerRefreshTokenRepository.save(tokenEntity);
        } else {
            throw new IllegalArgumentException("Unknown user details type");
        }

        return refreshToken;
    }

    private Date calculateExpiryDate(int refreshTokenExpirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, refreshTokenExpirationTime);
        return calendar.getTime();
    }



}
