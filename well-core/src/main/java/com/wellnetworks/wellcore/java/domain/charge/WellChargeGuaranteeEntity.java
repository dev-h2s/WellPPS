package com.wellnetworks.wellcore.java.domain.charge;

import jakarta.persistence.*;
import lombok.Getter;

// 충전 중 담보 예치금
@Entity
@Getter
@Table(name = "charging_guarantee_tb", indexes = {@Index(name = "charging_guarantee_id", columnList = "chargingGuaranteeId",unique = true)})
public class WellChargeGuaranteeEntity extends WellChargeHistoryEntity {

    @Id
    @Column(name = "charging_guarantee_id", columnDefinition = "uniqueidentifier") //충전 중 담보 예치금 고유 식별자 idx
    private Integer chargingGuaranteeId;

//    @Column(name = "charge_history_id") //충전 시도 내역 fk
//    private Integer chargeHistoryId;

    @Column(name = "guarantee_diposit") // 담보예치금
    private Integer guaranteeDiposit;

    @Column(name = "judgment_value") //판단 값
    private Integer judgmentValue;
}
