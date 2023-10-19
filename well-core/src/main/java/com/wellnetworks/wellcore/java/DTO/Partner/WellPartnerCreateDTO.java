package com.wellnetworks.wellcore.java.dto.Partner;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WellPartnerCreateDTO {
    private String partnerIdx;
    private String partnerCode;
    private String partnerName;
    private String partnerType;
    private String partnerGroup;
    private String discountCategory;
    private String salesManager;
    private String apiKey;
    private String preApprovalNumber;
    private LocalDateTime subscriptionDate;
    private String transactionStatus;
    private Integer dipositBalance;
    private String virtualAccount;
    private boolean specialPolicyOpening;
    private boolean specialPolicyCharge;
    private Long partnerUpperId;
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
    private String files;
    private String partnerMemo;
    private LocalDateTime salesTeamVisitDate;
    private String salesTeamVisitMemo;
//    private String commissionPolicy; 아직 안정해짐

    //생성
    @Builder
    public WellPartnerCreateDTO(WellPartnerEntity entity, WellApikeyInEntity apikey, WellFileStorageEntity file) {
        this.partnerIdx = entity.getPartnerIdx();
        this.partnerCode = entity.getPartnerCode();
        this.partnerName = entity.getPartnerName();
        this.partnerType = entity.getPartnerType();
        this.partnerGroup = entity.getPartnerGroup().toString();
        this.discountCategory = entity.getDiscountCategory();
        this.salesManager = entity.getSalesManager();
        if (apikey != null) {
            this.apiKey = apikey.getApiKeyIn();
        }
        this.preApprovalNumber = entity.getPreApprovalNumber();
        this.subscriptionDate = entity.getSubscriptionDate();
        this.transactionStatus = entity.getTransactionStatus();
        this.partnerUpperId = entity.getPartnerUpperId();
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
        if (file != null) {
            this.files = file.getFileKind();
        }
        this.partnerMemo = entity.getPartnerMemo();
        this.salesTeamVisitDate = entity.getSalesTeamVisitDate();
        this.salesTeamVisitMemo = entity.getSalesTeamVisitMemo();
    }

    public WellPartnerEntity toEntity() {
        return WellPartnerEntity.builder()
                .partnerIdx(partnerIdx)
                .partnerCode(partnerCode)
                .partnerName(partnerName)
                .partnerType(partnerType)
                .partnerGroup(toEntity().getPartnerGroup())
                .discountCategory(discountCategory)
                .salesManager(salesManager)
                .preApprovalNumber(preApprovalNumber)
                .subscriptionDate(subscriptionDate)
                .transactionStatus(transactionStatus)
                .partnerUpperId(partnerUpperId)
                .ceoName(ceoName)
                .ceoTelephone(ceoTelephone)
                .partnerTelephone(partnerTelephone)
                .emailAddress(emailAddress)
                .commissionDepositAccount(commissionDepositAccount)
                .commissionBankName(commissionBankName)
                .commissionBankHolder(commissionBankHolder)
                .registrationNumber(registrationNumber)
                .registrationAddress(registrationAddress)
                .registrationDetailAddress(registrationDetailAddress)
                .locationAddress(locationAddress)
                .locationDetailAddress(locationDetailAddress)
                .partnerMemo(partnerMemo)
                .salesTeamVisitDate(salesTeamVisitDate)
                .salesTeamVisitMemo(salesTeamVisitMemo)
                .build();

    }

    //동일한 거래처 나타내는지 판단
      @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WellPartnerCreateDTO other = (WellPartnerCreateDTO) obj;

        return partnerIdx != null && partnerIdx.equals(other.partnerIdx);
    }
}

