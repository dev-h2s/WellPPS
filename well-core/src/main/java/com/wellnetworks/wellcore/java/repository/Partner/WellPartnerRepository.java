package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WellPartnerRepository extends JpaRepository<WellPartnerEntity, String>, JpaSpecificationExecutor<WellPartnerEntity>{

// 거래처 리스트 crud

    //거래처_idx 조회
    WellPartnerEntity findByPartnerIdx(String partnerIdx);

    Optional<WellPartnerEntity> findByPartnerName(String partnerName);

    // partnerUpperIdx를 사용하여 상부점의 이름을 조회
    @Query("SELECT  p.partnerName FROM WellPartnerEntity p WHERE p.partnerIdx = :partnerIdx")
    String findPartnerUpperNameByPartnerUpperIdx(String partnerIdx);

    default String findPartnerNameByPartnerIdxSafely(String partnerUpperIdx) {
        if (partnerUpperIdx == null || partnerUpperIdx.isEmpty()) {
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

    // 등록된 거래처 개수
    @Query("SELECT COUNT(p) FROM WellPartnerEntity p WHERE p.transactionStatus = '등록'")
    Long registeredCount();

    // 가등록된 거래처 개수
    @Query("SELECT COUNT(p) FROM WellPartnerEntity p WHERE p.transactionStatus = '가등록'")
    Long preRegisteredCount();

    // 관리대상 거래처 개수
    @Query("SELECT COUNT(p) FROM WellPartnerEntity p WHERE p.transactionStatus = '관리대상'")
    Long managementCount();

    // 거래중지된 거래처 개수
    @Query("SELECT COUNT(p) FROM WellPartnerEntity p WHERE p.transactionStatus = '거래중지'")
    Long suspendedCount();

    // 거래처의 사업자등록증이 없는 개수
    @Query("SELECT COUNT(p) FROM WellPartnerEntity p WHERE p.partnerIdx NOT IN (SELECT DISTINCT fs.partnerIdx FROM WellPartnerFIleStorageEntity fs WHERE fs.file.fileKind = '사업자등록증')")
    Long countBusinessLicenseMissing();


    // 거래처의 계약서가 없는 개수
    @Query("SELECT COUNT(p) FROM WellPartnerEntity p WHERE p.partnerIdx NOT IN (SELECT DISTINCT fs.partnerIdx FROM WellPartnerFIleStorageEntity fs WHERE fs.file.fileKind = '계약서')")
    Long countContractDocumentMissing();





    //거래처 등록
    WellPartnerEntity save(WellPartnerEntity wellPartnerEntity);

    Page<WellPartnerEntity> findAll(Specification<WellPartnerEntity> spec, Pageable pageable);

    @Query("SELECT  p.partnerName FROM WellPartnerEntity p WHERE p.partnerIdx = :partnerIdx")
    String findPartnerNameByPartnerIdx(String partnerIdx);
}
