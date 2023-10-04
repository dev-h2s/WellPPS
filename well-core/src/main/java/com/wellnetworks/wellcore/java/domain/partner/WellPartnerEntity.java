package com.wellnetworks.wellcore.java.domain.partner;
// 거래처

import com.wellnetworks.wellcore.domain.converter.CompanyStateTypeToIndexConverter;
import com.wellnetworks.wellcore.domain.converter.CompanyTypeToIndexConverter;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Table(name = "partner_tb", indexes =
@Index(name = "IX_pcode", columnList = "pcode", unique = true))
public class WellPartnerEntity {
    @Id @GeneratedValue //거래처_idx
    @Column(name = "p_idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    private String idx;

    @Column(name = "p_group_id") //거래처 그룹_id
    private Long partnerGroupId;

    @Column(name = "p_id") //거래처_id
    private Long id;

    @Column(name = "pcode", unique = true) //거래처코드
    private String pCode;

    @Column(name = "tbl_id", nullable = false) //테이블코드
    private String tableID;

    @Column(name = "p_name", nullable = false) //거래처명
    private String partnerName;

    @Column(name = "trans_status") //거래유무
    private String transactionStatus;

    @Column(name = "p_type", nullable = false) //거래처구분
    private String pType;

    @Column(name = "p_upper_id") //상부점_id
    private Long partnerUpperId;

    @Column(name="p_tel") //사업장전화번호
    private String partnerTelecom;

    @Column(name = "product_regdt") //시작날짜
    private ZonedDateTime productRegisterDate;

    @Column(name = "product_moddt") //종료날짜
    private ZonedDateTime productModifyDate;

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

    @Column(name = "region") //지역
    private String region;

    @Column(name = "subdt") //가입승인일자
    private ZonedDateTime subscriptionDate;

    @Column(name = "special_policy_opening") //특수정책개통
    private boolean specialPolicyOpening;

    @Column(name = "special_policy_charge") //특수정책충전
    private boolean specialPolicyCharge;

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
    private ZonedDateTime salesTeamVisitDate;

    @Column(name = "sales_team_visit_memo") //영업팀방문일지
    private String salesTeamVisitMemo;

    @Column(name="commission_deposit_account") //수수료입금계좌
    private String commissionDepositAccount;

    @Column(name = "commission_bank_name") //수수료입금계좌은행명
    private String commissionBankName;

    @Column(name = "commission_bank_holder") //수수료입금계좌예금주
    private String commissionBankHolder;

    @Column(name = "writer") //작성자
    private String writer;

    @Column(name = "event") //이벤트
    private String event;

    @Column(name = "opening_visit_reqdt") //개통점방문요청일자
    private ZonedDateTime openingVisitRequestDate;

    @Column(name = "opening_visit_decdt") //개통점방문확정일자
    private ZonedDateTime openingVisitDecideDate;

    @Column(name = "opening_progress") //개통점진행도
    private String openingProgress;

    @Column(name = "opening_flag") //개통점전환여부
    private boolean openingFlag;

    @Column(name = "opening_note") //개통점신청비고
    private String openingNote;
}