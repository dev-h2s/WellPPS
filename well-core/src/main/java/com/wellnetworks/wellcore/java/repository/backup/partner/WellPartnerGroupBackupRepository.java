package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerEntityBackup;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerGroupEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellPartnerGroupBackupRepository extends JpaRepository<WellPartnerGroupEntityBackup, Long> {

    //거래처그룹_idx 검색
    WellPartnerGroupEntityBackup findByPartnerGroupId(Long partnerGroupId);

    //거래처그룹 등록
    WellPartnerGroupEntityBackup save(WellPartnerGroupEntityBackup wellPartnerGroupEntityBackup);

    //거래처그룹_idx삭제(체크항목 삭제)
    WellPartnerGroupEntityBackup deleteByPartnerGroupId(Long PartnerGroupId);
}
