package com.wellnetworks.wellcore.java.service.partner;

import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerEntityBackup;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerPermissionGroupEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerDetailDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerUpdateDTO;
import com.wellnetworks.wellcore.java.repository.File.WellPartnerFileRepository;
import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerCreateDTO;
import com.wellnetworks.wellcore.java.repository.Partner.*;
import com.wellnetworks.wellcore.java.repository.apikeyIn.WellApikeyInRepository;
import com.wellnetworks.wellcore.java.repository.backup.partner.*;
import com.wellnetworks.wellcore.java.service.File.WellFileStorageService;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Autowired private WellPartnerUserRepository partnerUserRepository;
    @Autowired private WellPartnerPermissionGroupRepository permissionGroupRepository;

    // 거래처 1개 조회
    public Optional<WellPartnerInfoDTO> getPartnerByPartnerIdx(String partnerIdx) {
        return Optional.ofNullable(wellPartnerRepository.findByPartnerIdx(partnerIdx))
                .map(entity -> {
                    List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerIdx);
                    WellVirtualAccountEntity virtualAccountEntity = entity.getVirtualAccount();
                    WellDipositEntity depositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : new WellDipositEntity();
                    String partnerUpperIdx = entity.getPartnerUpperIdx();
                    String partnerUpperName = partnerUpperIdx != null ? wellPartnerRepository.findPartnerNameByPartnerIdxSafely(partnerUpperIdx) : null;
                    return new WellPartnerInfoDTO(entity, fileStorages, depositEntity, partnerUpperName);
                });
    }

    // 거래처 상세 조회
    public Optional<WellPartnerDetailDTO> getDetailPartnerByPartnerIdx(String partnerIdx) {
        return Optional.ofNullable(wellPartnerRepository.findByPartnerIdx(partnerIdx))
                .map(entity -> {
                    List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerIdx);

                    WellVirtualAccountEntity virtualAccountEntity = entity.getVirtualAccount();
                    WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : new WellDipositEntity();

                    String partnerUpperIdx = entity.getPartnerUpperIdx();
                    String partnerUpperName = partnerUpperIdx != null ? wellPartnerRepository.findPartnerNameByPartnerIdxSafely(partnerUpperIdx) : null;

                    List<WellPartnerEntity> subPartnerEntities = wellPartnerRepository.findSubPartnersByPartnerUpperIdx(partnerIdx);
                    if (subPartnerEntities.isEmpty()) {
                        subPartnerEntities = wellPartnerRepository.findSubPartnersByPartnerUpperIdx(partnerUpperIdx);
                    }

                    Long groupId = entity.getPartnerGroup().getPartnerGroupId();

                    WellPartnerGroupEntity partnerGroupEntity = wellPartnerGroupRepository.findByPartnerGroupId(groupId);
                    String PartnerGroupName = groupId != null ? entity.getPartnerGroup().getPartnerGroupName() : null;
                    WellApikeyInEntity apikeyInEntity = wellApikeyInRepository.findByApiKeyInIdx(entity.getApiKey().getApiKeyInIdx());

                    WellPartnerDetailDTO dto = new WellPartnerDetailDTO(entity, fileStorages, dipositEntity, partnerUpperName, partnerGroupEntity, apikeyInEntity, subPartnerEntities, PartnerGroupName);

                    // 하위 거래처들의 이름을 설정
                    dto.setSubPartnerNames(subPartnerEntities.stream().map(WellPartnerEntity::getPartnerName).collect(Collectors.toList()));

                    return dto;
                });
    }



    //거래처 조회
    public List<WellPartnerInfoDTO> searchPartnerList(String partnerName, String ceoName, String ceoTelephone, String partnerCode, String address, String writer, String partnerTelephone
            , LocalDate startDate, LocalDate endDate
            , String discountCategory, String partnerType, String salesManager, String transactionStatus, String regionAddress
            , String partnerUpperIdx, Boolean hasBusinessLicense, Boolean hasContractDocument) {
        Specification<WellPartnerEntity> spec = Specification.where(PartnerSpecification.partnerNameContains(partnerName))
                .and(PartnerSpecification.partnerCeoNameContains(ceoName))
                .and(PartnerSpecification.partnerCeoTelephoneContains(ceoTelephone))
                .and(PartnerSpecification.partnerCodeContains(partnerCode))
                .and(PartnerSpecification.partnerAddressContains(address))
                .and(PartnerSpecification.writerContains(writer))
                .and(PartnerSpecification.partnerTelephoneContains(partnerTelephone))
                .and(PartnerSpecification.productRegisterDateBetween(startDate, endDate))
                .and(PartnerSpecification.discountCategoryEquals(discountCategory))
                .and(PartnerSpecification.partnerTypeEquals(partnerType))
                .and(PartnerSpecification.salesManagerEquals(salesManager))
                .and(PartnerSpecification.transactionStatusEquals(transactionStatus))
                .and(PartnerSpecification.regionAddressContains(regionAddress))
                .and(PartnerSpecification.partnerUpperNameEquals(partnerUpperIdx))
                .and(PartnerSpecification.businessLicenseEquals(hasBusinessLicense))
                .and(PartnerSpecification.contractDocumentEquals(hasContractDocument));

        List<WellPartnerEntity> partners = wellPartnerRepository.findAll(spec);
        if (partners == null) {
            return Collections.emptyList();
        }

        List<WellPartnerInfoDTO> partnerInfoList = new ArrayList<>();

        for (WellPartnerEntity partnerEntity : partners) {
            List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerEntity.getPartnerIdx());

            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : new WellDipositEntity();

            partnerUpperIdx = partnerEntity.getPartnerUpperIdx();
            String partnerUpperName = null;
            if (partnerEntity.getPartnerUpperIdx() != null) {
                partnerUpperName = wellPartnerRepository.findPartnerNameByPartnerIdxSafely(partnerUpperIdx);
            }
            WellPartnerInfoDTO partnerInfo = new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity, partnerUpperName);
            partnerInfoList.add(partnerInfo);
        }

        return partnerInfoList;
    }

    //거래처 리스트 조회

    public List<WellPartnerInfoDTO> getAllPartners() {
        List<WellPartnerEntity> partners = wellPartnerRepository.findAllByOrderByProductRegisterDateDesc();
        List<WellPartnerInfoDTO> partnerInfoList = new ArrayList<>();

        Long totalBusinessLicenseCount = 0L;
        Long totalContractDocumentCount = 0L;

        Long registeredCount = wellPartnerRepository.countByTransactionStatus("등록");
        Long preRegisteredCount = wellPartnerRepository.countByTransactionStatus("가등록");
        Long managementCount = wellPartnerRepository.countByTransactionStatus("관리대상");
        Long suspendedCount = wellPartnerRepository.countByTransactionStatus("거래중지");

        for (WellPartnerEntity partnerEntity : partners) {
            List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerEntity.getPartnerIdx());

            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

            Long businessLicenseCount = fileStorages.stream()
                    .filter(fileStorage -> "사업자등록증".equals(fileStorage.getFile().getFileKind()))
                    .count();

            Long contractDocumentCount = fileStorages.stream()
                    .filter(fileStorage -> "계약서".equals(fileStorage.getFile().getFileKind()))
                    .count();

            String partnerUpperIdx = partnerEntity.getPartnerUpperIdx();
            String partnerUpperName = null;
            if (partnerEntity.getPartnerUpperIdx() != null) {
                partnerUpperName = wellPartnerRepository.findPartnerNameByPartnerIdxSafely(partnerUpperIdx);
            }


            WellPartnerInfoDTO partnerInfo = new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity
                    , registeredCount, preRegisteredCount, managementCount, suspendedCount
                    , partnerUpperName, businessLicenseCount, contractDocumentCount
            );
            partnerInfoList.add(partnerInfo);

            totalBusinessLicenseCount += businessLicenseCount;
            totalContractDocumentCount += contractDocumentCount;
        }

        Long totalPartnerCount = (long) partners.size();
        Long NonBusinessLicenseCount = totalPartnerCount - totalBusinessLicenseCount;
        Long NonContractDocumentCount = totalPartnerCount - totalContractDocumentCount;

        for (WellPartnerInfoDTO partnerInfo : partnerInfoList) {
            partnerInfo.setBusinessLicenseCount(NonBusinessLicenseCount);
            partnerInfo.setContractDocumentCount(NonContractDocumentCount);
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

    //패스워드 랜덤
    public class PasswordUtil {
        private static final String ALLOWED_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        private static final int TEMP_PWD_LENGTH = 10;
        private static final SecureRandom RANDOM = new SecureRandom();

        public static String generateRandomPassword() {
            StringBuilder builder = new StringBuilder(TEMP_PWD_LENGTH);

            for (int i = 0; i < TEMP_PWD_LENGTH; i++) {
                builder.append(ALLOWED_STRING.charAt(RANDOM.nextInt(ALLOWED_STRING.length())));
            }

            return builder.toString();
        }
    }

    @Transactional(rollbackOn = Exception.class)
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

        // department 값을 기준으로 WellEmployeeGroupEntity 객체 조회
        String department = createDTO.getDepartment();
        Optional<WellPartnerPermissionGroupEntity> groupOptional = permissionGroupRepository.findByDepartment(department);

        WellPartnerPermissionGroupEntity group = groupOptional.orElseThrow(()
                -> new IllegalArgumentException("Invalid department: " + department));

        // 휴대폰 인증 코드의 만료 시간 설정. 예를 들어, 현재 시간으로부터 10분 후로 설정.
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String partnerIdx = UUID.randomUUID().toString();

        try {
            // 거래처 Entity 생성
            WellPartnerEntity partner = WellPartnerEntity.builder()
                    .partnerIdx(partnerIdx)
                    .partnerCode(generateUniquePartnerCode())
                    .partnerName(createDTO.getPartnerName())
                    .partnerType(createDTO.getPartnerType())
                    .specialPolicyCharge(createDTO.getSpecialPolicyCharge())
                    .specialPolicyOpening(createDTO.getSpecialPolicyOpening())
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

            //p_code
            String userId = partner.getPartnerCode();

            // 거래처 저장
            wellPartnerRepository.save(partner);

            // 파일 저장
            fileStorageService.saveFiles(createDTO, partner.getPartnerIdx());

            //거래처 유저
            WellPartnerUserEntity userEntity = WellPartnerUserEntity.builder()
                    .partnerIdx(partnerIdx) //생성되는 idx
                    .partnerIdentification(userId) // 로그인 id(p_code)
                    .isPhoneVerified(createDTO.getIsPhoneVerified()) // user-휴대폰 인증 여부
                    .phoneVerificationCode(createDTO.getPhoneVerificationCode()) // user-휴대폰 인증 코드
                    .phoneVerificationAttempts(createDTO.getPhoneVerificationAttempts()) // user-휴대폰 인증 시도 횟수
                    .phoneVerificationExpiration(expirationTime) // user-휴대폰 인증 만료 시간
                    .tmpPwd(PasswordUtil.generateRandomPassword())
                    .partnerManagerGroupKey(group) // 연관 관계 설정
                    .build();

            partnerUserRepository.save(userEntity);

            System.out.println("거래처, 거래처유저 생성완료");
        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("거래처 생성 중 오류 발생", e);
        }
    }


    //거래처 수정
    @Transactional(rollbackOn = Exception.class)
    public void update(String partnerIdx, WellPartnerUpdateDTO updateDTO) throws Exception {
        // 파트너 조회
        WellPartnerEntity partner = wellPartnerRepository.findByPartnerIdx(partnerIdx);

        // 파트너가 없으면 예외 처리
        if (partner == null) {throw new RuntimeException("해당 파트너를 찾을 수 없습니다.");}

        // 거래처 그룹 정보 가져오기
        WellPartnerGroupEntity partnerGroup = wellPartnerGroupRepository.findByPartnerGroupId(updateDTO.getPartnerGroupId());

        // API 연동 여부 확인 및 API 키 엔티티 가져오기
        WellApikeyInEntity apikeyIn = null;
        if (updateDTO.isInApiFlag() && updateDTO.getApiKeyInIdx() != null) {
            apikeyIn = wellApikeyInRepository.findByApiKeyInIdx(updateDTO.getApiKeyInIdx());
        }

        // 거래처 그룹이나 API 키를 찾을 수 없는 경우 예외 처리
        if (partnerGroup == null) {throw new RuntimeException("해당 거래처 그룹을 찾을 수 없습니다.");}

        if (updateDTO.isInApiFlag() && apikeyIn == null) {throw new RuntimeException("해당 API 키를 찾을 수 없습니다.");}

        try {
            // DTO를 통해 엔티티 업데이트
            BeanUtils.copyProperties(updateDTO, partner);

            // 거래처 그룹 및 API 키 설정
            partner.setPartnerGroup(partnerGroup);
            partner.setApiKey(apikeyIn);

            // 엔티티의 업데이트 메서드 호출
            partner.updateFromDTO(updateDTO);

            System.out.println("거래처 수정 완료");
        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("거래처 수정 중 오류 발생", e);
        }
    }



    //거래처 삭제 (관련 엔티티 백업 후 삭제)
    @Transactional(rollbackOn = Exception.class)
    public Optional<WellPartnerInfoDTO> deletePartnerIdx(String partnerIdx) {

        // 거래처를 조회
        WellPartnerEntity partnerEntity = wellPartnerRepository.findByPartnerIdx(partnerIdx);

        if (partnerEntity == null) {
            throw new RuntimeException("해당 파트너를 찾을 수 없습니다.");
        }
        try {
            String partnerUpperIdx = partnerEntity.getPartnerUpperIdx();
            String partnerUpperName = null;
            if (partnerEntity.getPartnerUpperIdx() != null) {
                partnerUpperName = wellPartnerRepository.findPartnerNameByPartnerIdxSafely(partnerUpperIdx);
            }

            List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerIdx);

            // 거래처가 가상계좌를 가지고 있는 경우, 예치금 정보를 가져옴
            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

            // 백업 엔티티에 복사
            WellPartnerEntityBackup partnerBackup = new WellPartnerEntityBackup();
            partnerBackup.setPartnerIdx(partnerIdx);
            partnerBackup.setPartnerGroupId(partnerEntity.getPartnerGroup().getPartnerGroupId());
            partnerBackup.setApiKeyInIdx(partnerEntity.getApiKey().getApiKeyInIdx());
            partnerBackup.setPartnerCode(null);
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
            partnerBackup.setSubscriptionDate(partnerEntity.getSubscriptionDate());
            partnerBackup.setSpecialPolicyOpening(partnerEntity.getSpecialPolicyOpening());
            partnerBackup.setSpecialPolicyCharge(partnerEntity.getSpecialPolicyCharge());
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

            return Optional.of(new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity, partnerUpperName));
        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("거래처 삭제 중 오류 발생", e);
        }
    }
}
