package com.wellnetworks.wellcore.java.repository.File;

import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WellFileStorageRepository extends JpaRepository<WellFileStorageEntity, String> {



    //거래처리스트 관련

    // 파일종류와 partnerIdx를 기준으로 파일 검색
    Optional<WellFileStorageEntity> findByFileKindAndPartnerIdx(String fileKind, String partnerIdx);
}
