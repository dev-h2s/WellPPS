package com.wellnetworks.wellcore.java.domain.charge;
// 충전정책
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellChargeCommissionEntity {

    @Id //수수료정보_id
    @Column(name = "comm_id")
    private Long CommissionId;

    @ManyToOne(fetch = LAZY) //거래처_idx
    @JoinColumn(name = "p_idx", unique = true, nullable = false)
    private WellPartnerEntity partner;  // 거래처 엔티티를 참조

    @Column(name = "op_discount") //개통점할인율
    private Float openingDiscount;

    @Column(name = "charge_discount") //충전점할인율
    private Float chargeDiscount;

    @Column(name = "api_discount") //api할인율
    private Float apiDiscount;

    @Column(name = "other_discount") //기타할인율
    private Float otherDiscount;

    @Column(name = "vat_info") //부가세정보
    private Float vatInfo;

    @Column(name = "income_tax_info") //소득세정보
    private Float incomeTaxInfo;

    @Column(name = "phone_issue_discount") //폰이슈개통고객할인율
    private Float phoneIssueDiscount;

    @Column(name = "pps") //pps카드
    private Integer pps;

    @Column(name = "cash") //현금
    private Integer cash;

}
