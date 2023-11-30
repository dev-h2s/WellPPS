package com.wellnetworks.wellcore.java.repository.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.domain.refreshtoken.EmployeeRefreshTokenEntity;
import com.wellnetworks.wellcore.java.domain.refreshtoken.PartnerRefreshTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PartnerRefreshTokenRepository extends JpaRepository<PartnerRefreshTokenEntity, Long> {
    // WellPartnerUserEntity를 기준으로 PartnerRefreshTokenEntity를 찾는 메서드
    Optional<PartnerRefreshTokenEntity> findByPartnerUser(WellPartnerUserEntity partnerUser);

    @Modifying
    @Query("delete from PartnerRefreshTokenEntity p where p.partnerUser = :partnerUser")
    void deleteByPartnerUser(@Param("partnerUser") WellPartnerUserEntity partnerUser);

    // 만료된 토큰을 찾아 삭제하는 메서드
    @Modifying
    @Query("delete from PartnerRefreshTokenEntity e where e.expiryDate <= CURRENT_TIMESTAMP")
    void deleteAllExpiredTokens();
}