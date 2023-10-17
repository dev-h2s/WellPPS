package com.wellnetworks.wellcore.java.service;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.backup.partner.*;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.*;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WellPartnerService {

    @Autowired private WellPartnerRepository wellPartnerRepository;
    @Autowired private WellPartnerUserRepository wellPartnerUserRepository;
    @Autowired private WellPartnerPermissionGroupRepository wellPartnerPermissionGroupRepository;
    @Autowired private WellPartnerGroupRepository wellPartnerGroupRepository;
    @Autowired private WellApikeyInRepository wellApikeyInRepository;
    @Autowired private WellVirtualAccountRepository wellVirtualAccountRepository;
    @Autowired private WellDipositRepository wellDipositRepository;
    @Autowired private WellOpeningRepository wellOpeningRepository;
    @Autowired private WellFakeRegistrationRepository wellFakeRegistrationRepository;
    @Autowired private WellPartnerFileRepository wellPartnerFileRepository;
    @Autowired private WellFakeRegistrationFIleBackupRepository getWellPartnerFakeRegistrationBackupRepository;
    @Autowired private WellVirtualAccountFileRepository wellVirtualAccountFileRepository;
    @Autowired private WellFileStorageRepository wellFileStorageRepository;

    @Autowired private WellPartnerBackupRepository wellPartnerBackupRepository;
    @Autowired private WellApikeyInBackupRepository wellApikeyInBackupRepository;
    @Autowired private WellDipositBackupRepository wellDipositBackupRepository;
    @Autowired private WellFakeRegistrationBackupRepository wellFakeRegistrationBackupRepository;
    @Autowired private WellFileBackupRepository wellFileBackupRepository;
    @Autowired private WellOpeningBackupRepository wellOpeningBackupRepository;
    @Autowired private WellFakeRegistrationFIleBackupRepository wellPartnerFakeRegistrationBackupRepository;
    @Autowired private WellPartnerFileBackupRepository wellPartnerFileBackupRepository;
    @Autowired private WellPartnerGroupBackupRepository wellPartnerGroupBackupRepository;
    @Autowired private WellPartnerPermissionGroupBackupRepository wellPartnerPermissionGroupBackupRepository;
    @Autowired private WellPartnerUserBackupRepository wellPartnerUserBackupRepository;
    @Autowired private WellVirtualAccountBackupRepository wellVirtualAccountBackupRepository;
    @Autowired private WellVirtualAccountFileBackupRepository wellVirtualAccountFileBackupRepository;

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

    //거래처 삭제 (관련 엔티티 백업 후 삭제)
    @Transactional(rollbackOn = Exception.class)
    public Optional<WellPartnerInfoDTO> deletePartnerIdx(String partnerIdx) {

        // 거래처를 조회
        WellPartnerEntity partnerEntity = wellPartnerRepository.findByPartnerIdx(partnerIdx);
        //거래처유저조회
        WellPartnerUserEntity partnerUserEntity = wellPartnerUserRepository.findByPartnerId(partnerEntity.getPartnerIds());
        //거래처유저그룹 조회
//        WellPartnerPermissionGroupEntity partnerPermissionGroupEntity = wellPartnerPermissionGroupRepository.findByPartnerManagerGroupKey(partnerUserEntity.getPartnerManagerGroupKey());
        //거래처그룹 조회
//        WellPartnerGroupEntity wellPartnerGroupEntity = wellPartnerGroupRepository.findByPartnerGroupId(partnerEntity.getPartnerGroupId());
        //내부apikey 조회
//        WellApikeyInEntity wellApikeyInEntity = wellApikeyInRepository.findByApiKeyInIdx(partnerEntity.getApiKeyInIdx());
        //가상계좌 조회
//        WellVirtualAccountEntity wellVirtualAccountEntity = wellVirtualAccountRepository.findByPartnerIdx(partnerEntity.getPartnerIdx());
        //예치금 조회
        //개통 조회
        //부정가입현황 조회
        //거래처파일 조회
        //부정가입현황파일 조회
        //가상계좌파일 조회
        //첨부파일 조회

        if (partnerEntity != null) {
            List<WellFileStorageEntity> fileStorages = partnerEntity.getFiles().stream()
                    .map(WellPartnerFIleStorageEntity::getFile)
                    .collect(Collectors.toList());

            // 거래처가 가상계좌를 가지고 있는 경우, 예치금 정보를 가져옴
            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

            // 백업 엔티티에 복사
            //거래처
            WellPartnerEntityBackup partnerBackup = new WellPartnerEntityBackup();
            partnerBackup.setPartnerIdx(partnerIdx); // someValue를 실제 ID 값으로 설정
            BeanUtils.copyProperties(partnerEntity, partnerBackup);
            //거래처유저
            WellPartnerUserEntityBackup partnerUserBackup = new WellPartnerUserEntityBackup();
            partnerUserBackup.setPartnerId(partnerUserEntity.getPartnerId());
            BeanUtils.copyProperties(partnerUserEntity, partnerUserBackup);
            //거래처유저그룹
//            WellPartnerPermissionGroupEntityBackup partnerPermissionGroupBackup = new WellPartnerPermissionGroupEntityBackup();
//            partnerPermissionGroupBackup.setPartnerManagerGroupKey(partnerPermissionGroupEntity.getPartnerManagerGroupKey());
//            BeanUtils.copyProperties(partnerPermissionGroupEntity, partnerPermissionGroupBackup);
            //거래처그룹
//            WellPartnerGroupEntityBackup partnerGroupEntityBackup = new WellPartnerGroupEntityBackup();
//            partnerGroupEntityBackup.setPartnerGroupId(wellPartnerGroupEntity.getPartnerGroupId());
//            BeanUtils.copyProperties(wellPartnerGroupEntity, partnerGroupEntityBackup);
            //내부apikey
//            WellApikeyInEntityBackup apikeyInEntityBackup = new WellApikeyInEntityBackup();
//            apikeyInEntityBackup.setApiKeyInIdx(wellApikeyInEntity.getApiKeyInIdx());
//            BeanUtils.copyProperties(wellApikeyInEntity, apikeyInEntityBackup);
            //가상계좌
//            WellVirtualAccountEntityBackup virtualAccountEntityBackup = new WellVirtualAccountEntityBackup();
//            virtualAccountEntityBackup.setPartnerIdx(wellVirtualAccountEntity.getPartnerIdx());
//            BeanUtils.copyProperties(wellVirtualAccountEntity, virtualAccountEntityBackup);
            //예치금
            //개통
            //부정가입현황
            //거래처파일
            //부정가입현황파일
            //가상계좌파일
            //첨부파일


            // 백업 테이블에 저장
            wellPartnerBackupRepository.save(partnerBackup);
            wellPartnerUserBackupRepository.save(partnerUserBackup);
//            wellPartnerPermissionGroupBackupRepository.save(partnerPermissionGroupBackup);
//            wellPartnerGroupBackupRepository.save(partnerGroupEntityBackup);
//            wellApikeyInBackupRepository.save(apikeyInEntityBackup);
//            wellVirtualAccountBackupRepository.save(virtualAccountEntityBackup);

            // 거래처 삭제
            wellPartnerRepository.delete(partnerEntity);
            em.remove(partnerUserEntity);
//            em.remove(partnerPermissionGroupEntity);
//            em.remove(wellPartnerGroupEntity);
//            em.remove(wellApikeyInEntity);
//            em.remove(virtualAccountEntity);

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
