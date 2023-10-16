package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WellPartnerRepository extends JpaRepository<WellPartnerEntity, String>{

// 거래처 리스트 crud

    //거래처_idx 검색
    WellPartnerEntity findByPartnerIdx(String partnerIdx);
    //페이지 네이션으로 모든 거래처 엔티티 검색
    Page<WellPartnerEntity> findAll(Pageable pageable);

    //거래처 거래유무 개수
    Long countByTransactionStatusAndPartnerIdx(String transactionStatus, String partnerIdx);

    //거래처 등록
    WellPartnerEntity save(WellPartnerEntity wellPartnerEntity);

    //거래처_idx삭제(체크항목 삭제)
    WellPartnerEntity deleteByPartnerIdx(String partnerIdx);
}
