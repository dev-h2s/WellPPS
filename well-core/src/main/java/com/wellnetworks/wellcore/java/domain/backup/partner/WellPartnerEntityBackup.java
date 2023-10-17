package com.wellnetworks.wellcore.java.domain.backup.partner;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.opening.WellOpeningEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
@Entity
@Data
@NoArgsConstructor
public class WellPartnerEntityBackup {
    @Id //거래처_idx
    @Column(name = "p_idx")
    private String partnerIdx;

    @ManyToOne(fetch = LAZY) //거래처 그룹_id
    @JoinColumn(name = "p_group_id", insertable = false, updatable = false)
    private WellPartnerGroupEntityBackup partnerGroup;

    @OneToOne(fetch = LAZY) //거래처_id (id를 사용하여 거래처 유저 엔티티와 1대1 연결)
    @JoinColumn(name = "p_id")
    private WellPartnerUserEntityBackup partnerId;

    // API 키와의 다대일 관계 (하나의 거래처는 하나의 API 키를 가짐)
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "api_key_in_id", insertable = false, updatable = false)
    private WellApikeyInEntityBackup apiKey;

    // 가상계좌 연결 1대1
    @OneToOne(mappedBy = "partner", fetch = LAZY, cascade = CascadeType.ALL)
    private WellVirtualAccountEntityBackup virtualAccount;

    // 개통 연결 1대다
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<WellOpeningEntityBackup> openings = new ArrayList<>();

    //여러 거래처 파일을 가질 수 있음
    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<WellPartnerFileStorageEntityBackup> files = new ArrayList<>();

    @Column(name = "pcode", unique = true) //거래처코드
    private String partnerCode;

    @Column(name = "tbl_id", nullable = false) //테이블코드
    private String tableID;

    @Column(name = "p_name", nullable = false) //거래처명
    private String partnerName;

    @Column(name = "trans_status") //거래유무
    private String transactionStatus;

    @Column(name = "p_type", nullable = false) //거래처구분
    private String partnerType;

    @Column(name = "p_upper_id") //상부점_id
    private Long partnerUpperId;

    @Column(name = "p_tel") //사업장전화번호
    private String partnerTelephone;

    @Column(name = "product_regdt") //시작날짜
    private LocalDateTime productRegisterDate;

    @Column(name = "product_moddt") //종료날짜
    private LocalDateTime productModifyDate;

    @Column(name = "sales_manager") //영업담당자
    private String salesManager;

    @Column(name = "ceo_name") //대표자명
    private String ceoName;

    @Column(name = "ceo_tel") //대표자전화번호
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
    private LocalDateTime subscriptionDate;

    @Column(name = "special_policy_opening") //특수정책개통
    private boolean specialPolicyOpening;

    @Column(name = "special_policy_charge") //특수정책충전
    private boolean specialPolicyCharge;

    @Column(name = "pwd") //비밀번호
    private String password;

    @Column(name = "pre_approval_number") //사전승낙번호
    private String preApprovalNumber;

    @Column(name = "email_addr") //이메일주소
    private String emailAddress;

    @Column(name = "registration_number") //사업자등록번호
    private String registrationNumber;

    @Column(name = "partner_memo") //메모
    private String partnerMemo;

    @Column(name = "sales_team_visdt") //영업팀최근방문일자
    private LocalDateTime salesTeamVisitDate;

    @Column(name = "sales_team_visit_memo") //영업팀방문일지
    private String salesTeamVisitMemo;

    @Column(name = "commission_deposit_account") //수수료입금계좌
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
    private LocalDateTime openingVisitRequestDate;

    @Column(name = "opening_visit_decdt") //개통점방문확정일자
    private LocalDateTime openingVisitDecideDate;

    @Column(name = "opening_progress") //개통점진행도
    private String openingProgress;

    @Column(name = "opening_flag") //개통점전환여부
    private boolean openingFlag;

    @Column(name = "opening_note") //개통점신청비고
    private String openingNote;
}