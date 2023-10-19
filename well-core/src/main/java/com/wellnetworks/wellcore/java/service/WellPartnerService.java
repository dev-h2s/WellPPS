package com.wellnetworks.wellcore.java.service;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.backup.partner.*;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.opening.WellOpeningEntity;
import com.wellnetworks.wellcore.java.domain.partner.*;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerUpdateDTO;
import com.wellnetworks.wellcore.java.repository.File.WellFileStorageRepository;
import com.wellnetworks.wellcore.java.repository.File.WellPartnerFileRepository;
import com.wellnetworks.wellcore.java.repository.File.WellVirtualAccountFileRepository;
import com.wellnetworks.wellcore.java.repository.Partner.*;
import com.wellnetworks.wellcore.java.repository.account.WellDipositRepository;
import com.wellnetworks.wellcore.java.repository.account.WellVirtualAccountRepository;
import com.wellnetworks.wellcore.java.repository.apikeyIn.WellApikeyInRepository;
import com.wellnetworks.wellcore.java.repository.backup.partner.*;
import com.wellnetworks.wellcore.java.repository.member.WellGroupRepository;
import com.wellnetworks.wellcore.java.repository.opening.WellOpeningRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.lang.reflect.Member;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.wellnetworks.wellcore.java.domain.partner.QWellPartnerEntity.wellPartnerEntity;

@Service
public class WellPartnerService {

    @Autowired private WellPartnerRepository wellPartnerRepository;

    @Autowired private WellPartnerBackupRepository wellPartnerBackupRepository;


    @PersistenceContext
    private EntityManager em;

    // 거래처 1개 조회
    public Optional<WellPartnerInfoDTO> getPartnerByPartnerIdx(String partnerIdx) {
        WellPartnerEntity partnerEntity = wellPartnerRepository.findByPartnerIdx(partnerIdx);

        // 해당 거래처와 연결된 파일 정보를 가져와서 리스트로 정리
        if (partnerEntity != null) {
            List<WellFileStorageEntity> fileStorages = partnerEntity.getFiles().stream()
                    .map(WellPartnerFIleStorageEntity::getFile)
                    .collect(Collectors.toList());

            // 거래처가 가상계좌를 가지고 있는 경우, 예치금 정보를 가져옴
            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

            return Optional.of(new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity));
        } else {
            return Optional.empty();
        }
    }

    //거래처 리스트 조회
    public List<WellPartnerInfoDTO> getAllPartners() {
        List<WellPartnerEntity> partners = wellPartnerRepository.findAll();
        List<WellPartnerInfoDTO> partnerInfoList = new ArrayList<>();

        for (WellPartnerEntity partnerEntity : partners) {
            List<WellFileStorageEntity> fileStorages = partnerEntity.getFiles().stream()
                    .map(WellPartnerFIleStorageEntity::getFile)
                    .collect(Collectors.toList());

            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

            WellPartnerInfoDTO partnerInfo = new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity);
            partnerInfoList.add(partnerInfo);
        }

        return partnerInfoList;
    }

    //거래처 생성
//    @Transactional
//    public Long save(final BoardRequestDto params) {
//        WellPartnerEntity entity = wellPartnerRepository.save(params.toEntity());
//        return entity.getPartnerIdx();
//    }







    //거래처 삭제 (관련 엔티티 백업 후 삭제)
    @Transactional(rollbackOn = Exception.class)
    public Optional<WellPartnerInfoDTO> deletePartnerIdx(String partnerIdx) {

        // 거래처를 조회
        WellPartnerEntity partnerEntity = wellPartnerRepository.findByPartnerIdx(partnerIdx);


        if (partnerEntity != null) {
            List<WellFileStorageEntity> fileStorages = partnerEntity.getFiles().stream()
                    .map(WellPartnerFIleStorageEntity::getFile)
                    .collect(Collectors.toList());

            // 거래처가 가상계좌를 가지고 있는 경우, 예치금 정보를 가져옴
            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

            // 백업 엔티티에 복사
            WellPartnerEntityBackup partnerBackup = new WellPartnerEntityBackup();
            partnerBackup.setPartnerIdx(partnerIdx);
            partnerBackup.setPartnerId(partnerEntity.getPartnerId().getPartnerId());
            partnerBackup.setPartnerGroupId(partnerEntity.getPartnerGroup().getPartnerGroupId());
            partnerBackup.setApiKeyInIdx(partnerEntity.getApiKey().getApiKeyInIdx());
            partnerBackup.setPartnerCode(partnerEntity.getPartnerCode());
            partnerBackup.setPartnerName(partnerEntity.getPartnerName());
            partnerBackup.setTransactionStatus(partnerEntity.getTransactionStatus());
            partnerBackup.setPartnerType(partnerEntity.getPartnerType());
            partnerBackup.setPartnerUpperId(partnerEntity.getPartnerUpperId());
            partnerBackup.setPartnerTelephone(partnerEntity.getPartnerTelephone());
            partnerBackup.setProductRegisterDate(partnerEntity.getProductRegisterDate());
            partnerBackup.setProductModifyDate(partnerEntity.getProductModifyDate());
            partnerBackup.setSalesManager(partnerEntity.getSalesManager());
            partnerBackup.setCeoName(partnerEntity.getCeoName());
            partnerBackup.setCeoTelephone(partnerEntity.getCeoTelephone());
            partnerBackup.setRegistrationAddress(partnerEntity.getRegistrationAddress());
            partnerBackup.setRegistrationDetailAddress(partnerEntity.getRegistrationDetailAddress());
            partnerBackup.setLocationAddress(partnerEntity.getLocationAddress());
            partnerBackup.setLocationDetailAddress(partnerEntity.getLocationDetailAddress());
            partnerBackup.setCommisionType(partnerEntity.getCommisionType());
            partnerBackup.setSize(partnerEntity.getSize());
            partnerBackup.setPage(partnerEntity.getPage());
            partnerBackup.setDiscountCategory(partnerEntity.getDiscountCategory());
            partnerBackup.setRegion(partnerEntity.getRegion());
            partnerBackup.setSubscriptionDate(partnerEntity.getSubscriptionDate());
            partnerBackup.setSpecialPolicyOpening(partnerEntity.isSpecialPolicyOpening());
            partnerBackup.setSpecialPolicyCharge(partnerEntity.isSpecialPolicyCharge());
            partnerBackup.setPassword(partnerEntity.getPassword());
            partnerBackup.setPreApprovalNumber(partnerEntity.getPreApprovalNumber());
            partnerBackup.setEmailAddress(partnerEntity.getEmailAddress());
            partnerBackup.setRegistrationNumber(partnerEntity.getRegistrationNumber());
            partnerBackup.setPartnerMemo(partnerEntity.getPartnerMemo());
            partnerBackup.setSalesTeamVisitDate(partnerEntity.getSalesTeamVisitDate());
            partnerBackup.setSalesTeamVisitMemo(partnerEntity.getSalesTeamVisitMemo());
            partnerBackup.setCommissionDepositAccount(partnerEntity.getCommissionDepositAccount());
            partnerBackup.setCommissionBankName(partnerEntity.getCommissionBankName());
            partnerBackup.setCommissionBankHolder(partnerEntity.getCommissionBankHolder());
            partnerBackup.setWriter(partnerEntity.getWriter());
            partnerBackup.setEvent(partnerEntity.getEvent());
            partnerBackup.setOpeningVisitRequestDate(partnerEntity.getOpeningVisitRequestDate());
            partnerBackup.setOpeningVisitDecideDate(partnerEntity.getOpeningVisitDecideDate());
            partnerBackup.setOpeningProgress(partnerEntity.getOpeningProgress());
            partnerBackup.setOpeningFlag(partnerEntity.isOpeningFlag());
            partnerBackup.setOpeningNote(partnerEntity.getOpeningNote());

            // 백업 테이블에 저장
            wellPartnerBackupRepository.save(partnerBackup);

            // 거래처 삭제
            wellPartnerRepository.delete(partnerEntity);

            return Optional.of(new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity));
        } else {
            // 삭제 대상이 없을 경우 빈 Optional 반환
            return Optional.empty();
        }
    }


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


    //    public WellPartnerInfoDTO getPartnerInfo(String partnerIdx) {
//        WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(partnerIdx);
//        List<WellFileCountDTO> attachments = getFileCountsByPartnerIdx(partnerIdx);
//        WellPartnerInfoDTO partnerInfoDTO = new WellPartnerInfoDTO(partnerEntity);
//        partnerInfoDTO.setAttachments(attachments);
//        return partnerInfoDTO;
//    }

//    public List<WellFileCountDTO> getFileCountsByPartnerIdx(String partnerIdx) {
//        List<WellFileCountDTO> fileCounts = new ArrayList<>();
//
//        // 파일 종류별 개수 가져오기
//        int registrationCount = fileStorageRepository.countByFileKindAndPartnerFileStorage_Partner_PartnerIdx("등록증", partnerIdx);
//        int contractCount = fileStorageRepository.countByFileKindAndPartnerFileStorage_Partner_PartnerIdx("계약서", partnerIdx);
//
//        WellFileCountDTO registrationDTO = new WellFileCountDTO("등록증", registrationCount);
//        WellFileCountDTO contractDTO = new WellFileCountDTO("계약서", contractCount);
//
//        fileCounts.add(registrationDTO);
//        fileCounts.add(contractDTO);
//
//        return fileCounts;
//    }
}
