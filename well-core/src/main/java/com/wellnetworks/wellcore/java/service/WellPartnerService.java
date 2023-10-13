package com.wellnetworks.wellcore.java.service;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.repository.WellPartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
@Service
public class WellPartnerService {

    @Autowired
    private WellPartnerRepository wellPartnerRepository;

//    @Autowired
    // 사용자 정보 가져오기
//    private WellUserService wellUserService;

//    @Autowired
    // 파일 정보 가져오기
//    private WellFileStorageService wellFileStorageService;

//    @Autowired
    // 사용자 crud 가져오기
//    private WellUserRepository wellUserRepository;

    // 페이지네이션 거래처 검색
    public Page<WellPartnerEntity> getPaginatedPartners(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return wellPartnerRepository.findAll(pageable);
    }






    //거래유무 엔티티 개수
    //거래중 개수
//    @Query("SELECT p FROM WellPartnerEntity p " +
//            "WHERE p.transactionStatus = '거래중'")
//    Long countByTrading(String transactionStatus);
//    //거래중지 개수
//    @Query("SELECT p FROM WellPartnerEntity p " +
//            "WHERE p.transactionStatus = '거래중지'")
//    Long countByTradeStop(String transactionStatus);
//    //관리대상 개수
//    @Query("SELECT p FROM WellPartnerEntity p " +
//            "WHERE p.transactionStatus = '관리대상'")
//    Long countByTarget(String transactionStatus);
//    // 가등록 개수
//    @Query("SELECT p FROM WellPartnerEntity p " +
//            "WHERE p.transactionStatus = '가등록'")
//    Long countByFakeTrade(String transactionStatus);
//    //첨부파일에 따라 계약서 미첨부 개수
//    @Query("SELECT COUNT(p.partnerIdx) " +
//            "FROM WellPartnerEntity p " +
//            "LEFT JOIN WellPartnerFIleStorageEntity pf ON p.partnerIdx = pf.partner " +
//            "LEFT JOIN WellFileStorageEntity f ON pf.fileIdx = f.fileIdx AND f.fileKind = '계약서' " +
//            "WHERE f.fileIdx IS NULL")
//    Optional<Long> countByContractIsNull();
//    //첨부파일에 따라 등록증 미첨부 개수
//    @Query("SELECT COUNT(p.partnerIdx) " +
//            "FROM WellPartnerEntity p " +
//            "LEFT JOIN WellPartnerFIleStorageEntity pf ON p.partnerIdx = pf.partner " +
//            "LEFT JOIN WellFileStorageEntity f ON pf.fileIdx = f.fileIdx AND f.fileKind = '등록증' " +
//            "WHERE f.fileIdx IS NULL")
//    Optional<Long> countByRegistrationIsNull();
}
