package com.wellnetworks.wellcore.java.domain.opening;

import jakarta.persistence.*;
import lombok.Getter;

// 개통 수수료 여권
@Entity
@Getter
@Table(name = "commission_passport_tb", indexes = {@Index(name = "commission_certificate_idx", columnList = "commissionCertificateIdx",unique = true)})
public class WellCommissionPassportEntity extends WellCommissionOpeningPolicyEntity {

    @Id
    @Column(name = "commission_passport_idx", columnDefinition = "uniqueidentifier") // 개통수수료의 고유값 pk
    private Integer commissionCertificateIdx;

//    @Column(name = "version_id") // 개통정책과 연결되는 fk
//    private Integer versionId;

    @Column(name = "o_idx") // 개통정책과 연결되는 fk
    private String operatorIdx;

    @Column(name = "p_idx")  // 개통정책과 연결되는 fk
    private String productIdx;

//    @Column(name = "passport_type") // 개통정책과 연결되는 fk
//    private Boolean passportType;

    @Column(name = "initial_charge")  // 개통시 당사가 거래처에 주는 충전 초기금액
    private Integer initialCharge;

    @Column(name = "policy_quantity")  // mvno(통신사)가 우리에게 주는 요금제 무료 충전 횟수(금액)
    private Integer policyQuantity;

    @Column(name = "fee_pps_card") // 개통시 거래처에 제공하는 pps카드 개수
    private Integer feePpsCard;

    @Column(name = "fee_deposit")  // 개통시 거래처에 제공하는 예치금
    private Integer feeDeposit;

    @Column(name = "fee_cash") // 개통시 거래처에 제공하는 현금(사용빈도 낮음)
    private Integer feeCash;

    @Column(name = "deposit_deduction") // 개통시 거래처에 제공받는 ?? 예치금
    private Integer depositDeduction;
}
