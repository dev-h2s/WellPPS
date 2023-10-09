package com.wellnetworks.wellcore.java.domain.charge;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

// 충전시도요청
@Entity
@Getter
@Table(name = "charge_request_tb", indexes = {@Index(name = "charge_request_id", columnList = "memberManagerGroupKey",unique = true)})
public class WellChargeRequestEntity extends WellChargeHistoryEntity {

    @Id
    @Column(name = "charge_request_id", columnDefinition = "uniqueidentifier") // 생성 고유값 pk
    private Integer chargeRequestId;

    @ManyToOne(fetch = FetchType.LAZY) // 충전 시도 내역 테이블과 연결되는 fk
    @JoinColumn(name = "charge_history_idx") // charge_history_idx로 연결
    private WellChargeHistoryEntity chargeHistory;

    @Column(name = "request_api_key") // 충전 시도시 요청된는 apikey 값
    private String  requestApiKey;

    @Column(name = "charge_request_tel") // 충전 시도하는 전화번호
    private String chargeRequestTel;

    @Column(name = "charge_request_price") // 충전 시도시 요청하는 금액
    private Integer chargeRequestPrice;

    @Column(name = "moddt") // 충전 시도요청 내역 로그 생성시간
    private LocalDateTime modifyDate;

}
