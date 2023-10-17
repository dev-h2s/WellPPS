package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellDipositEntityBackup;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellOpeningEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellOpeningBackupRepository extends JpaRepository<WellOpeningEntityBackup, String> {

    //개통_idx 검색
    WellOpeningEntityBackup findByOpeningInfoIdx(String openingInfoIdx);

    //개통 등록
    WellOpeningEntityBackup save(WellOpeningEntityBackup wellOpeningEntityBackup);

    //개통_idx삭제(체크항목 삭제)
    WellOpeningEntityBackup deleteByOpeningInfoIdx(String openingInfoIdx);
}
