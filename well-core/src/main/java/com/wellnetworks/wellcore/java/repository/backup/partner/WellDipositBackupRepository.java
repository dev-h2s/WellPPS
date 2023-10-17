package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellApikeyInEntityBackup;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellDipositEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellDipositBackupRepository extends JpaRepository<WellDipositEntityBackup, String> {

    //예치금_idx 검색
    WellDipositEntityBackup findByDipositIdx(String dipositIdx);

    //예치금 등록
    WellDipositEntityBackup save(WellDipositEntityBackup wellDipositEntityBackup);

    //예치금_idx삭제(체크항목 삭제)
    WellDipositEntityBackup deleteByDipositIdx(String dipositIdx);
}
