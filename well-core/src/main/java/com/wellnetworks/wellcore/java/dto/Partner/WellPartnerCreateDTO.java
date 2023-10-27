package com.wellnetworks.wellcore.java.dto.Partner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WellPartnerCreateDTO {
    @JsonProperty("p_code")
    private String parterCode;
    @JsonProperty("p_name")
    private String partnerName;
    @JsonProperty("p_type")
    private String partnerType;
    @JsonProperty("special_policy_opening")
    private boolean specialPolicyOpening;
    @JsonProperty("special_policy_charge")
    private boolean specialPolicyCharge;

    @JsonProperty("p_group_id")
    private Long partnerGroupId; // 거래처 그룹 정보 추가

    @JsonProperty("discount_category") //충전할인율구분
    private String discountCategory;
    @JsonProperty("sales_manager") //영업담당자
    private String salesManager;

    @JsonProperty("in_api_flag")
    private boolean inApiFlag;
    @JsonProperty("api_key_in_idx")//APIKEY_idx
    private String apiKeyInIdx;

    @JsonProperty("pre_approval_number") //사전승낙번호
    private String preApprovalNumber;
    @JsonProperty("subdt") //가입승인일자
    private LocalDateTime subscriptionDate;
    @JsonProperty("trans_status") //거래유무
    private String transactionStatus;
    @JsonProperty("p_upper_idx") //상부점_id
    private String partnerUpperIdx;
    @JsonProperty("ceo_name") //대표자명
    private String ceoName;
    @JsonProperty("ceo_tel") //대표자전화번호
    private String ceoTelephone;
    @JsonProperty("p_tel") //사업장전화번호
    private String partnerTelephone;
    @JsonProperty("email_addr") //이메일주소
    private String emailAddress;
    @JsonProperty("commission_deposit_account") //수수료입금계좌
    private String commissionDepositAccount;
    @JsonProperty("commission_bank_name") //수수료입금계좌은행명
    private String commissionBankName;
    @JsonProperty("commission_bank_holder") //수수료입금계좌예금주
    private String commissionBankHolder;
    @JsonProperty("registration_number") //사업자등록번호
    private String registrationNumber;
    @JsonProperty("reg_addr") //사업자등록증주소
    private String registrationAddress;
    @JsonProperty("reg_detail_addr") //사업자등록증상세주소
    private String registrationDetailAddress;
    @JsonProperty("loc_addr") //사업자소재지주소
    private String locationAddress;
    @JsonProperty("loc_detail_addr") //사업자소재지상세주소
    private String locationDetailAddress;
    @JsonProperty("partner_memo") //메모
    private String partnerMemo;

    private List<MultipartFile> multipartFiles;

    public WellPartnerCreateDTO(){}

    @QueryProjection
    public WellPartnerCreateDTO(String parterCode, String partnerName, String partnerType, boolean specialPolicyOpening, boolean specialPolicyCharge, Long partnerGroupId, String discountCategory, String salesManager, boolean inApiFlag, String apiKeyInIdx, String preApprovalNumber, LocalDateTime subscriptionDate, String transactionStatus, String partnerUpperIdx, String ceoName, String ceoTelephone, String partnerTelephone, String emailAddress, String commissionDepositAccount, String commissionBankName, String commissionBankHolder, String registrationNumber, String registrationAddress, String registrationDetailAddress, String locationAddress, String locationDetailAddress, String partnerMemo) {
        this.parterCode = parterCode;
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
    }
}

