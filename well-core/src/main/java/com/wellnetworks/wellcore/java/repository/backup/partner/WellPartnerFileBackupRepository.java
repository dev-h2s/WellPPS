package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellApikeyInEntityBackup;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerFileStorageEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellPartnerFileBackupRepository extends JpaRepository<WellPartnerFileStorageEntityBackup, String> {

    //거래처파일_idx 검색
    WellPartnerFileStorageEntityBackup findByFileIdx(String fileIdx);

    //거래처파일 등록
    WellPartnerFileStorageEntityBackup save(WellPartnerFileStorageEntityBackup wellPartnerFileStorageEntityBackup);

    //거래처파일_idx삭제(체크항목 삭제)
    WellPartnerFileStorageEntityBackup deleteByFileIdx(String fileIdx);
}
