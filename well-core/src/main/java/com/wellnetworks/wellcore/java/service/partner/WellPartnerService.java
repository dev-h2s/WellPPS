package com.wellnetworks.wellcore.java.service.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerEntityBackup;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerUpdateDTO;
import com.wellnetworks.wellcore.java.repository.File.WellFileStorageRepository;
import com.wellnetworks.wellcore.java.repository.File.WellPartnerFileRepository;
import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerCreateDTO;
//import com.wellnetworks.wellcore.java.repository.File.WellFileStorageRepository;
import com.wellnetworks.wellcore.java.repository.Partner.*;
import com.wellnetworks.wellcore.java.repository.apikeyIn.WellApikeyInRepository;
import com.wellnetworks.wellcore.java.repository.backup.partner.*;
import com.wellnetworks.wellcore.java.repository.search.partnerSearch;
import com.wellnetworks.wellcore.java.service.File.WellFileStorageService;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WellPartnerService {

    @Autowired private WellPartnerRepository wellPartnerRepository;
    @Autowired private WellPartnerBackupRepository wellPartnerBackupRepository;
    @Autowired private WellPartnerGroupRepository wellPartnerGroupRepository;
    @Autowired private WellApikeyInRepository wellApikeyInRepository;
    @Autowired private WellFileStorageService fileStorageService;
    @Autowired private WellPartnerFileRepository partnerFileRepository;
    @Autowired private WellFileStorageRepository fileRepository;
    @PersistenceContext
    private EntityManager em;



    // 거래처 1개 조회
    public Optional<WellPartnerInfoDTO> getPartnerByPartnerIdx(String partnerIdx) {
        WellPartnerEntity partnerEntity = wellPartnerRepository.findByPartnerIdx(partnerIdx);

        if (partnerEntity != null) {
            List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerIdx);
            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity depositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

            Long registeredCount = wellPartnerRepository.countByTransactionStatus("등록");
            Long preRegisteredCount = wellPartnerRepository.countByTransactionStatus("가등록");
            Long managementCount = wellPartnerRepository.countByTransactionStatus("관리대상");
            Long suspendedCount = wellPartnerRepository.countByTransactionStatus("거래중지");

            return Optional.of(new WellPartnerInfoDTO(partnerEntity, fileStorages, depositEntity
                    , registeredCount, preRegisteredCount, managementCount, suspendedCount
                    ));
        } else {
            return Optional.empty();
        }
    }



    //거래처 리스트 조회
    public List<WellPartnerInfoDTO> getAllPartners() {
        List<WellPartnerEntity> partners = wellPartnerRepository.findAllByOrderByProductRegisterDateDesc();
        List<WellPartnerInfoDTO> partnerInfoList = new ArrayList<>();

        for (WellPartnerEntity partnerEntity : partners) {
            List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerEntity.getPartnerIdx());

            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

            Long registeredCount = wellPartnerRepository.countByTransactionStatus("등록");
            Long preRegisteredCount = wellPartnerRepository.countByTransactionStatus("가등록");
            Long managementCount = wellPartnerRepository.countByTransactionStatus("관리대상");
            Long suspendedCount = wellPartnerRepository.countByTransactionStatus("거래중지");

            WellPartnerInfoDTO partnerInfo = new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity
                    , registeredCount, preRegisteredCount, managementCount, suspendedCount
                    );
            partnerInfoList.add(partnerInfo);
        }

        return partnerInfoList;
    }






    //거래처 생성

    //p_code 랜덤 값
    public String generateUniquePartnerCode() {
        String partnerCode = null;
        boolean isUnique = false;
        while (!isUnique) {
            Random random = new Random();
            int randomNumber = 100000 + random.nextInt(900000); // 6자리 숫자 생성
            partnerCode = "pa-" + randomNumber;
            // 중복 확인
            WellPartnerEntity existingPartner = wellPartnerRepository.findByPartnerCode(partnerCode);
            // 중복되지 않으면 반복 중지
            if (existingPartner == null) {
                isUnique = true;
            }
        }
        return partnerCode;
    }

    @Transactional
    public void join(WellPartnerCreateDTO createDTO) throws Exception {
        // 거래처 그룹 정보 가져오기
        WellPartnerGroupEntity partnerGroup = wellPartnerGroupRepository.findByPartnerGroupId(createDTO.getPartnerGroupId());

        // API 연동 여부 확인 및 API 키 엔티티 가져오기
        WellApikeyInEntity apikeyIn = null; // 초기화
        if (createDTO.isInApiFlag() && createDTO.getApiKeyInIdx() != null) {
            apikeyIn = wellApikeyInRepository.findByApiKeyInIdx(createDTO.getApiKeyInIdx());
        }

        // API 연동 여부와 API 키가 설정되지 않은 경우 예외 처리
        if (createDTO.isInApiFlag() && apikeyIn == null) {
            throw new RuntimeException("해당 API 키를 찾을 수 없습니다.");
        }


        // 거래처 Entity 생성
        WellPartnerEntity partner = WellPartnerEntity.builder()
                .partnerCode(generateUniquePartnerCode())
                .partnerName(createDTO.getPartnerName())
                .partnerType(createDTO.getPartnerType())
                .specialPolicyCharge(createDTO.isSpecialPolicyCharge())
                .specialPolicyOpening(createDTO.isSpecialPolicyOpening())
                .partnerGroup(partnerGroup) // 거래처 그룹 설정
                .discountCategory(createDTO.getDiscountCategory())
                .salesManager(createDTO.getSalesManager())
                .inApiFlag(createDTO.isInApiFlag())
                .apiKey(apikeyIn) // apikey 설정
                .preApprovalNumber(createDTO.getPreApprovalNumber())
                .subscriptionDate(createDTO.getSubscriptionDate())
                .transactionStatus(createDTO.getTransactionStatus())
                .partnerUpperIdx(createDTO.getPartnerUpperIdx()) // 상부점_id 설정
                .ceoName(createDTO.getCeoName())
                .ceoTelephone(createDTO.getCeoTelephone())
                .partnerTelephone(createDTO.getPartnerTelephone())
                .emailAddress(createDTO.getEmailAddress())
                .commissionBankName(createDTO.getCommissionBankName())
                .commissionDepositAccount(createDTO.getCommissionDepositAccount())
                .commissionBankHolder(createDTO.getCommissionBankHolder())
                .registrationNumber(createDTO.getRegistrationNumber())
                .registrationAddress(createDTO.getRegistrationAddress())
                .registrationDetailAddress(createDTO.getRegistrationDetailAddress())
                .locationAddress(createDTO.getLocationAddress())
                .locationDetailAddress(createDTO.getLocationDetailAddress())
                .partnerMemo(createDTO.getPartnerMemo())
                .build();

        // 거래처 저장
        wellPartnerRepository.save(partner);

        // 파일 저장
        fileStorageService.saveFiles(createDTO, partner.getPartnerIdx());

        System.out.println();
    }


    //거래처 수정
    @Transactional
    public void update(WellPartnerUpdateDTO updateDTO) throws Exception {

        WellPartnerEntity partner = wellPartnerRepository.findByPartnerIdx(updateDTO.getPartnerIdx());
        System.out.println(updateDTO.getPartnerIdx());

        if (partner == null) {
            throw new RuntimeException("해당 파트너를 찾을 수 없습니다.");
        }

        // 거래처 그룹 정보 가져오기
        WellPartnerGroupEntity partnerGroup = wellPartnerGroupRepository.findByPartnerGroupId(updateDTO.getPartnerGroupId());

        // API 연동 여부 확인 및 API 키 엔티티 가져오기
        WellApikeyInEntity apikeyIn = null; // 초기화
        if (updateDTO.isInApiFlag() && updateDTO.getApiKeyInIdx() != null) {
            apikeyIn = wellApikeyInRepository.findByApiKeyInIdx(updateDTO.getApiKeyInIdx());
        }

        // API 연동 여부와 API 키가 설정되지 않은 경우 예외 처리
        if (updateDTO.isInApiFlag() && apikeyIn == null) {
            throw new RuntimeException("해당 API 키를 찾을 수 없습니다.");
        }


        partner.setPartnerGroup(partnerGroup); // 거래처 그룹 업데이트
        partner.setApiKey(apikeyIn); // API 키 업데이트
        partner.updateFromDTO(updateDTO);

        // 거래처 저장
        wellPartnerRepository.save(partner);

        System.out.println();
    }



    //거래처 삭제 (관련 엔티티 백업 후 삭제)
    @Transactional(rollbackOn = Exception.class)
    public Optional<WellPartnerInfoDTO> deletePartnerIdx(String partnerIdx) {

        // 거래처를 조회
        WellPartnerEntity partnerEntity = wellPartnerRepository.findByPartnerIdx(partnerIdx);


        if (partnerEntity != null) {
            List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerIdx);

            // 거래처가 가상계좌를 가지고 있는 경우, 예치금 정보를 가져옴
            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

            Long registeredCount = wellPartnerRepository.countByTransactionStatus("등록");
            Long preRegisteredCount = wellPartnerRepository.countByTransactionStatus("가등록");
            Long managementCount = wellPartnerRepository.countByTransactionStatus("관리대상");
            Long suspendedCount = wellPartnerRepository.countByTransactionStatus("거래중지");

            // 백업 엔티티에 복사
            WellPartnerEntityBackup partnerBackup = new WellPartnerEntityBackup();
            partnerBackup.setPartnerIdx(partnerIdx);
            partnerBackup.setPartnerGroupId(partnerEntity.getPartnerGroup().getPartnerGroupId());
            partnerBackup.setApiKeyInIdx(partnerEntity.getApiKey().getApiKeyInIdx());
            partnerBackup.setPartnerCode(partnerEntity.getPartnerCode());
            partnerBackup.setPartnerName(partnerEntity.getPartnerName());
            partnerBackup.setTransactionStatus(partnerEntity.getTransactionStatus());
            partnerBackup.setPartnerType(partnerEntity.getPartnerType());
            partnerBackup.setPartnerUpperIdx(partnerEntity.getPartnerUpperIdx());
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
            partnerBackup.setInApiFlag(partnerEntity.getInApiFlag());

            // 백업 테이블에 저장
            wellPartnerBackupRepository.save(partnerBackup);

            // 거래처 삭제
            wellPartnerRepository.delete(partnerEntity);

            return Optional.of(new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity
                                                    , registeredCount, preRegisteredCount, managementCount, suspendedCount));
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

    public Page<WellPartnerInfoDTO> searchPartner(Pageable pageable, List<partnerSearch> searchKeywords )
    {
        if (searchKeywords == null || searchKeywords.isEmpty()) {
            // 검색 조건이 없으면 모든 파트너 데이터 반환
            Page<WellPartnerEntity> partners = wellPartnerRepository.findAll(pageable);
            List<WellPartnerInfoDTO> dtos = partners.stream()
                    .map(WellPartnerInfoDTO::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(dtos, pageable, partners.getTotalElements());
        }

        Specification<WellPartnerEntity> searchSpecification = WellPartnerSearch.buildSpecification(searchKeywords);
        Page<WellPartnerEntity> searchResult = wellPartnerRepository.findAll(searchSpecification, pageable);
        List<WellPartnerInfoDTO> dtos = searchResult.stream()
                .map(WellPartnerInfoDTO::new)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, searchResult.getTotalElements());
    }



}
