package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellFakeRegistrationFileStorageEntityBackup;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellVirtualAccountFileStorageEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellVirtualAccountFileBackupRepository extends JpaRepository<WellVirtualAccountFileStorageEntityBackup, String> {

    //가상계좌파일_idx 검색
    WellVirtualAccountFileStorageEntityBackup findByFileIdx(String fileIdx);

    //가상계좌파일 등록
    WellVirtualAccountFileStorageEntityBackup save(WellVirtualAccountFileStorageEntityBackup wellVirtualAccountFileStorageEntityBackup);

    //가상계좌파일_idx삭제(체크항목 삭제)
    WellVirtualAccountFileStorageEntityBackup deleteByFileIdx(String fileIdx);
}
