package com.wellnetworks.wellcore.java.repository.File;

import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WellEmployeeFileRepository extends JpaRepository<WellEmployeeFileStorageEntity, Long> {
    List<WellEmployeeFileStorageEntity> findByEmployeeIdx(String employeeIdx);

    void deleteByFileId(Long fileId);
}
