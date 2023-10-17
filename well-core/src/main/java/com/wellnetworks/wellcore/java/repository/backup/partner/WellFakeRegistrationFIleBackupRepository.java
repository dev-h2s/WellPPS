package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellFakeRegistrationFileStorageEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellFakeRegistrationFIleBackupRepository extends JpaRepository<WellFakeRegistrationFileStorageEntityBackup, String> {

    //부정가입현황_idx 검색
    WellFakeRegistrationFileStorageEntityBackup findByFileIdx(String fileIdx);

    //부정가입현황 등록
    WellFakeRegistrationFileStorageEntityBackup save(WellFakeRegistrationFileStorageEntityBackup wellFakeRegistrationFileStorageEntityBackup);

    //부정가입현황_idx삭제(체크항목 삭제)
    WellFakeRegistrationFileStorageEntityBackup deleteByFileIdx(String fileIdx);
}
