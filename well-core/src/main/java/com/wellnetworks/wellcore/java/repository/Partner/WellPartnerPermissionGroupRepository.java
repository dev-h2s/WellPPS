package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerPermissionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellPartnerPermissionGroupRepository extends JpaRepository<WellPartnerPermissionGroupEntity, String> {

    //거래처유저그룹_idx 검색
    WellPartnerPermissionGroupEntity findByPartnerManagerGroupKey(String partnerManagerGroupKey);

    //거래처유저그룹 등록
    WellPartnerPermissionGroupEntity save(WellPartnerPermissionGroupEntity wellPartnerPermissionGroupEntity);

    //거래처유저그룹_idx삭제(체크항목 삭제)
    WellPartnerPermissionGroupEntity deleteByPartnerManagerGroupKey(String partnerManagerGroupKey);
}
