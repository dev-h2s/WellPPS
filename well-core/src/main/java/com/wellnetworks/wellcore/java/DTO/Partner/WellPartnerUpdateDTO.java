package com.wellnetworks.wellcore.java.dto.Partner;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WellPartnerUpdateDTO {
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

    public WellPartnerUpdateDTO(WellPartnerEntity entity, WellApikeyInEntity apikey, WellDipositEntity diposit, WellVirtualAccountEntity account, WellFileStorageEntity file) {
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
        if (diposit != null) {
            this.dipositBalance = diposit.getDipositBalance();
        }
        if (account != null) {
            this.virtualAccount = account.getVirtualBankName() + " " + account.getVirtualAccount();
        }
        this.specialPolicyOpening = entity.isSpecialPolicyOpening();
        this.specialPolicyCharge = entity.isSpecialPolicyCharge();
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

      //동일한 거래처 나타내는지 판단
      @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WellPartnerUpdateDTO other = (WellPartnerUpdateDTO) obj;

        return partnerIdx != null && partnerIdx.equals(other.partnerIdx);
    }
}

