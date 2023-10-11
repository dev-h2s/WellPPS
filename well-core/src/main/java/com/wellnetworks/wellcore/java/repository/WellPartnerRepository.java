package com.wellnetworks.wellcore.java.repository;

import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WellPartnerRepository extends JpaRepository<WellPartnerEntity, String>{

// 거래처 리스트 crud

    //거래처_idx 검색
    WellPartnerEntity findByPartnerIdx(String partnerIdx);
    //페이지 네이션으로 모든 거래처 엔티티 검색
    Page<WellPartnerEntity> findAll(Pageable pageable);

    //상부점 select박스로 검색
    Optional<WellPartnerEntity> findByPartnerUpperId(Long partnerUpperId);
    //충전할인율구분 select박스로 검색
    Optional<WellPartnerEntity> findByDiscountCategory(String discountCategory);
    //거래처구분 select박스로 검색
    Optional<WellPartnerEntity> findByPartnerType(String partnerType);
    //영업담당자 select박스로 검색
    Optional<WellPartnerEntity> findBySalesManager(String salesManager);
    //등록증 select박스로 검색
    @Query("SELECT DISTINCT f " +
            "FROM WellFileStorageEntity f " +
            "JOIN f.PartnerFileStorage pf " +
            "JOIN pf.partner p " +
            "WHERE p.partnerIdx = :partnerIdx " +
            "AND f.fileKind = '등록증'")
    Optional<WellFileStorageEntity> findByRegistrationByPartnerIdx(@Param("partnerIdx") String partnerIdx);
    //계약서 select박스로 검색
    @Query("SELECT DISTINCT f " +
            "FROM WellFileStorageEntity f " +
            "JOIN f.PartnerFileStorage pf " +
            "JOIN pf.partner p " +
            "WHERE p.partnerIdx = :partnerIdx " +
            "AND f.fileKind = '계약서'")
    Optional<WellFileStorageEntity> findContractByPartnerIdx(@Param("partnerIdx") String partnerIdx);
    //거래유무 select박스로 검색
    Optional<WellPartnerEntity> findByTransactionStatus(String transactionStatus);
    //지역 select박스로 검색
    Optional<WellPartnerEntity> findByRegion(String region);

    //거래처명으로 거래처 검색
    Optional<WellPartnerEntity> findByPartnerName(String partnerName);
    //대표자명으로 거래처 검색
    Optional<WellPartnerEntity> findByCeoName(String ceoName);
    //대표자번호로 거래처 검색
    Optional<WellPartnerEntity> findByCeoTelephone(String ceoTelephone);
    //p코드로 거래처 검색
    Optional<WellPartnerEntity> findByPartnerCode(String partnerCode);
    //주소로 거래처 검색
    @Query("SELECT p FROM WellPartnerEntity p " +
            "WHERE p.registrationAddress LIKE %:keyword% " +
            "OR p.registrationDetailAddress LIKE %:keyword% " +
            "OR p.locationAddress LIKE %:keyword% " +
            "OR p.locationDetailAddress LIKE %:keyword%")
    Optional<WellPartnerEntity> findByAddress(@Param("keyword") String keyword);


    //작성자로 거래처 검색
    Optional<WellPartnerEntity> findByWriter(String writer);
    //사업장번호로 거래처 검색
    Optional<WellPartnerEntity> findByRegistrationNumber(String registrationNumber);

    //거래유무 엔티티 개수
    //거래중 개수
    @Query("SELECT p FROM WellPartnerEntity p " +
            "WHERE p.transactionStatus = '거래중'")
    Long countByTrading(String transactionStatus);
    //거래중지 개수
    @Query("SELECT p FROM WellPartnerEntity p " +
            "WHERE p.transactionStatus = '거래중지'")
    Long countByTradeStop(String transactionStatus);
    //관리대상 개수
    @Query("SELECT p FROM WellPartnerEntity p " +
            "WHERE p.transactionStatus = '관리대상'")
    Long countByTarget(String transactionStatus);
    // 가등록 개수
    @Query("SELECT p FROM WellPartnerEntity p " +
            "WHERE p.transactionStatus = '가등록'")
    Long countByFakeTrade(String transactionStatus);
    //첨부파일에 따라 계약서 미첨부 개수
    @Query("SELECT COUNT(p.partnerIdx) " +
            "FROM WellPartnerEntity p " +
            "LEFT JOIN WellPartnerFIleStorageEntity pf ON p.partnerIdx = pf.partner " +
            "LEFT JOIN WellFileStorageEntity f ON pf.fileIdx = f.fileIdx AND f.fileKind = '계약서' " +
            "WHERE f.fileIdx IS NULL")
    Optional<Long> countByContractIsNull();
    //첨부파일에 따라 등록증 미첨부 개수
    @Query("SELECT COUNT(p.partnerIdx) " +
            "FROM WellPartnerEntity p " +
            "LEFT JOIN WellPartnerFIleStorageEntity pf ON p.partnerIdx = pf.partner " +
            "LEFT JOIN WellFileStorageEntity f ON pf.fileIdx = f.fileIdx AND f.fileKind = '등록증' " +
            "WHERE f.fileIdx IS NULL")
    Optional<Long> countByRegistrationIsNull();

    //거래처 등록
    WellPartnerEntity save(WellPartnerEntity wellPartnerEntity);
    //거래처 수정

    //거래처_idx삭제(체크항목 삭제)
    Optional<WellPartnerEntity> deleteByPartnerIdx(String partnerIdx);
}
