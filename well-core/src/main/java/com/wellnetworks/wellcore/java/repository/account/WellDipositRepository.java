package com.wellnetworks.wellcore.java.repository.account;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WellDipositRepository extends JpaRepository<WellDipositEntity, String> {
    @Query("SELECT SUM(CASE WHEN d.dipositStatus = '가상계좌입금액' THEN d.dipositAmount ELSE 0 END) FROM WellDipositEntity d")
    Long calculateDipositSum();

    @Query("SELECT SUM(CASE WHEN d.dipositStatus = '수수료지급액' THEN d.dipositAmount ELSE 0 END) FROM WellDipositEntity d")
    Long calculateChargeSum();
}