package com.wellnetworks.wellcore.java.repository.account;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellVirtualAccountEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellVirtualAccountRepository extends JpaRepository<WellVirtualAccountEntity, String> {

    //가상계좌_idx 검색
    WellVirtualAccountEntity findByPartnerIdx(String partnerIdx);

    //가상계좌 등록
    WellVirtualAccountEntity save(WellVirtualAccountEntity wellVirtualAccountEntity);

    //가상계좌_idx삭제(체크항목 삭제)
    WellVirtualAccountEntity deleteByPartnerIdx(String partnerIdx);
}
