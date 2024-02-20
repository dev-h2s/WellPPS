package com.wellnetworks.wellcore.java.domain.partner;
// 거래처

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.dto.partner.WellPartnerUpdateDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class WellPartnerEntity {

    @Id //거래처_idx
    @Column(name = "p_idx", columnDefinition = "uniqueidentifier")
    private String partnerIdx;

    @ManyToOne(fetch = LAZY) //거래처 그룹_id
    @JoinColumn(insertable = true, updatable = true)
    private WellPartnerGroupEntity partnerGroup;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "p_idx")
    private WellPartnerUserEntity partnerUser;

    // API 키와의 다대일 관계 (하나의 거래처는 API 키를 가짐)
    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "apikey_in_idx")
    private WellApikeyInEntity apiKey;

    // 가상계좌 연결 1대1
    @OneToOne(mappedBy = "partner", fetch = LAZY)
    private WellVirtualAccountEntity virtualAccount;

    // 개통 연결 1대다
//    @OneToMany(mappedBy = "partner")
//    private List<WellOpeningEntity> openings = new ArrayList<>();

    // 요금제 조회 테이블 연결 1대 다
//    @OneToMany(mappedBy = "partner")
//    private List<WellProductSearchEntity> productSearch = new ArrayList<>();

    // 충전 시도내역 테이블 연결 1대 다
//    @OneToMany(mappedBy = "partner")
//    private List<WellChargeHistoryEntity> chargeHistory = new ArrayList<>();

    @Column(name = "in_api_flag") //내부API연동여부
    private Boolean inApiFlag;

    @Column(name = "pcode", unique = true) //거래처코드
    private String partnerCode;

    @Column(name = "p_name") //거래처명
    private String partnerName;

    @Column(name = "trans_status") //거래유무
    private String transactionStatus;

    @Column(name = "p_type") //거래처구분
    private String partnerType;

    @Column(name = "p_upper_idx") //상부점_id
    private String partnerUpperIdx;

    @Column(name="p_tel") //사업장전화번호
    private String partnerTelephone;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @Column(name = "product_regdt") //시작날짜
    private LocalDateTime productRegisterDate;

    @Column(name = "product_moddt") //종료날짜
    private LocalDateTime productModifyDate;

    @Column(name="sales_manager") //영업담당자
    private String salesManager;

    @Column(name = "ceo_name") //대표자명
    private String ceoName;

    @Column(name="ceo_tel") //대표자전화번호
    private String ceoTelephone;

    @Column(name = "reg_addr") //사업자등록증주소
    private String registrationAddress;

    @Column(name = "reg_detail_addr") //사업자등록증상세주소
    private String registrationDetailAddress;

    @Column(name = "loc_addr") //사업자소재지주소
    private String locationAddress;

    @Column(name = "loc_detail_addr") //사업자소재지상세주소
    private String locationDetailAddress;

    @Column(name = "commision_type") //수수료타입
    private String commisionType;

    @Column(name = "size") //size
    private Integer size;

    @Column(name = "page") //page
    private Integer page;

    @Column(name = "discount_category") //충전할인율구분
    private String discountCategory;

    @Column(name = "subdt") //가입승인일자
    private LocalDate subscriptionDate;

    @Column(name = "special_policy_opening") //특수정책개통
    private Boolean specialPolicyOpening;

    @Column(name = "special_policy_charge") //특수정책충전
    private Boolean specialPolicyCharge;

    @Column(name = "pwd") //비밀번호
    private String password;

    @Column(name = "pre_approval_number") //사전승낙번호
    private String preApprovalNumber;

    @Column(name="email_addr") //이메일주소
    private String emailAddress;

    @Column(name = "registration_number") //사업자등록번호
    private String registrationNumber;

    @Column(name = "partner_memo") //메모
    private String partnerMemo;

    @Column(name = "sales_team_visdt") //영업팀최근방문일자
    private LocalDateTime salesTeamVisitDate;

    @Column(name = "sales_team_visit_memo") //영업팀방문일지
    private String salesTeamVisitMemo;

    @Column(name="commission_deposit_account") //수수료입금계좌
    private String commissionDepositAccount;

    @Column(name = "commission_bank_name") //수수료입금계좌은행명
    private String commissionBankName;

    @Column(name = "commission_bank_holder") //수수료입금계좌예금주
    private String commissionBankHolder;

    // 회원가입 관리 / 개통점 신청 관련 컬럼
    @Column(name = "registration_status") //회원가입 여부(승인관리)
    private String registrationStatus;
    @Column(name = "rejection_reason") //회원가입 거부 사유
    private String rejectionReason;

    @Column(name = "opening_rejection_reason") //회원가입 거부 사유
    private String openingRejectionReason;

    @Column(name = "writer") //작성자
    private String writer;

    @Column(name = "event") //이벤트
    private String event;

    @Column(name = "visit_status") //방문요청여부
    private Boolean visitStatus;

    @Column(name = "opening_visit_reqdt") //개통점방문희망일자
    private LocalDateTime openingVisitRequestDate;

    @Column(name = "opening_visit_decdt") //개통점방문확정일자
    private LocalDateTime openingVisitDecideDate;

    @Column(name = "opening_progress") //개통점진행도
    private String openingProgress;

    @Column(name = "opening_status") //개통점전환여부
    private String openingStatus;
    @Column(name = "opening_note") //개통점신청비고
    private String openingNote;

    @Column(name = "review_date") //회원가입 관리 검수일자
    private LocalDate reviewDate;
    @Column(name = "reviwer") //회원가입 관리 검수자
    private String reviewer;

    @Column(name="terms_ofUse")
    private Boolean termsOfUse; // 이용약관 동의

    @Column(name="sign_request_date")
    private LocalDateTime signRequestDate; // 회원가입 요청일자

    @Column(name="desired_date")
    private LocalDate desiredDate; //방문 희망 날짜

    @Column(name="delete_status")
    private Boolean deleteStatus; //삭제 상태

    @Builder
    public WellPartnerEntity(String partnerIdx, String partnerCode, String partnerName, String partnerType, Boolean specialPolicyOpening, Boolean specialPolicyCharge
                            , WellPartnerGroupEntity partnerGroup, String discountCategory, String salesManager
                            , boolean inApiFlag, WellApikeyInEntity apiKey, String preApprovalNumber, LocalDate subscriptionDate
                            , String transactionStatus,String partnerUpperIdx, String ceoName, String ceoTelephone
                            , String partnerTelephone, String commissionBankName, String commissionDepositAccount, String commissionBankHolder
                            , String emailAddress, String registrationNumber
                            , String registrationAddress, String registrationDetailAddress, String locationAddress, String locationDetailAddress
                            , String partnerMemo,
                             String writer, String event, Boolean visitStatus, LocalDateTime openingVisitRequestDate, LocalDateTime openingVisitDecideDate, String openingProgress, String openingStatus, String openingNote
                             , String registrationStatus,String rejectionReason, LocalDate reviewDate, String reviewer, Boolean termsOfUse, LocalDateTime signRequestDate, LocalDate desiredDate,
                             Boolean deleteStatus, LocalDateTime salesTeamVisitDate, String salesTeamVisitMemo, String openingRejectionReason ) {
        this.partnerIdx = partnerIdx;
        this.partnerCode = partnerCode;
        this.partnerName = partnerName;
        this.partnerType = partnerType;
        this.specialPolicyCharge = specialPolicyCharge;
        this.specialPolicyOpening = specialPolicyOpening;
        if (partnerGroup != null) {
            this.partnerGroup = partnerGroup;
        }
        this.discountCategory = discountCategory;
        this.salesManager = salesManager;
        this.inApiFlag = inApiFlag;
        if (apiKey != null) {
            this.apiKey = apiKey;
        }
        this.preApprovalNumber = preApprovalNumber;
        this.subscriptionDate = subscriptionDate;
        this.transactionStatus = transactionStatus;
        this.partnerUpperIdx = partnerUpperIdx;
        this.ceoName = ceoName;
        this.ceoTelephone = ceoTelephone;
        this.partnerTelephone = partnerTelephone;
        this.emailAddress = emailAddress;
        this.commissionBankName = commissionBankName;
        this.commissionDepositAccount = commissionDepositAccount;
        this.commissionBankHolder = commissionBankHolder;
        this.registrationNumber = registrationNumber;
        this.registrationAddress = registrationAddress;
        this.registrationDetailAddress = registrationDetailAddress;
        this.locationAddress = locationAddress;
        this.locationDetailAddress = locationDetailAddress;
        this.partnerMemo = partnerMemo;
        this.writer = writer;
        this.event = event;
        this.visitStatus = visitStatus;
        this.openingVisitRequestDate = openingVisitRequestDate;
        this.openingVisitDecideDate = openingVisitDecideDate;
        this.openingProgress = openingProgress;
        this.openingStatus = openingStatus;
        this.openingNote = openingNote;
        this.registrationStatus = registrationStatus;
        this.rejectionReason = rejectionReason;
        this.reviewDate = reviewDate;
        this.reviewer = reviewer;
        this.termsOfUse = termsOfUse;
        this.signRequestDate = signRequestDate;
        this.desiredDate = desiredDate;
        this.deleteStatus = deleteStatus;
        this.salesTeamVisitMemo = salesTeamVisitMemo;
        this.salesTeamVisitDate = salesTeamVisitDate;
        this.openingRejectionReason = openingRejectionReason;
    }

    public void updateFromDTO(WellPartnerUpdateDTO updateDTO) {
        this.partnerName = updateDTO.getPartnerName();
        this.partnerType = updateDTO.getPartnerType();
        this.specialPolicyCharge = updateDTO.getSpecialPolicyCharge();
        this.specialPolicyOpening = updateDTO.getSpecialPolicyOpening();
        this.discountCategory = updateDTO.getDiscountCategory();
        this.inApiFlag = updateDTO.isInApiFlag();
        this.preApprovalNumber = updateDTO.getPreApprovalNumber();
        this.subscriptionDate = LocalDate.from(updateDTO.getSubscriptionDate());
        this.transactionStatus = updateDTO.getTransactionStatus();
        this.partnerUpperIdx = updateDTO.getPartnerUpperIdx();
        this.salesManager = updateDTO.getSalesManager();
        this.ceoName = updateDTO.getCeoName();
        this.ceoTelephone = updateDTO.getCeoTelephone();
        this.partnerTelephone = updateDTO.getPartnerTelephone();
        this.emailAddress = updateDTO.getEmailAddress();
        this.commissionBankName = updateDTO.getCommissionBankName();
        this.commissionDepositAccount = updateDTO.getCommissionDepositAccount();
        this.commissionBankHolder = updateDTO.getCommissionBankHolder();
        this.registrationNumber = updateDTO.getRegistrationNumber();
        this.registrationAddress = updateDTO.getRegistrationAddress();
        this.registrationDetailAddress = updateDTO.getRegistrationDetailAddress();
        this.locationAddress = updateDTO.getLocationAddress();
        this.locationDetailAddress = updateDTO.getLocationDetailAddress();
        this.partnerMemo = updateDTO.getPartnerMemo();
        this.rejectionReason = updateDTO.getRejectionReason();
        this.registrationStatus = updateDTO.getRegistrationStatus();
        this.writer = updateDTO.getWriter();
        this.event = updateDTO.getEvent();
        this.visitStatus = updateDTO.getVisitStatus();
        this.openingVisitRequestDate = updateDTO.getOpeningVisitRequestDate();
        this.openingVisitDecideDate = updateDTO.getOpeningVisitDecideDate();
        this.openingProgress = updateDTO.getOpeningProgress();
        this.openingStatus = updateDTO.getOpeningStatus();
        this.openingNote = updateDTO.getOpeningNote();
        this.reviewDate = updateDTO.getReviewDate();
        this.reviewer = updateDTO.getReviewer();
        this.termsOfUse = updateDTO.getTermsOfUse();
        this.signRequestDate = updateDTO.getSignRequestDate();
        this.desiredDate = updateDTO.getDesiredDate();
        this.deleteStatus = updateDTO.getDeleteStatus();

    }

    public void setApiKey(WellApikeyInEntity apiKey) {this.apiKey = apiKey;}
    public void setPartnerGroup(WellPartnerGroupEntity partnerGroup) {
        this.partnerGroup = partnerGroup;
    }
    public void setInApiFlag(Boolean inApiFlag) {this.inApiFlag = inApiFlag;}
    public void setDeleteStatusOn() {this.deleteStatus = true;}
}