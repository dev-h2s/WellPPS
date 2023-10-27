package com.wellnetworks.wellcore.java.repository.File;

import com.wellnetworks.wellcore.java.domain.file.WellVirtualAccountFIleStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellVirtualAccountFileRepository extends JpaRepository<WellVirtualAccountFIleStorageEntity, String> {

    //가상계좌파일_idx 검색
    WellVirtualAccountFIleStorageEntity findByFileIdx(String fileIdx);

    //가상계좌파일 등록
    WellVirtualAccountFIleStorageEntity save(WellVirtualAccountFIleStorageEntity wellVirtualAccountFIleStorageEntity);

    //가상계좌파일_idx삭제(체크항목 삭제)
    WellVirtualAccountFIleStorageEntity deleteByFileIdx(String fileIdx);
}
