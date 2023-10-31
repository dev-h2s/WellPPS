package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellPartnerRepository extends JpaRepository<WellPartnerEntity, String>{

// 거래처 리스트 crud

    //거래처_idx 조회
    WellPartnerEntity findByPartnerIdx(String partnerIdx);
    //페이지 네이션으로 모든 거래처 엔티티 조회
    Page<WellPartnerEntity> findAll(Pageable pageable);

    WellPartnerEntity findByPartnerCode(String partnerCode);

    //거래처 거래유무 개수
    Long countByTransactionStatus(String transactionStatus);

    //거래처 등록
    WellPartnerEntity save(WellPartnerEntity wellPartnerEntity);

    //내림차순 정렬
    List<WellPartnerEntity> findAllByOrderByProductRegisterDateDesc();

    //거래처 검색
    Page<WellPartnerEntity> findAll(Specification<WellPartnerEntity> searchSpecification, Pageable pageable);
}
