package com.wellnetworks.wellcore.java.repository.account;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WellVirtualAccountRepository extends JpaRepository<WellVirtualAccountEntity, String> {
    Long countByIssuance(String issuance);

    List<WellVirtualAccountEntity> findAvailableAccountsByVirtualBankNameAndIssuance(String virtualBankName, String issuance);

    Page<WellVirtualAccountEntity> findAll(Specification<WellVirtualAccountEntity> spec, Pageable pageable);

    Optional<WellVirtualAccountEntity> findByPartnerPartnerIdx(String partnerIdx);

    @Query("SELECT COUNT(p) FROM WellVirtualAccountEntity p WHERE p.issuance = '발급'")
    Long issuedCount(); // 발급

    @Query("SELECT COUNT(p) FROM WellVirtualAccountEntity p WHERE p.issuance = '미발급'")
    Long notIssuedCount(); // 미발급

    @Query("SELECT COUNT(p) FROM WellVirtualAccountEntity p WHERE p.issuance = '회수'")
    Long collectCount(); // 회수
}
