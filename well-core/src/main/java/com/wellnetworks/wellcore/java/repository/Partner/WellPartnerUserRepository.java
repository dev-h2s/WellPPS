package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellPartnerUserRepository extends JpaRepository<WellPartnerUserEntity, Long> {
    //거래처_idx 조회
    WellPartnerUserEntity findByPartnerIdx(String partnerIdx);
}
