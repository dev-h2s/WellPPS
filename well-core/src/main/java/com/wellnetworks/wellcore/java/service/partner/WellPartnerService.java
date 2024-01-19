package com.wellnetworks.wellcore.java.service.partner;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.backup.partner.WellPartnerEntityBackup;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerPermissionGroupEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.dto.Partner.*;
import com.wellnetworks.wellcore.java.dto.Partner.sign.WellPartnerSignCreateDTO;
import com.wellnetworks.wellcore.java.dto.Partner.sign.WellPartnerSignInfoDTO;
import com.wellnetworks.wellcore.java.repository.File.WellPartnerFileRepository;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerGroupRepository;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerPermissionGroupRepository;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerUserRepository;
import com.wellnetworks.wellcore.java.repository.apikeyIn.WellApikeyInRepository;
import com.wellnetworks.wellcore.java.repository.backup.partner.WellPartnerBackupRepository;
import com.wellnetworks.wellcore.java.service.File.WellFileStorageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WellPartnerService {

    @Autowired
    private WellPartnerRepository wellPartnerRepository;
    @Autowired
    private WellPartnerBackupRepository wellPartnerBackupRepository;
    @Autowired
    private WellPartnerGroupRepository wellPartnerGroupRepository;
    @Autowired
    private WellApikeyInRepository wellApikeyInRepository;
    @Autowired
    private WellFileStorageService fileStorageService;
    @Autowired
    private WellPartnerFileRepository partnerFileRepository;
    @Autowired
    private WellPartnerUserRepository partnerUserRepository;
    @Autowired
    private WellPartnerPermissionGroupRepository permissionGroupRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    // 거래처 상세 조회
    public Optional<WellPartnerDetailDTO> getDetailPartnerByPartnerIdx(String partnerIdx) {
//        try {
        WellPartnerEntity entity = wellPartnerRepository.findByPartnerIdx(partnerIdx);
        if (entity == null) {
            throw new EntityNotFoundException("해당 파트너를 찾을 수 없습니다. 파트너 ID: " + partnerIdx);
        }

        List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerIdx);
        WellDipositEntity depositEntity = entity.getVirtualAccount() != null ? entity.getVirtualAccount().getDeposit() : null;
        String partnerUpperName = entity.getPartnerUpperIdx() != null ? wellPartnerRepository.findPartnerNameByPartnerIdxSafely(entity.getPartnerUpperIdx()) : null;

        List<WellPartnerEntity> subPartnerEntities = wellPartnerRepository.findSubPartnersByPartnerUpperIdx(partnerIdx);
        Long groupId = entity.getPartnerGroup() != null ? entity.getPartnerGroup().getPartnerGroupId() : null;
        String partnerGroupName = groupId != null ? entity.getPartnerGroup().getPartnerGroupName() : null;

        WellApikeyInEntity apikeyInEntity = entity.getApiKey();

        WellPartnerDetailDTO dto = new WellPartnerDetailDTO(entity, fileStorages, depositEntity, partnerUpperName, entity.getPartnerGroup(), apikeyInEntity, subPartnerEntities, partnerGroupName);

        return Optional.of(dto);
//        } catch (EntityNotFoundException e) {
//            return Optional.empty();
//        } catch (Exception e) {
//            throw new RuntimeException("거래처 상세 정보 조회 중 오류 발생: " + e.getMessage(), e);
//        }
    }


    // 거래처 검색
    public Page<WellPartnerSearchDTO> searchPartnerList(String partnerName, String ceoName, String ceoTelephone, String partnerCode, String address, String writer, String partnerTelephone
            , LocalDate startDate, LocalDate endDate
            , String discountCategory, String partnerType, String salesManager, String transactionStatus, String regionAddress
            , String partnerUpperIdx, Boolean hasBusinessLicense, Boolean hasContractDocument, Pageable pageable) {

        try {
            // 1. registrationStatusIsApproved() 스펙을 추가합니다.
            Specification<WellPartnerEntity> approvedStatusSpec = registrationStatusIsApproved();

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
                    .and(PartnerSpecification.contractDocumentEquals(hasContractDocument))
                    .and(approvedStatusSpec);

            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "productRegisterDate"));

            Page<WellPartnerEntity> partners = wellPartnerRepository.findAll(spec, pageable);

            List<WellPartnerSearchDTO> partnerInfoList = new ArrayList<>();

            for (WellPartnerEntity partnerEntity : partners) {
                List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerEntity.getPartnerIdx());

                WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
                WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

                Integer dipositBalance = dipositEntity != null && dipositEntity.getDipositBalance() != null ? dipositEntity.getDipositBalance() : 0;

                partnerUpperIdx = partnerEntity.getPartnerUpperIdx();
                String partnerUpperName = null;
                if (partnerEntity.getPartnerUpperIdx() != null) {
                    partnerUpperName = wellPartnerRepository.findPartnerNameByPartnerIdxSafely(partnerUpperIdx);
                }
                WellPartnerSearchDTO partnerInfo = new WellPartnerSearchDTO(partnerEntity, fileStorages, dipositBalance, partnerUpperName);
                partnerInfoList.add(partnerInfo);
            }

            return new PageImpl<>(partnerInfoList, pageable, partners.getTotalElements());
        } catch (Exception e) {
            // 여기에 예외 처리 로직 추가
            throw new RuntimeException("거래처 검색 중 오류 발생: " + e.getMessage(), e);
        }
    }

    // 거래처 리스트에서 회원가입이 승인된 것만 나올수 있게끔
    public static Specification<WellPartnerEntity> registrationStatusIsApproved() {
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("registrationStatus"), "승인");
        };
    }

    //거래처 리스트 조회
    public Page<WellPartnerInfoDTO> getAllPartners(Pageable pageable) {
        try {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "productRegisterDate"));

//        Page<WellPartnerEntity> partners = wellPartnerRepository.findAll(pageable);
            Page<WellPartnerEntity> partners = wellPartnerRepository.findAll(registrationStatusIsApproved(), pageable);
            List<WellPartnerInfoDTO> partnerInfoList = new ArrayList<>();


            for (WellPartnerEntity partnerEntity : partners) {
                List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerEntity.getPartnerIdx());

                WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
                WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;


                String partnerUpperIdx = partnerEntity.getPartnerUpperIdx();
                String partnerUpperName = null;
                if (partnerEntity.getPartnerUpperIdx() != null) {
                    partnerUpperName = wellPartnerRepository.findPartnerNameByPartnerIdxSafely(partnerUpperIdx);
                }


                WellPartnerInfoDTO partnerInfo = new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity
                        , partnerUpperName
                );
                partnerInfoList.add(partnerInfo);

            }

            Long totalPartnerCount = partners.getTotalElements();

            return new PageImpl<>(partnerInfoList, pageable, totalPartnerCount);
        } catch (Exception e) {
            // 여기에 예외 처리 로직 추가
            throw new RuntimeException("거래처 리스트 조회 중 오류 발생: " + e.getMessage(), e);
        }
    }

    // 거래처 리스트에서 회원가입이 승인이 아닌 것만 나올수 있게끔
    public static Specification<WellPartnerEntity> registrationStatusIsNotAndDeleteStatusIsFalse() {
        return (root, query, criteriaBuilder) -> {
            Predicate registrationStatusNotApproved = criteriaBuilder.notEqual(root.get("registrationStatus"), "승인");
            Predicate deleteStatusFalse = criteriaBuilder.equal(root.get("deleteStatus"), false);

            return criteriaBuilder.and(registrationStatusNotApproved, deleteStatusFalse);
        };
    }

    //거래처 회원가입 리스트 조회
    public Page<WellPartnerSignInfoDTO> getAllPartnerSign(Pageable pageable, String ceoTelephone, String ceoName, String partnerName) {
        try {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "signRequestDate"));

            Page<WellPartnerEntity> partners = wellPartnerRepository.findAll(registrationStatusIsNotAndDeleteStatusIsFalse(), pageable);
            List<WellPartnerSignInfoDTO> partnerInfoList = new ArrayList<>();

            WellPartnerEntity partner = WellPartnerEntity.builder()
                    .build();

            String discountCategory = partner.getDiscountCategory();
            String registrationStatus = partner.getRegistrationStatus();



            Specification<WellPartnerEntity> approvedStatusSpec = registrationStatusIsNotAndDeleteStatusIsFalse();


           // 검색 Like 조건
            Specification<WellPartnerEntity> spec = Specification.where(PartnerSpecification.partnerCeoNameContains(ceoName))
                    .and(PartnerSpecification.partnerNameContains(partnerName))
                    .and(PartnerSpecification.partnerCeoTelephoneContains(ceoTelephone))
                    .and(approvedStatusSpec);

            if(discountCategory !=null){
                spec = spec.and(PartnerSpecification.discountCategoryEquals(discountCategory));
            }
            if(registrationStatus !=null){
                spec = spec.and(PartnerSpecification.transactionStatusEquals(registrationStatus));
            }


            for (WellPartnerEntity partnerEntity : partners) {
                List<WellPartnerFIleStorageEntity> fileStorages = partnerFileRepository.findByPartnerIdx(partnerEntity.getPartnerIdx());

                WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
                WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;


                String partnerUpperIdx = partnerEntity.getPartnerUpperIdx();
                String partnerUpperName = null;
                if (partnerEntity.getPartnerUpperIdx() != null) {
                    partnerUpperName = wellPartnerRepository.findPartnerNameByPartnerIdxSafely(partnerUpperIdx);
                }


                WellPartnerSignInfoDTO partnerSignInfo = new WellPartnerSignInfoDTO(partnerEntity, fileStorages, dipositEntity
                        , partnerUpperName
                );
                partnerInfoList.add(partnerSignInfo);

            }

            Long totalPartnerCount = partners.getTotalElements();

            return new PageImpl<>(partnerInfoList, pageable, totalPartnerCount);
        }
        catch (Exception e) {
            // 여기에 예외 처리 로직 추가
            throw new RuntimeException("거래처 리스트 조회 중 오류 발생: " + e.getMessage(), e);
        }
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
        private static final String ALLOWED_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()";
        private static final int TEMP_PWD_LENGTH = 10;
        private static final SecureRandom RANDOM = new SecureRandom();
        private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

        public static String[] generateRandomPassword() {
            StringBuilder builder = new StringBuilder(TEMP_PWD_LENGTH);

            for (int i = 0; i < TEMP_PWD_LENGTH; i++) {
                builder.append(ALLOWED_STRING.charAt(RANDOM.nextInt(ALLOWED_STRING.length())));
            }

            String rawPassword = builder.toString();
            String encryptedPassword = ENCODER.encode(rawPassword); // 암호화된 비밀번호를 반환
            return new String[]{rawPassword, encryptedPassword};
        }
    }

    //거래처 생성
    @Transactional(rollbackOn = Exception.class)
    public String join(WellPartnerCreateDTO createDTO) throws Exception {
        // 거래처 그룹 정보 가져오기
        WellPartnerGroupEntity partnerGroup = wellPartnerGroupRepository.findByPartnerGroupId(createDTO.getPartnerGroupId());

        // API 연동 여부 확인 및 API 키 엔티티 가져오기
        WellApikeyInEntity apikeyIn = null; // 초기화
        if (createDTO.isInApiFlag() && createDTO.getApiKeyInIdx() != null) {
            apikeyIn = wellApikeyInRepository.findByApiKeyInIdx(createDTO.getApiKeyInIdx());
        }


        String[] passwords = WellPartnerService.PasswordUtil.generateRandomPassword();
        String tempPasswordPlainText = passwords[0]; // 사용자에게 전달할 임시 비밀번호 평문
        String tempPasswordEncrypted = passwords[1]; // 데이터베이스에 저장할 암호화된 비밀번호


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
                    //회원가입 관리 관련 컬럼
                    .registrationStatus("승인")
                    .rejectionReason(createDTO.getRejectionReason())
                    .writer(createDTO.getWriter()) //작성자
                    .event(createDTO.getEvent())//이벤트
                    .visitStatus(createDTO.getVisitStatus())//방문요청여부
                    .openingVisitRequestDate(createDTO.getOpeningVisitRequestDate())//개통점방문희망일자
                    .openingVisitDecideDate(createDTO.getOpeningVisitDecideDate())//개통점방문확정일자
                    .openingProgress(createDTO.getOpeningProgress())//개통점진행도
                    .isOpeningFlag(createDTO.getIsOpeningFlag())//개통점전환여부
                    .openingNote(createDTO.getOpeningNote())//개통점신청비고
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
                    .tmpPwd(tempPasswordEncrypted)
                    .isFirstLogin(true)
                    .isPasswordResetRequired(true)
                    .partnerManagerGroupKey(group) // 연관 관계 설정
                    .build();

            partnerUserRepository.save(userEntity);

            System.out.println("거래처, 거래처유저 생성완료");
        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("거래처 생성 중 오류 발생", e);
        }
        System.out.println("거래처, 거래처유저 생성완료");
        return tempPasswordPlainText;
    }


    //거래처 회원가입 신청 생성
    @Transactional(rollbackOn = Exception.class)
    public String signJoin(WellPartnerSignCreateDTO signCreateDTO) throws Exception {
        // 거래처 그룹 정보 가져오기
        WellPartnerGroupEntity partnerGroup = wellPartnerGroupRepository.findByPartnerGroupId(signCreateDTO.getPartnerGroupId());

        // API 연동 여부 확인 및 API 키 엔티티 가져오기
        WellApikeyInEntity apikeyIn = null; // 초기화
        if (signCreateDTO.isInApiFlag() && signCreateDTO.getApiKeyInIdx() != null) {
            apikeyIn = wellApikeyInRepository.findByApiKeyInIdx(signCreateDTO.getApiKeyInIdx());
        }

        // API 연동 여부와 API 키가 설정되지 않은 경우 예외 처리
        if (signCreateDTO.isInApiFlag() && apikeyIn == null) {
            throw new RuntimeException("해당 API 키를 찾을 수 없습니다.");
        }

        // department 값을 기준으로 WellEmployeeGroupEntity 객체 조회
        String department = signCreateDTO.getDepartment();
        Optional<WellPartnerPermissionGroupEntity> groupOptional = permissionGroupRepository.findByDepartment(department);

        WellPartnerPermissionGroupEntity group = groupOptional.orElseThrow(()
                -> new IllegalArgumentException("Invalid department: " + department));


        String partnerIdx = UUID.randomUUID().toString();

        try {
            LocalDateTime requestDate = LocalDateTime.now(ZoneId.systemDefault());
            // 거래처 Entity 생성
            WellPartnerEntity partner = WellPartnerEntity.builder()
                    .partnerIdx(partnerIdx)
                    .partnerCode(generateUniquePartnerCode())
                    .ceoName(signCreateDTO.getCeoName())
                    .partnerName(signCreateDTO.getPartnerName())
                    .emailAddress(signCreateDTO.getEmailAddress())
                    .ceoTelephone(signCreateDTO.getCeoTelephone())
                    .registrationNumber(signCreateDTO.getRegistrationNumber())
                    .discountCategory(signCreateDTO.getDiscountCategory())
                    .visitStatus(signCreateDTO.getVisitStatus())
                    .desiredDate(signCreateDTO.getDesiredDate())
                    .registrationAddress(signCreateDTO.getRegistrationAddress())
                    .registrationDetailAddress(signCreateDTO.getRegistrationDetailAddress())
                    .partnerTelephone(signCreateDTO.getPartnerTelephone())
                    .termsOfUse(signCreateDTO.getTermsOfUse())
                    .signRequestDate(requestDate)
                    .inApiFlag(signCreateDTO.isInApiFlag())
                    .apiKey(apikeyIn) // apikey 설정

                    //회원가입 관리 관련 컬럼
                    .registrationStatus("대기")
                    .visitStatus(signCreateDTO.getVisitStatus())//방문요청여부
                    .partnerGroup(partnerGroup) // 거래처 그룹 설정
                    .build();

            //p_code
            String userId = partner.getPartnerCode();

            // 거래처 저장
            wellPartnerRepository.save(partner);

            // 파일 저장
            fileStorageService.saveSignFiles(signCreateDTO, partner.getPartnerIdx());


        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("거래처 생성 중 오류 발생", e);
        }
        System.out.println("거래처, 회원가입");
        return signCreateDTO.getPartnerName();
    }


    //거래처 수정
    @Transactional(rollbackOn = Exception.class)
    public void update(String partnerIdx, WellPartnerUpdateDTO updateDTO) throws Exception {
        try {
            // DTO를 통해 엔티티 업데이트
            WellPartnerEntity partner = wellPartnerRepository.findByPartnerIdx(partnerIdx);
            BeanUtils.copyProperties(updateDTO, partner);

            // 거래처 그룹 및 API 키 설정
            WellPartnerGroupEntity partnerGroup = wellPartnerGroupRepository.findByPartnerGroupId(updateDTO.getPartnerGroupId());
            WellApikeyInEntity apikeyIn = null;
            if (updateDTO.isInApiFlag() && updateDTO.getApiKeyInIdx() != null) {
                apikeyIn = wellApikeyInRepository.findByApiKeyInIdx(updateDTO.getApiKeyInIdx());
            }

            if (partnerGroup == null) {
                throw new RuntimeException("해당 거래처 그룹을 찾을 수 없습니다.");
            }

            if (updateDTO.isInApiFlag() && apikeyIn == null) {
                throw new RuntimeException("해당 API 키를 찾을 수 없습니다.");
            }

            partner.setPartnerGroup(partnerGroup);
            partner.setApiKey(apikeyIn);

            fileStorageService.updateFiles(updateDTO, partnerIdx);

            // 엔티티의 업데이트 메서드 호출
            partner.updateFromDTO(updateDTO);

        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("거래처 수정 중 오류 발생", e);
        }
    }


    //거래처 삭제 (관련 엔티티 백업 후 삭제)
    @Transactional(rollbackOn = Exception.class)
    public WellPartnerInfoDTO deletePartnerIdx(String partnerIdx) {
        WellPartnerEntity partnerEntity = wellPartnerRepository.findByPartnerIdx(partnerIdx);
        if (partnerEntity == null) {
            throw new EntityNotFoundException("해당하는 거래처를 찾을 수 없습니다.");
        }

        // 수정이 필요한 부분 시작
        WellApikeyInEntity apiKeyInEntity = partnerEntity.getApiKey();
        if (apiKeyInEntity != null) {
            // apiKey가 null이 아닌 경우에만 추가 동작을 수행
            String apiKeyInIdx = String.valueOf(apiKeyInEntity.getApiKeyInIdx());
            // 삭제 등의 동작 수행
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
            partnerBackup.setApiKeyInIdx(null);
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
            partnerBackup.setOpeningFlag(partnerEntity.getIsOpeningFlag());
            partnerBackup.setOpeningNote(partnerEntity.getOpeningNote());
            partnerBackup.setInApiFlag(partnerEntity.getInApiFlag());
            partnerBackup.setRegistrationStatus(partnerEntity.getRegistrationStatus());
            partnerBackup.setRejectionReason(partnerEntity.getRejectionReason());
//        partnerBackup.setReviewDate(partnerEntity.getReviewDate());
//        partnerBackup.setReviewer(partnerEntity.getReviewer());
//        partnerBackup.setTermsOfUse(partnerEntity.getTermsOfUse());
//        partnerBackup.setSignRequestDate(partnerEntity.getSignRequestDate());
//        partnerBackup.setDesiredDate(partnerEntity.getDesiredDate());

            // 백업 테이블에 저장
            wellPartnerBackupRepository.save(partnerBackup);

            // 거래처 삭제
            wellPartnerRepository.delete(partnerEntity);

            return new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity, partnerUpperName);
        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("거래처 삭제 중 오류 발생", e);
        }
    }

    //거래처 회원가입 삭제
    @Transactional(rollbackOn = Exception.class)
    public int deletePartnerSign(String partnerIdx) {
        WellPartnerEntity partnerEntity = wellPartnerRepository.findByPartnerIdx(partnerIdx);
        if (partnerEntity == null) {
            throw new EntityNotFoundException("해당하는 거래처를 찾을 수 없습니다.");
        }

        partnerEntity.setDeleteStatusOn();
        wellPartnerRepository.save(partnerEntity);

        return 1;

    }
}
