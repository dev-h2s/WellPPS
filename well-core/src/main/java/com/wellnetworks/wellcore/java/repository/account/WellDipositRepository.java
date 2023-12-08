package com.wellnetworks.wellcore.java.repository.account;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellDipositRepository extends JpaRepository<WellDipositEntity, String> {
    @Query("SELECT SUM(CASE WHEN d.dipositStatus = '가상계좌입금액' THEN d.dipositAmount ELSE 0 END) FROM WellDipositEntity d")
    Long calculateDipositSum();

    @Query("SELECT SUM(CASE WHEN d.dipositStatus = '수수료지급액' THEN d.dipositAmount ELSE 0 END) FROM WellDipositEntity d")
    Long calculateChargeSum();

    @Query("SELECT COALESCE(SUM(d.dipositAmount), 0) FROM WellDipositEntity d WHERE d.partner = :partner")
    int calculateDipositSumByPartner(@Param("partner") WellPartnerEntity partner);

}