package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellPartnerGroupRepository extends JpaRepository<WellPartnerGroupEntity, Long> {

    //거래처그룹_idx 검색
    WellPartnerGroupEntity findByPartnerGroupId(Long partnerGroupId);

    //거래처그룹 등록
    WellPartnerGroupEntity save(WellPartnerGroupEntity wellPartnerGroupEntity);

    //거래처그룹_idx삭제(체크항목 삭제)
    WellPartnerGroupEntity deleteByPartnerGroupId(Long PartnerGroupId);
}
