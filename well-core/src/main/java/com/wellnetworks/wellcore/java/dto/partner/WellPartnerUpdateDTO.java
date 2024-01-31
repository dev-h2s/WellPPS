package com.wellnetworks.wellcore.java.dto.partner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPartnerUpdateDTO {
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
    private Long partnerGroupId;
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

//    회원가입 관련  컬럼
    @Schema(description = "회원가입 여부", example = "승인")
    private String registrationStatus;
    @Schema(description = "회원가입 거부 사유", example = "대표자명 불일치")
    private String rejectionReason;
    @Schema(description = "작성자", example = "김형술")
    private String writer;
    @Schema(description = "이벤트", example = "미정")
    private String event;
    @Schema(description = "방문요청여부", example = "true")
    private Boolean visitStatus;
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
    @Schema(description = "검수일자", example = "2024-01-17")
    private LocalDate reviewDate;
    @Schema(description = "검수자", example = "김진")
    private String reviewer;
    @Schema(description = "이용약관 동의", example = "true")
    private Boolean termsOfUse;
    @Schema(description = "회원가입 요청일자", example = "2024-01-17T12:34:56")
    private LocalDateTime signRequestDate;
    @Schema(description = "방문희망 날짜", example = "2024-01-17")
    private LocalDate desiredDate;
    @Schema(description = "삭제 상태", example = "false")
    private Boolean deleteStatus;



}
