package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellApikeyInEntityBackup;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellVirtualAccountEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellVirtualAccountBackupRepository extends JpaRepository<WellVirtualAccountEntityBackup, String> {

    //가상계좌_idx 검색
    WellVirtualAccountEntityBackup findByVirtualAccountIdx(String virtualAccountIdx);

    //가상계좌 등록
    WellVirtualAccountEntityBackup save(WellVirtualAccountEntityBackup wellVirtualAccountEntityBackup);

    //가상계좌_idx삭제(체크항목 삭제)
    WellVirtualAccountEntityBackup deleteByVirtualAccountIdx(String virtualAccountIdx);
}
