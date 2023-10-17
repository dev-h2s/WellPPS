package com.wellnetworks.wellcore.java.repository.File;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellFakeRegistrationFileStorageEntityBackup;
import com.wellnetworks.wellcore.java.domain.file.WellFakeRegistrationFIleStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellFakeRegistrationFIleRepository extends JpaRepository<WellFakeRegistrationFIleStorageEntity, String> {

    //부정가입현황_idx 검색
    WellFakeRegistrationFIleStorageEntity findByFileIdx(String fileIdx);

    //부정가입현황 등록
    WellFakeRegistrationFIleStorageEntity save(WellFakeRegistrationFIleStorageEntity wellFakeRegistrationFIleStorageEntity);

    //부정가입현황_idx삭제(체크항목 삭제)
    WellFakeRegistrationFIleStorageEntity deleteByFileIdx(String fileIdx);
}
