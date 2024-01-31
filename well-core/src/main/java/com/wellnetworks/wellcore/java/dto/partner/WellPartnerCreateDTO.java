package com.wellnetworks.wellcore.java.dto.partner;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Validated
public class WellPartnerCreateDTO {
    @Schema(description = "파트너 로그인 id로쓰는 코드", example = "pa-121234")
    private String partnerCode;
    @Schema(description = "거래처 이름", example = "탄현 통신")
    private String partnerName;
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
    @Schema(description = "가입승인날짜", example = "2024-01-17")
    private LocalDate subscriptionDate;
    @Schema(description = "거래유무", example = "관리대상")
    private String transactionStatus;
    @Schema(description = "상부점idx", example = "상부점 idx")
    private String partnerUpperIdx;
    @Schema(description = "대표자명", example = "김형술")
    private String ceoName;
    @Schema(description = "사업장전화번호", example = "010-4849-9822")
    private String ceoTelephone;
    @Schema(description = "대표자전화번호", example = "010-9822-4849")
    private String partnerTelephone;
    @Schema(description = "이메일주소", example = "gugi92@naver.com")
    private String emailAddress;
    @Schema(description = "수수료 입금계좌", example = "524902-01-438111")
    private String commissionDepositAccount;
    @Schema(description = "수수료 입금 계좌은행 이름", example = "국민")
    private String commissionBankName;
    @Schema(description = "수수료 입금계좌 은행주", example = "김진")
    private String commissionBankHolder;
    @Schema(description = "사업자등록번호", example = "23141345")
    private String registrationNumber;
    @Schema(description = "사업자 등록주소", example = "서울시 강서구")
    private String registrationAddress;
    @Schema(description = "사업자 등록 상세주소", example = "허준로 224 1308호")
    private String registrationDetailAddress;
    @Schema(description = "사업자소재지주소", example = "서울시 강서구")
    private String locationAddress;
    @Schema(description = "사업자소재지상세주소", example = "허준로 224 1308호")
    private String locationDetailAddress;
    @Schema(description = "메모", example = "기타 내용 메모")
    private String partnerMemo;



    //회원가입 관리 관련 컬럼
    @Schema(description = "회원가입 여부", example = "승인")
    private String registrationStatus;
    @Schema(description = "회원가입 거부 사유", example = "대표자명 불일치")
    private String rejectionReason;
    @Schema(description = "작성자", example = "김형술")
    private String writer;
    @Schema(description = "이벤트", example = "미정")
    private String event;
    @Schema(description = "방문요청여부", example = "true")
    private Boolean visitStatus;//방문요청여부
    @Schema(description = "개통점방문희망일자", example = "2024-01-17T12:34:56")
    private LocalDateTime openingVisitRequestDate;
    @Schema(description = "개통점방문확정일자", example = "2024-01-17T12:34:56")
    private LocalDateTime openingVisitDecideDate;
    @Schema(description = "개통점진행도", example = "요청수신")
    private String openingProgress;
    @Schema(description = "개통점전환여부", example = "대기")
    private String openingStatus;
    @Schema(description = "개통점신청비고", example = "비고 메모")
    private String openingNote;

    //거래처유저
    @Schema(description = "거래처 부서명??", example = "대리점???")
    private String department;
    @Schema(description = "그룹별권한", example = "1??")
    private String partnerManagerGroupKey;
    @Schema(description = "로그인시아이디", example = "pa-12345")
    private String partnerIdentification;
    @Schema(description = "로그인시패스워드", example = "!@#SDFfq3")
    private String partnerUserPwd;
    @Schema(description = "권한", example = "???")
    private String permissions;
    @Schema(description = "임시 비밀번호", example = "!@#SDFfq3")
    private String tmpPwd;
    @Schema(description = "임시비밀번호 만료날짜", example = "2024-01-17T12:34:56")
    private LocalDateTime tmpPwdExpiration;
    @Schema(description = "임시비밀번호 사용횟수", example = "3")
    private Integer tmpPwdCount;
    @Schema(description = "임시비밀번호 생성일자", example = "2024-01-17T12:34:56")
    private LocalDateTime tmpPwdDate;
    @Schema(description = "전화번호 인증여부", example = "true")
    private Boolean isPhoneVerified;
    @Schema(description = "휴대폰 인증 코드", example = "345513")
    private String phoneVerificationCode;
    @Schema(description = "휴대폰 인증 시도 횟수", example = "1")
    private Integer phoneVerificationAttempts;
    @Schema(description = "휴대폰 인증 만료 시간", example = "2024-01-17T12:34:56")
    private LocalDateTime phoneVerificationExpiration;
    @Schema(description = "휴대폰 인증 코드 전송 시간", example = "2024-01-17T12:34:56")
    private LocalDateTime phoneVerificationSentTime;
    @Schema(description = "유저정보 생성일자", example = "2024-01-17T12:34:56")
    private LocalDateTime partnerUserRegisterDate;


    @QueryProjection
    public WellPartnerCreateDTO(String partnerCode, String partnerName, String partnerType, Boolean specialPolicyOpening, Boolean specialPolicyCharge, Long partnerGroupId
            , String discountCategory, String salesManager, boolean inApiFlag, String apiKeyInIdx, String preApprovalNumber
            , LocalDate subscriptionDate, String transactionStatus, String partnerUpperIdx, String ceoName, String ceoTelephone
            , String partnerTelephone, String emailAddress, String commissionDepositAccount, String commissionBankName, String commissionBankHolder
            , String registrationNumber, String registrationAddress, String registrationDetailAddress, String locationAddress, String locationDetailAddress
            , String partnerMemo            , String partnerManagerGroupKey, String partnerIdentification, String partnerUserPwd, String permissions
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

