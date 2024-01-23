package com.wellnetworks.wellcore.java.dto.partner;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPartnerDetailDTO {
    private String partnerCode;
    private String partnerName;
    private String partnerType;
    private Boolean specialPolicyOpening;
    private Boolean specialPolicyCharge;

    private Long partnerGroupId;
    private String PartnerGroupName;

    private String discountCategory;
    private String salesManager;

    private boolean inApiFlag;
    private String apiKeyInIdx;

    private String preApprovalNumber;
    private LocalDate subscriptionDate;
    private String transactionStatus;

    private String partnerUpperIdx;
    private String partnerUpperName;

    private List<WellSubPartnerDetailDTO> subPartners = new ArrayList<>();

    private Integer dipositBalance;
    private String ceoName;
    private String ceoTelephone;
    private String partnerTelephone;
    private String emailAddress;
    private String commissionDepositAccount;
    private String commissionBankName;
    private String commissionBankHolder;
    private String registrationNumber;
    private String registrationAddress;
    private String registrationDetailAddress;
    private String locationAddress;
    private String locationDetailAddress;
    private String partnerMemo;

    private List<WellFileDetailDTO> fileDetails = new ArrayList<>();

    public WellPartnerDetailDTO(WellPartnerEntity entity, List<WellPartnerFIleStorageEntity> fileStorages, WellDipositEntity diposit
            , String partnerUpperName, WellPartnerGroupEntity group, WellApikeyInEntity apikey, List<WellPartnerEntity> subPartners
            , String PartnerGroupName) {
        this.partnerCode = entity.getPartnerCode();
        this.partnerName = entity.getPartnerName();
        this.partnerType = entity.getPartnerType();
        this.specialPolicyOpening = entity.getSpecialPolicyOpening();
        this.specialPolicyCharge = entity.getSpecialPolicyCharge();
        if (group != null) {
            this.partnerGroupId = group.getPartnerGroupId();
            this.PartnerGroupName = PartnerGroupName;
        }
        this.discountCategory = entity.getDiscountCategory();
        this.salesManager = entity.getSalesManager();

        this.inApiFlag = entity.getInApiFlag();
        if (apikey != null) {
            this.apiKeyInIdx = apikey.getApiKeyInIdx();
        } else {
            this.apiKeyInIdx = null; // API 키가 없는 경우 null 처리
        }

        this.preApprovalNumber = entity.getPreApprovalNumber();
        this.subscriptionDate = entity.getSubscriptionDate() != null ? LocalDate.from(entity.getSubscriptionDate()) : null;

        this.transactionStatus = entity.getTransactionStatus();
        this.partnerUpperIdx = entity.getPartnerUpperIdx();
        if (partnerUpperIdx != null) {
            this.partnerUpperName = partnerUpperName;
        }
        else {
            this.partnerUpperName = null;
        }

        for (WellPartnerEntity subPartner : subPartners) {
            if (subPartner != null && subPartner.getPartnerIdx() != null) {
                WellSubPartnerDetailDTO fileDetail = new WellSubPartnerDetailDTO();
                fileDetail.setSubPartnerIdx(subPartner.getPartnerIdx());
                fileDetail.setSubPartnerName(subPartner.getPartnerName());
                this.subPartners.add(fileDetail);
            }
        }

        this.dipositBalance = diposit != null ? diposit.getDipositBalance() : 0;

        this.ceoName = entity.getCeoName();
        this.ceoTelephone = entity.getCeoTelephone();
        this.partnerTelephone = entity.getPartnerTelephone();
        this.emailAddress = entity.getEmailAddress();
        this.commissionDepositAccount = entity.getCommissionDepositAccount();
        this.commissionBankName = entity.getCommissionBankName();
        this.commissionBankHolder = entity.getCommissionBankHolder();
        this.registrationNumber = entity.getRegistrationNumber();
        this.registrationAddress = entity.getRegistrationAddress();
        this.registrationDetailAddress = entity.getRegistrationDetailAddress();
        this.locationAddress = entity.getLocationAddress();
        this.locationDetailAddress = entity.getLocationDetailAddress();
        this.partnerMemo = entity.getPartnerMemo();
        for (WellPartnerFIleStorageEntity fileStorage : fileStorages) {
            if (fileStorage != null && fileStorage.getFile() != null) {
                WellFileDetailDTO fileDetail = new WellFileDetailDTO();
                fileDetail.setFileId(fileStorage.getFile().getId());
                fileDetail.setOriginFileName(fileStorage.getFile().getOriginFileName());
                fileDetail.setFileKind(fileStorage.getFile().getFileKind());
                this.fileDetails.add(fileDetail);
            }
        }
        List<String> requiredFileKinds = Arrays.asList("사업자등록증", "계약서", "대표자신분증", "매장사진", "대표자명함");

        // 파일 종류별로 첫 번째 항목을 찾고, 없으면 빈 정보를 추가
        for (String fileKind : requiredFileKinds) {
            WellFileDetailDTO fileDetail = fileStorages.stream()
                    .filter(fileStorage -> fileStorage != null && fileStorage.getFile() != null && fileStorage.getFile().getFileKind().equals(fileKind))
                    .map(fileStorage -> new WellFileDetailDTO(fileStorage.getFile().getId(), fileStorage.getFile().getOriginFileName(), fileStorage.getFile().getFileKind()))
                    .findFirst()
                    .orElse(new WellFileDetailDTO(null, null, fileKind));

            this.fileDetails.add(fileDetail);
        }

        // 중복 제거
        this.fileDetails = this.fileDetails.stream().distinct().collect(Collectors.toList());
    }
}


