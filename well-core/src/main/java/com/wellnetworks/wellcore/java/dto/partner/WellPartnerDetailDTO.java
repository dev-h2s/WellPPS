package com.wellnetworks.wellcore.java.dto.partner;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private String businessLicenseFile;
    private String contractDocumentFile;
    private String idCardFile;
    private String storePhotoFile;
    private String businessCardFile;

    public WellPartnerDetailDTO(WellPartnerEntity entity, WellDipositEntity diposit, String partnerUpperName
            , WellPartnerGroupEntity group, WellApikeyInEntity apikey, List<WellPartnerEntity> subPartners
            , String PartnerGroupName, List<WellFileStorageEntity> fileStorageEntities) {
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
        } else {
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

        for (WellFileStorageEntity fileDetailDTO : fileStorageEntities) {
            switch (fileDetailDTO.getFileKind()) {
                case "사업자등록증" -> this.businessLicenseFile = fileDetailDTO.getSavedFileName();
                case "계약서" -> this.contractDocumentFile = fileDetailDTO.getSavedFileName();
                case "대표자신분증" -> this.idCardFile = fileDetailDTO.getSavedFileName();
                case "매장사진" -> this.storePhotoFile = fileDetailDTO.getSavedFileName();
                case "대표자명함" -> this.businessCardFile = fileDetailDTO.getSavedFileName();
            }
        }
    }
}


