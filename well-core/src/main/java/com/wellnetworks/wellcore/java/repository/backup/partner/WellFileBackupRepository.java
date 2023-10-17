package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellFileStorageEntityBackup;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellVirtualAccountFileStorageEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellFileBackupRepository extends JpaRepository<WellFileStorageEntityBackup, String> {

    //첨부파일_idx 검색
    WellFileStorageEntityBackup findByFileIdx(String fileIdx);

    //첨부파일 등록
    WellFileStorageEntityBackup save(WellFileStorageEntityBackup wellFileStorageEntityBackup);

    //첨부파일_idx삭제(체크항목 삭제)
    WellFileStorageEntityBackup deleteByFileIdx(String fileIdx);
}
