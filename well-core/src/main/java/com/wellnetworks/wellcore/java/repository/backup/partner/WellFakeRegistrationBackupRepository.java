package com.wellnetworks.wellcore.java.repository.backup.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellApikeyInEntityBackup;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellFakeRegistrationEntityBackup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellFakeRegistrationBackupRepository extends JpaRepository<WellFakeRegistrationEntityBackup, Long> {

    //부정가입현황_idx 검색
    WellFakeRegistrationEntityBackup findByFakeRegistrationId(Long fakeRegistrationId);

    //부정가입현황 등록
    WellFakeRegistrationEntityBackup save(WellFakeRegistrationEntityBackup wellFakeRegistrationEntityBackup);

    //부정가입현황_idx삭제(체크항목 삭제)
    WellFakeRegistrationEntityBackup deleteByFakeRegistrationId(Long fakeRegistrationId);
}
