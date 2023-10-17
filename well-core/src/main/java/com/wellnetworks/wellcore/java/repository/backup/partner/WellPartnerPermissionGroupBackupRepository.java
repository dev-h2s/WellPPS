package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerEntityBackup;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerPermissionGroupEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellPartnerPermissionGroupBackupRepository extends JpaRepository<WellPartnerPermissionGroupEntityBackup, String> {

    //거래처유저그룹_idx 검색
    WellPartnerPermissionGroupEntityBackup findByPartnerManagerGroupKey(String partnerManagerGroupKey);

    //거래처유저그룹 등록
    WellPartnerPermissionGroupEntityBackup save(WellPartnerPermissionGroupEntityBackup wellPartnerPermissionGroupEntityBackup);

    //거래처유저그룹_idx삭제(체크항목 삭제)
    WellPartnerPermissionGroupEntityBackup deleteByPartnerManagerGroupKey(String partnerManagerGroupKey);
}
