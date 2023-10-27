package com.wellnetworks.wellcore.java.repository.account;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellVirtualAccountRepository extends JpaRepository<WellVirtualAccountEntity, String> {

    //가상계좌_idx 검색
    WellVirtualAccountEntity findByVirtualAccountIdx(String virtualAccountIdx);

    //가상계좌 등록
    WellVirtualAccountEntity save(WellVirtualAccountEntity wellVirtualAccountEntity);

    //가상계좌_idx삭제(체크항목 삭제)
    WellVirtualAccountEntity deleteByVirtualAccountIdx(String virtualAccountIdx);
}
