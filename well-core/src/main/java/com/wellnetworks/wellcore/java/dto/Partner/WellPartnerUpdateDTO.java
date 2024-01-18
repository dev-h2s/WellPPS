package com.wellnetworks.wellcore.java.dto.Partner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPartnerUpdateDTO {
    private String partnerCode;
    @NotBlank(message = "거래처명은 필수 입력 항목입니다.")
    private String partnerName;
    @NotBlank(message = "거래처 출력은 필수 입력 항목입니다.")
    private String partnerType;
    private Boolean specialPolicyOpening;
    private Boolean specialPolicyCharge;

    private Long partnerGroupId; // 거래처 그룹 정보 추가

    @NotBlank(message = "충전할인율은 필수 입력 항목입니다.")
    private String discountCategory;
    @NotBlank(message = "영업담당자는 필수 입력 항목입니다.")
    private String salesManager;

    @NotNull(message = "api연동여부는 필수 입력 항목입니다.")
    private boolean inApiFlag;
    private String apiKeyInIdx;

    private String preApprovalNumber;
    private LocalDateTime subscriptionDate;
    @NotBlank(message = "거래유무는 필수 입력 항목입니다.")
    private String transactionStatus;
    private String partnerUpperIdx;
    @NotBlank(message = "ceo이름은 필수 입력 항목입니다.")
    private String ceoName;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호를 입력하세요.")
    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    private String ceoTelephone;
    @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "올바른 휴대폰 번호를 입력하세요.")
    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    private String partnerTelephone;
    @Email(message = "올바른 이메일 주소를 입력하세요.")
    @NotBlank(message = "이메일 주소는 필수 입력 항목입니다.")
    private String emailAddress;
    private String commissionDepositAccount;
    private String commissionBankName;
    private String commissionBankHolder;
    @NotBlank(message = "등록번호는 필수 입력 항목입니다.")
    private String registrationNumber;
    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    private String registrationAddress;
    @NotBlank(message = "상세주소는 필수 입력 항목입니다.")
    private String registrationDetailAddress;
    @NotBlank(message = "주소는 필수 입력 항목입니다.")
    private String locationAddress;
    @NotBlank(message = "상세주소는 필수 입력 항목입니다.")
    private String locationDetailAddress;
    private String partnerMemo;

    private List<MultipartFile> businessLicenseFiles;
    private List<MultipartFile> contractDocumentFiles;
    private List<MultipartFile> idCardFiles;
    private List<MultipartFile> storePhotoFiles;
    private List<MultipartFile> businessCardFiles;

//    회원가입 관련 관련 컬럼
    private String registrationStatus;
    private String rejectionReason;
    private String writer; //작성자
    private String event;//이벤트
    private Boolean visitStatus;//방문요청여부
    private LocalDateTime openingVisitRequestDate;//개통점방문희망일자
    private LocalDateTime openingVisitDecideDate;//개통점방문확정일자
    private String openingProgress;//개통점진행도
    private Boolean isOpeningFlag;//개통점전환여부
    private String openingNote;//개통점신청비고

}
