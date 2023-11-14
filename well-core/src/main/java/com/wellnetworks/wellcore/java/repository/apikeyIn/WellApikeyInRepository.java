package com.wellnetworks.wellcore.java.repository.apikeyIn;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellApikeyInRepository extends JpaRepository<WellApikeyInEntity, String> {

    //apikey_idx 검색
    WellApikeyInEntity findByApiKeyInIdx(String apikeyInIdx);
}
