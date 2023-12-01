package com.wellnetworks.wellcore.java.repository.apikeyIn;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellApikeyInRepository extends JpaRepository<WellApikeyInEntity, String> {

    //apikey_idx 검색
    WellApikeyInEntity findByApiKeyInIdx(String apikeyInIdx);

    WellApikeyInEntity findByPartnerIdx(String partnerIdx);

    void deleteByApiKeyInIdx(String apikeyInIdx);

    Page<WellApikeyInEntity> findAll(Specification<WellApikeyInEntity> spec, Pageable pageable);
}
