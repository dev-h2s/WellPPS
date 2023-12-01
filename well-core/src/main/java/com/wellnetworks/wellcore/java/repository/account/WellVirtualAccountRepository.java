package com.wellnetworks.wellcore.java.repository.account;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellVirtualAccountRepository extends JpaRepository<WellVirtualAccountEntity, String> {
    Long countByIssuance(String issuance);

    Page<WellVirtualAccountEntity> findAll(Specification<WellPartnerEntity> spec, Pageable pageable);
}
