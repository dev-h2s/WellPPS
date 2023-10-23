package com.wellnetworks.wellcore.java.repository.File;

import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellPartnerFileCreateDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellPartnerFileRepository extends JpaRepository<WellPartnerFIleStorageEntity, String> {

    //거래처파일_idx 검색
    WellPartnerFIleStorageEntity findByFileIdx(String fileIdx);

    //거래처파일 등록
    WellPartnerFIleStorageEntity save(WellPartnerFIleStorageEntity wellPartnerFIleStorageEntity);

    //거래처파일_idx삭제(체크항목 삭제)
    WellPartnerFIleStorageEntity deleteByFileIdx(String fileIdx);

    void save(WellPartnerFileCreateDTO partnerFile);
}
