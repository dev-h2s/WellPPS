package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellPartnerBackupRepository  extends JpaRepository<WellPartnerEntityBackup, String> {

    //거래처_idx 검색
    WellPartnerEntityBackup findByPartnerIdx(String partnerIdx);

    //거래처 등록
    WellPartnerEntityBackup save(WellPartnerEntityBackup wellPartnerEntityBackup);

    //거래처_idx삭제(체크항목 삭제)
    WellPartnerEntityBackup deleteByPartnerIdx(String partnerIdx);
}
