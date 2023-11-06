package com.wellnetworks.wellcore.java.dto.Partner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPartnerCreateDTO {
    private String partnerCode;
    private String partnerName;
    private String partnerType;
    private boolean specialPolicyOpening;
    private boolean specialPolicyCharge;

    private Long partnerGroupId; // 거래처 그룹 정보 추가

    private String discountCategory;
    private String salesManager;

    private boolean inApiFlag;
    private String apiKeyInIdx;

    private String preApprovalNumber;
    private LocalDateTime subscriptionDate;
    private String transactionStatus;
    private String partnerUpperIdx;
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

    private List<MultipartFile> businessLicenseFiles;
    private List<MultipartFile> contractDocumentFiles;
    private List<MultipartFile> idCardFiles;
    private List<MultipartFile> storePhotoFiles;
    private List<MultipartFile> businessCardFiles;

    //거래처유저
    private String department;
    private String partnerManagerGroupKey;
    private String partnerIdentification;
    private String partnerUserPwd;
    private String permissions;
    private String tmpPwd;
    private LocalDateTime tmpPwdExpiration;
    private Integer tmpPwdCount;
    private LocalDateTime tmpPwdDate;
    private Boolean isPhoneVerified;
    private String phoneVerificationCode;
    private Integer phoneVerificationAttempts;
    private LocalDateTime phoneVerificationExpiration;
    private LocalDateTime phoneVerificationSentTime;
    private LocalDateTime partnerUserRegisterDate;


    @QueryProjection
    public WellPartnerCreateDTO(String partnerCode, String partnerName, String partnerType, boolean specialPolicyOpening, boolean specialPolicyCharge, Long partnerGroupId
                                , String discountCategory, String salesManager, boolean inApiFlag, String apiKeyInIdx, String preApprovalNumber
                                , LocalDateTime subscriptionDate, String transactionStatus, String partnerUpperIdx, String ceoName, String ceoTelephone
                                , String partnerTelephone, String emailAddress, String commissionDepositAccount, String commissionBankName, String commissionBankHolder
                                , String registrationNumber, String registrationAddress, String registrationDetailAddress, String locationAddress, String locationDetailAddress
                                , String partnerMemo, List<String> fileKinds
                                , String partnerManagerGroupKey, String partnerIdentification, String partnerUserPwd, String permissions
                                , String tmpPwd, LocalDateTime tmpPwdExpiration, Integer tmpPwdCount, LocalDateTime tmpPwdDate, Boolean isPhoneVerified
                                , String phoneVerificationCode, Integer phoneVerificationAttempts, LocalDateTime phoneVerificationExpiration, LocalDateTime phoneVerificationSentTime, LocalDateTime partnerUserRegisterDate
                                , String department
    ) {


        this.partnerCode = partnerCode;
        this.partnerName = partnerName;
        this.partnerType = partnerType;
        this.specialPolicyOpening = specialPolicyOpening;
        this.specialPolicyCharge = specialPolicyCharge;
        this.partnerGroupId = partnerGroupId;
        this.discountCategory = discountCategory;
        this.salesManager = salesManager;
        this.inApiFlag = inApiFlag;
        this.apiKeyInIdx = apiKeyInIdx;
        this.preApprovalNumber = preApprovalNumber;
        this.subscriptionDate = subscriptionDate;
        this.transactionStatus = transactionStatus;
        this.partnerUpperIdx = partnerUpperIdx;
        this.ceoName = ceoName;
        this.ceoTelephone = ceoTelephone;
        this.partnerTelephone = partnerTelephone;
        this.emailAddress = emailAddress;
        this.commissionDepositAccount = commissionDepositAccount;
        this.commissionBankName = commissionBankName;
        this.commissionBankHolder = commissionBankHolder;
        this.registrationNumber = registrationNumber;
        this.registrationAddress = registrationAddress;
        this.registrationDetailAddress = registrationDetailAddress;
        this.locationAddress = locationAddress;
        this.locationDetailAddress = locationDetailAddress;
        this.partnerMemo = partnerMemo;
        this.fileKinds = fileKinds;

        //거래처유저
        this.partnerManagerGroupKey = partnerManagerGroupKey;
        this.partnerIdentification = partnerIdentification;
        this.partnerUserPwd = partnerUserPwd;
        this.permissions = permissions;
        this.tmpPwd = tmpPwd;
        this.tmpPwdExpiration = tmpPwdExpiration;
        this.tmpPwdCount = tmpPwdCount;
        this.tmpPwdDate = tmpPwdDate;
        this.isPhoneVerified = isPhoneVerified;
        this.phoneVerificationCode = phoneVerificationCode;
        this.phoneVerificationAttempts = phoneVerificationAttempts;
        this.phoneVerificationExpiration = phoneVerificationExpiration;
        this.phoneVerificationSentTime = phoneVerificationSentTime;
        this.partnerUserRegisterDate = partnerUserRegisterDate;
        this.department = department;
    }
}

