package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellPartnerUserRepository extends JpaRepository<WellPartnerUserEntity, Long> {

    //거래처유저_id 검색
    WellPartnerUserEntity findByPartnerId(Long partnerId);

    //거래처유저 등록
    WellPartnerUserEntity save(WellPartnerUserEntity wellPartnerUserEntity);

    //거래처유저_id삭제(체크항목 삭제)
    WellPartnerUserEntity deleteByPartnerId(Long partnerId);
}
