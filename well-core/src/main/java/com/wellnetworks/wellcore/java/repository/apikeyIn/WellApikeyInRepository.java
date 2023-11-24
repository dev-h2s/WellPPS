package com.wellnetworks.wellcore.java.repository.apikeyIn;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellApikeyInRepository extends JpaRepository<WellApikeyInEntity, String> {

    //apikey_idx 검색
    WellApikeyInEntity findByApiKeyInIdx(String apikeyInIdx);

    void deleteByApiKeyInIdx(String apikeyInIdx);

    List<WellApikeyInEntity> findAll(Specification<WellApikeyInEntity> spec);
}
