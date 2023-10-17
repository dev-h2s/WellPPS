package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellApikeyInEntityBackup;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellApikeyInBackupRepository extends JpaRepository<WellApikeyInEntityBackup, String> {

    //apikey_idx 검색
    WellApikeyInEntityBackup findByApiKeyInIdx(String apikeyInIdx);

    //apikey 등록
    WellApikeyInEntityBackup save(WellApikeyInEntityBackup wellApikeyInEntityBackup);

    //apikey_idx삭제(체크항목 삭제)
    WellApikeyInEntityBackup deleteByApiKeyInIdx(String apikeyInIdx);
}
