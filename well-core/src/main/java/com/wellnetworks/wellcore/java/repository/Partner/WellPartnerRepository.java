package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellPartnerRepository extends JpaRepository<WellPartnerEntity, String>, JpaSpecificationExecutor<WellPartnerEntity>{

// 거래처 리스트 crud

    //거래처_idx 조회
    WellPartnerEntity findByPartnerIdx(String partnerIdx);

    // partnerUpperIdx를 사용하여 상부점의 이름을 조회
    @Query("SELECT  p.partnerName FROM WellPartnerEntity p WHERE p.partnerIdx = :partnerIdx")
    String findPartnerUpperNameByPartnerUpperIdx(String partnerIdx);

    default String findPartnerNameByPartnerIdxSafely(String partnerUpperIdx) {
        if (partnerUpperIdx == null) {
            return null; // 또는 원하는 기본값을 반환
        } else {
            try {
                return findPartnerUpperNameByPartnerUpperIdx(partnerUpperIdx);
            } catch (Exception e) {
                e.printStackTrace(); // 예외를 처리할 방법을 선택하세요.
                return null; // 또는 원하는 기본값을 반환
            }
        }
    }

    // 상부점의 하부점 목록 가져오기
    @Query("SELECT p FROM WellPartnerEntity p WHERE p.partnerUpperIdx = :partnerUpperIdx")
    List<WellPartnerEntity> findSubPartnersByPartnerUpperIdx(String partnerUpperIdx);

    WellPartnerEntity findByPartnerCode(String partnerCode);

    //거래처 거래유무 개수
    Long countByTransactionStatus(String transactionStatus);

    //거래처 등록
    WellPartnerEntity save(WellPartnerEntity wellPartnerEntity);

    //내림차순 정렬
    List<WellPartnerEntity> findAllByOrderByProductRegisterDateDesc();

    List<WellPartnerEntity> findAll(Specification<WellPartnerEntity> spec);

    @Query("SELECT  p.partnerName FROM WellPartnerEntity p WHERE p.partnerIdx = :partnerIdx")
    String findPartnerNameByPartnerIdx(String partnerIdx);

}
