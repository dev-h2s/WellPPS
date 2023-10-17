package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerEntityBackup;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerUserEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellPartnerUserBackupRepository extends JpaRepository<WellPartnerUserEntityBackup, Long> {

    //거래처유저_id 검색
    WellPartnerUserEntityBackup findByPartnerId(Long partnerId);

    //거래처유저 등록
    WellPartnerUserEntityBackup save(WellPartnerUserEntityBackup wellPartnerUserEntityBackup);

    //거래처유저_id삭제(체크항목 삭제)
    WellPartnerUserEntityBackup deleteByPartnerId(Long partnerId);
}
