package com.wellnetworks.wellcore.java.repository.apikeyIn;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WellApikeyInRepository extends JpaRepository<WellApikeyInEntity, String> {

    //apikey_idx 검색
    WellApikeyInEntity findByApiKeyInIdx(String apikeyInIdx);

    //apikey 등록
    WellApikeyInEntity save(WellApikeyInEntity wellApikeyInEntity);

    //apikey_idx삭제(체크항목 삭제)
    WellApikeyInEntity deleteByApiKeyInIdx(String apikeyInIdx);
}
