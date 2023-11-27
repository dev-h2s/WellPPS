package com.wellnetworks.wellcore.java.repository.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.domain.refreshtoken.EmployeeRefreshTokenEntity;
import com.wellnetworks.wellcore.java.domain.refreshtoken.PartnerRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartnerRefreshTokenRepository extends JpaRepository<PartnerRefreshTokenEntity, Long> {
    // WellPartnerUserEntity를 기준으로 PartnerRefreshTokenEntity를 찾는 메서드
    Optional<PartnerRefreshTokenEntity> findByPartnerUser(WellPartnerUserEntity partnerUser);
}