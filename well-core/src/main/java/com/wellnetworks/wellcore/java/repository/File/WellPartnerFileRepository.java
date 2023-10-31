package com.wellnetworks.wellcore.java.repository.File;

import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WellPartnerFileRepository extends JpaRepository<WellPartnerFIleStorageEntity, Long> {

    //apikey_idx 검색
    List<WellPartnerFIleStorageEntity> findByPartnerIdx(String partnerIdx);
}
