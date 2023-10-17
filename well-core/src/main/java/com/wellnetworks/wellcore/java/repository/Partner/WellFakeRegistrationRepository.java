package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellFakeRegistrationEntityBackup;
import com.wellnetworks.wellcore.java.domain.partner.WellFakeRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellFakeRegistrationRepository extends JpaRepository<WellFakeRegistrationEntity, Long> {

    //부정가입현황_idx 검색
    WellFakeRegistrationEntity findByFakeRegistrationId(Long fakeRegistrationId);

    //부정가입현황 등록
    WellFakeRegistrationEntity save(WellFakeRegistrationEntity wellFakeRegistrationEntity);

    //부정가입현황_idx삭제(체크항목 삭제)
    WellFakeRegistrationEntity deleteByFakeRegistrationId(Long fakeRegistrationId);
}
