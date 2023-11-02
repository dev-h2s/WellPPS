package com.wellnetworks.wellcore.java.repository.File;

import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface WellPartnerFileRepository extends JpaRepository<WellPartnerFIleStorageEntity, Long> {

    //apikey_idx 검색
    List<WellPartnerFIleStorageEntity> findByPartnerIdx(String partnerIdx);
}
