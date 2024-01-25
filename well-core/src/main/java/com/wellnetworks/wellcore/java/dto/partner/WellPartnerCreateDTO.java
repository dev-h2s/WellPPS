package com.wellnetworks.wellcore.java.dto.partner;

import com.querydsl.core.annotations.QueryProjection;
import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "파트너 로그인 id로쓰는 코드", example = "pa-121234")
    private String partnerCode;
    @Schema(description = "거래처 이름", example = "탄현 통신")
    private String partnerName;
    //    @NotBlank(message = "거래처 출력은 필수 입력 항목입니다.")
    @Schema(description = "거래처 타입", example = "대리점")
    private String partnerType;
    @Schema(description = "특수정책 계통", example = "true")
    private Boolean specialPolicyOpening;
    @Schema(description = "특수정책 충전", example = "flase")
    private Boolean specialPolicyCharge;
    @Schema(description = "거래처 그룹 아이디", example = "1")
    private Long partnerGroupId; // 거래처 그룹 정보 추가
    @Schema(description = "충전할인율", example = "개통점")
    private String discountCategory;
    @Schema(description = "영업담당자", example = "김진")
    private String salesManager;
    @Schema(description = "api연동여부", example = "true")
    private boolean inApiFlag;
    @Schema(description = "api연동여부", example = "true")
    private String apiKeyInIdx;
    @Schema(description = "사전승낙번호", example = "????")
    private String preApprovalNumber;
    @Schema(description = "가입승인날짜", example = "2024-01-17T12:34:56")
    private LocalDateTime subscriptionDate;
    @Schema(description = "거래유무", example = "관리대상")
    private String transactionStatus;
    @Schema(description = "상부점idx", example = "상부점 idx")
    private String partnerUpperIdx;
    //    @NotBlank(message = "ceo이름은 필수 입력 항목입니다.")
    @Schema(description = "대표자명", example = "김형술")
    private String ceoName;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String ceoTelephone;
    //    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호를 입력하세요.")
//    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String partnerTelephone;
    //    @Email(message = "올바른 이메일 주소를 입력하세요.")
//    @NotBlank(message = "이메일 주소는 필수 입력 항목입니다.")
    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String emailAddress;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String commissionDepositAccount;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String commissionBankName;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String commissionBankHolder;
    //    @NotBlank(message = "등록번호는 필수 입력 항목입니다.")
    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String registrationNumber;
    //    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String registrationAddress;
    //    @NotBlank(message = "상세주소는 필수 입력 항목입니다.")
    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String registrationDetailAddress;
    //    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String locationAddress;
    //    @NotBlank(message = "상세주소는 필수 입력 항목입니다.")
    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String locationDetailAddress;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String partnerMemo;


    //회원가입 관리 관련 컬럼
    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String registrationStatus; //회원가입 여부

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String rejectionReason; // 회원가입 거부 사유

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String writer; //작성자

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String event;//이벤트

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private Boolean visitStatus;//방문요청여부

    @Schema(description = "대표자전화번호", example = "2024-01-17T12:34:56")
    private LocalDateTime openingVisitRequestDate;//개통점방문희망일자

    @Schema(description = "대표자전화번호", example = "2024-01-17T12:34:56")
    private LocalDateTime openingVisitDecideDate;//개통점방문확정일자

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String openingProgress;//개통점진행도

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String openingStatus;//개통점전환여부

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String openingNote;//개통점신청비고


    private List<WellFileDetailDTO> fileDetails = new ArrayList<>();

    private List<MultipartFile> businessLicenseFiles;
    private List<MultipartFile> contractDocumentFiles;
    private List<MultipartFile> idCardFiles;
    private List<MultipartFile> storePhotoFiles;
    private List<MultipartFile> businessCardFiles;

    //거래처유저
//    @NotBlank(message = "거래처 유저 부서는 필수 입력 항목입니다.")
    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String department;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String partnerManagerGroupKey;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String partnerIdentification;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String partnerUserPwd;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String permissions;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String tmpPwd;

    @Schema(description = "대표자전화번호", example = "2024-01-17T12:34:56")
    private LocalDateTime tmpPwdExpiration;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private Integer tmpPwdCount;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private LocalDateTime tmpPwdDate;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private Boolean isPhoneVerified;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String phoneVerificationCode;

    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private Integer phoneVerificationAttempts;

    @Schema(description = "대표자전화번호", example = "2024-01-17T12:34:56")
    private LocalDateTime phoneVerificationExpiration;

    @Schema(description = "대표자전화번호", example = "2024-01-17T12:34:56")
    private LocalDateTime phoneVerificationSentTime;

    @Schema(description = "대표자전화번호", example = "2024-01-17T12:34:56")
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

