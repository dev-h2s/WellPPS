package com.wellnetworks.wellcore.java.dto.partner;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPartnerUpdateDTO {
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
    private String apiKeyInIdx;

    private String preApprovalNumber;
    private LocalDateTime subscriptionDate;
//    @NotBlank(message = "거래유무는 필수 입력 항목입니다.")
    private String transactionStatus;
    private String partnerUpperIdx;
//    @NotBlank(message = "ceo이름은 필수 입력 항목입니다.")
    private String ceoName;
//    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호를 입력하세요.")
//    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    private String ceoTelephone;
//    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호를 입력하세요.")
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

//    회원가입 관련  컬럼
    private String registrationStatus; // 회원가입 여부(승인 관리)
    private String rejectionReason; // 회원가입 거부 사유
    private String writer; //작성자
    private String event;//이벤트
    private Boolean visitStatus;//방문요청여부
    private LocalDateTime openingVisitRequestDate;//개통점방문희망일자
    private LocalDateTime openingVisitDecideDate;//개통점방문확정일자
    private String openingProgress;//개통점진행도
    private String isOpeningFlag;//개통점전환여부
    private String openingNote;//개통점신청비고
    private LocalDate reviewDate;//회원가입 관리 검수일자
    private String reviewer;//회원가입 관리 검수자
    private Boolean termsOfUse;// 이용약관 동의
    private LocalDateTime signRequestDate; // 회원가입 요청일자
    private LocalDate desiredDate; //방문 희망 날짜
    private Boolean deleteStatus; //삭제 상태

    //개통점 신청 관련 컬럼
    private String openingStatus;


}
