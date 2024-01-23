package com.wellnetworks.wellcore.java.dto.Partner;

import com.querydsl.core.annotations.QueryProjection;
import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Validated
public class WellPartnerCreateDTO {
    private String partnerCode;
//    @NotBlank(message = "거래처명은 필수 입력 항목입니다.")
    private String partnerName;
//    @NotBlank(message = "거래처 출력은 필수 입력 항목입니다.")
    private String partnerType;
    private Boolean specialPolicyOpening;
    private Boolean specialPolicyCharge;

    private Long partnerGroupId; // 거래처 그룹 정보 추가

//    @NotBlank(message = "충전할인율은 필수 입력 항목입니다.")
    private String discountCategory;
//    @NotBlank(message = "영업담당자는 필수 입력 항목입니다.")
    private String salesManager;

//    @NotNull(message = "api연동여부는 필수 입력 항목입니다.")
    private boolean inApiFlag;
    @Nullable
    private String apiKeyInIdx;

    private String preApprovalNumber;
    private LocalDateTime subscriptionDate;
//    @NotBlank(message = "거래유무는 필수 입력 항목입니다.")
    private String transactionStatus;
    private String partnerUpperIdx;
//    @NotBlank(message = "ceo이름은 필수 입력 항목입니다.")
    private String ceoName;
//    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호를 입력하세요.")
//    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    private String ceoTelephone;
//    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호를 입력하세요.")
//    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    private String partnerTelephone;
//    @Email(message = "올바른 이메일 주소를 입력하세요.")
//    @NotBlank(message = "이메일 주소는 필수 입력 항목입니다.")
    private String emailAddress;
    private String commissionDepositAccount;
    private String commissionBankName;
    private String commissionBankHolder;
//    @NotBlank(message = "등록번호는 필수 입력 항목입니다.")
    private String registrationNumber;
//    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    private String registrationAddress;
//    @NotBlank(message = "상세주소는 필수 입력 항목입니다.")
    private String registrationDetailAddress;
//    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    private String locationAddress;
//    @NotBlank(message = "상세주소는 필수 입력 항목입니다.")
    private String locationDetailAddress;
    private String partnerMemo;


    //회원가입 관리 관련 컬럼
    private String registrationStatus; //회원가입 여부
    private String rejectionReason; // 회원가입 거부 사유
    private String writer; //작성자
    private String event;//이벤트
    private Boolean visitStatus;//방문요청여부
    private LocalDateTime openingVisitRequestDate;//개통점방문희망일자
    private LocalDateTime openingVisitDecideDate;//개통점방문확정일자
    private String openingProgress;//개통점진행도
    private String openingStatus;//개통점전환여부
    private String openingNote;//개통점신청비고


    private List<WellFileDetailDTO> fileDetails = new ArrayList<>();

    private List<MultipartFile> businessLicenseFiles;
    private List<MultipartFile> contractDocumentFiles;
    private List<MultipartFile> idCardFiles;
    private List<MultipartFile> storePhotoFiles;
    private List<MultipartFile> businessCardFiles;

    //거래처유저
//    @NotBlank(message = "거래처 유저 부서는 필수 입력 항목입니다.")
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
    public WellPartnerCreateDTO(String partnerCode, String partnerName, String partnerType, Boolean specialPolicyOpening, Boolean specialPolicyCharge, Long partnerGroupId
                                , String discountCategory, String salesManager, boolean inApiFlag, String apiKeyInIdx, String preApprovalNumber
                                , LocalDateTime subscriptionDate, String transactionStatus, String partnerUpperIdx, String ceoName, String ceoTelephone
                                , String partnerTelephone, String emailAddress, String commissionDepositAccount, String commissionBankName, String commissionBankHolder
                                , String registrationNumber, String registrationAddress, String registrationDetailAddress, String locationAddress, String locationDetailAddress
                                , String partnerMemo, List<WellFileDetailDTO> fileDetails
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
        this.fileDetails = fileDetails;

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

