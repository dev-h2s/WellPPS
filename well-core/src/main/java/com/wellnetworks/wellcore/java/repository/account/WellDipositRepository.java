package com.wellnetworks.wellcore.java.repository.account;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellDipositEntityBackup;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellDipositRepository extends JpaRepository<WellDipositEntity, String> {
    //예치금_idx 검색
    WellDipositEntityBackup findByDipositIdx(String dipositIdx);

    //예치금 등록
    WellDipositEntityBackup save(WellDipositEntityBackup wellDipositEntityBackup);

    //예치금_idx삭제(체크항목 삭제)
    WellDipositEntityBackup deleteByDipositIdx(String dipositIdx);
}