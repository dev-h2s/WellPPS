package com.wellnetworks.wellcore.java.repository;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellPartnerRepository extends JpaRepository<WellPartnerEntity, String> {
    // 거래처 리스트 crud



}
