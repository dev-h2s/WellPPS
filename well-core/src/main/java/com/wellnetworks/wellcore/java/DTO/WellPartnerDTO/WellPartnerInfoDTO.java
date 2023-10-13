package com.wellnetworks.wellcore.java.dto.WellPartnerDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WellPartnerInfoDTO {
    private String partnerIdx;
    private String partnerCode;
    private String tableID;
    private String partnerName;
    private String transactionStatus;
    private String partnerType;
    private Long partnerUpperId;
    private String partnerTelephone;
    private LocalDateTime productRegisterDate;
    private LocalDateTime productModifyDate;
    private String salesManager;
    private String ceoName;
    private String ceoTelephone;
    private String registrationAddress;
    private String registrationDetailAddress;
    private String locationAddress;
    private String locationDetailAddress;
    private String commisionType;
    private String discountCategory;
    private String region;
    private LocalDateTime subscriptionDate;
    private boolean specialPolicyOpening;
    private boolean specialPolicyCharge;
    private String password;
    private String preApprovalNumber;
    private String emailAddress;
    private String registrationNumber;
    private String partnerMemo;
    private LocalDateTime salesTeamVisitDate;
    private String salesTeamVisitMemo;
    private String commissionDepositAccount;
    private String commissionBankName;
    private String commissionBankHolder;
    private String writer;
    private String event;
    private LocalDateTime openingVisitRequestDate;
    private LocalDateTime openingVisitDecideDate;
    private String openingProgress;
    private boolean openingFlag;
    private String openingNote;


    //동일한 거래처 나타내는지 판단
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WellPartnerInfoDTO other = (WellPartnerInfoDTO) obj;

        return partnerIdx != null && partnerIdx.equals(other.partnerIdx);
    }
}
