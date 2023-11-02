package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellPartnerRepository extends JpaRepository<WellPartnerEntity, String>, JpaSpecificationExecutor<WellPartnerEntity>{

// 거래처 리스트 crud

    //거래처_idx 조회
    WellPartnerEntity findByPartnerIdx(String partnerIdx);

    WellPartnerEntity findByPartnerCode(String partnerCode);

    //거래처 거래유무 개수
    Long countByTransactionStatus(String transactionStatus);

    //거래처 등록
    WellPartnerEntity save(WellPartnerEntity wellPartnerEntity);

    //내림차순 정렬
    List<WellPartnerEntity> findAllByOrderByProductRegisterDateDesc();

    List<WellPartnerEntity> findAll(Specification<WellPartnerEntity> spec);
}
