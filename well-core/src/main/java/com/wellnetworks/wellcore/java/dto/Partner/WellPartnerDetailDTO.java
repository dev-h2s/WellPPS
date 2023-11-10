package com.wellnetworks.wellcore.java.dto.Partner;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPartnerDetailDTO {
    private String partnerIdx;
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
    private LocalDateTime subscriptionDate;
    private String transactionStatus;

    private String partnerUpperIdx;
    private String partnerUpperName;

    private List<String> subPartners = new ArrayList<>();
    private List<String> subPartnerNames = new ArrayList<>();

    private int dipositBalance;
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

    private List<String> fileKinds = new ArrayList<>();

    public WellPartnerDetailDTO(WellPartnerEntity entity, List<WellPartnerFIleStorageEntity> fileStorages, WellDipositEntity diposit
            , String partnerUpperName, WellPartnerGroupEntity group, WellApikeyInEntity apikey, List<WellPartnerEntity> subPartners
            , String PartnerGroupName) {
        this.partnerIdx = entity.getPartnerIdx();
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
        this.apiKeyInIdx = apikey.getApiKeyInIdx();

        this.preApprovalNumber = entity.getPreApprovalNumber();
        this.subscriptionDate = entity.getSubscriptionDate();
        this.transactionStatus = entity.getTransactionStatus();
        this.partnerUpperIdx = entity.getPartnerUpperIdx();
        if (partnerUpperIdx != null) {
            this.partnerUpperName = partnerUpperName;
        }
        else {
            this.partnerUpperName = null;
        }
        this.subPartners = subPartners.stream().map(WellPartnerEntity::getPartnerIdx).collect(Collectors.toList());
        for (WellPartnerEntity subPartner : subPartners) {
            subPartnerNames.add(subPartner.getPartnerName());
        }


        if (diposit != null) {
            this.dipositBalance = diposit.getDipositBalance();
        }
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
            if (fileStorage != null) {
                String fileKind = fileStorage.getFile().getFileKind(); // 파일 저장소 엔티티의 종류 가져오기
                // fileKind와 원하는 종류를 비교하여 일치하는 경우에만 리스트에 추가
                if (fileKind.equals(fileKind)) {
                    fileKinds.add(fileStorage.getFile().getFileKind()); // 첨부파일 엔티티를 리스트에 추가
                }
            }
        }
    }
}


