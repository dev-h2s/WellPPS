package com.wellnetworks.wellcore.java.dto.partner;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPartnerDetailDTO {
    @Schema(description = "거래처 로그인 id로쓰는 코드")
    private String partnerCode;
    @Schema(description = "거래처 이름")
    private String partnerName;
    @Schema(description = "거래처 타입")
    private String partnerType;
    @Schema(description = "특수정책 계통")
    private Boolean specialPolicyOpening;
    @Schema(description = "특수정책 충전")
    private Boolean specialPolicyCharge;
    @Schema(description = "거래처 그룹 아이디")
    private Long partnerGroupId;
    @Schema(description = "거래처 그룹 이름")
    private String PartnerGroupName;
    @Schema(description = "충전할인율")
    private String discountCategory;
    @Schema(description = "영업담당자")
    private String salesManager;
    @Schema(description = "api연동여부")
    private boolean inApiFlag;
    @Schema(description = "api연동여부")
    private String apiKeyInIdx;
    @Schema(description = "사전승낙번호")
    private String preApprovalNumber;
    @Schema(description = "가입승인날짜")
    private LocalDate subscriptionDate;
    @Schema(description = "거래유무")
    private String transactionStatus;
    @Schema(description = "상부점idx", example = "상부점 idx")
    private String partnerUpperIdx;
    @Schema(description = "상부점이름", example = "상부점 idx")
    private String partnerUpperName;

    private List<WellSubPartnerDetailDTO> subPartners = new ArrayList<>(); // 하부점
    @Schema(description = "예치금잔액")
    private Integer dipositBalance;

    @Schema(description = "대표자명")
    private String ceoName;

    @Schema(description = "대표자전화번호")
    private String ceoTelephone;

    @Schema(description = "거래처 전화번호")
    private String partnerTelephone;

    @Schema(description = "이메일 주소")
    private String emailAddress;

    @Schema(description = "수수료입금계좌")
    private String commissionDepositAccount;

    @Schema(description = "수수료입금계좌은행명")
    private String commissionBankName;

    @Schema(description = "수수료입금계좌예금주")
    private String commissionBankHolder;

    @Schema(description = "사업자등록번호")
    private String registrationNumber;

    @Schema(description = "사업자등록증주소")
    private String registrationAddress;

    @Schema(description = "사업자등록증상세주소")
    private String registrationDetailAddress;

    @Schema(description = "사업자소재지주소")
    private String locationAddress;

    @Schema(description = "사업자소재지상세주소")
    private String locationDetailAddress;

    @Schema(description = "메모")
    private String partnerMemo;
    private WellPartnerFIleStorageEntity businessLicenseFile;
    private WellPartnerFIleStorageEntity contractDocumentFile;
    private WellPartnerFIleStorageEntity idCardFile;
    private WellPartnerFIleStorageEntity storePhotoFile;
    private WellPartnerFIleStorageEntity businessCardFile;

    public WellPartnerDetailDTO(WellPartnerEntity entity, WellDipositEntity diposit, String partnerUpperName
            , WellPartnerGroupEntity group, WellApikeyInEntity apikey, List<WellPartnerEntity> subPartners
            , String PartnerGroupName, List<WellPartnerFIleStorageEntity> fileStorageEntities) {
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

        for (WellPartnerFIleStorageEntity fileDetailDTO : fileStorageEntities) {
            switch (fileDetailDTO.getFile().getFileKind()) {
                case "사업자등록증" -> this.businessLicenseFile = fileDetailDTO;
                case "계약서" -> this.contractDocumentFile = fileDetailDTO;
                case "대표자신분증" -> this.idCardFile = fileDetailDTO;
                case "매장사진" -> this.storePhotoFile = fileDetailDTO;
                case "대표자명함" -> this.businessCardFile = fileDetailDTO;
            }
        }
    }
}


