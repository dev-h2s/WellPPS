package com.wellnetworks.wellcore.java.repository.File;

import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellFIleStorageDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellFileStorageRepository extends JpaRepository<WellFileStorageEntity, String> {

    //첨부파일_idx 검색
    WellFileStorageRepository findByFileIdx(String fileIdx);

    //첨부파일 등록
    WellFileStorageRepository save(WellFileStorageRepository wellFileStorageRepository);

    //첨부파일_idx삭제(체크항목 삭제)
    WellFileStorageRepository deleteByFileIdx(String fileIdx);

    WellFIleStorageDTO save(WellFIleStorageDTO fileStorage);
}