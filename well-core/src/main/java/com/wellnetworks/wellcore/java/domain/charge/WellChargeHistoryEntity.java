package com.wellnetworks.wellcore.java.domain.charge;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

// 충전 시도 내역
@Entity
@Getter
@Table(name = "charge_history_tb", indexes = {@Index(name = "charge_history_idx", columnList = "chargeHistoryIdx",unique = true)})
public class WellChargeHistoryEntity {

    @Id
    @Column(name = "charge_history_idx", columnDefinition = "uniqueidentifier") //충전 시도 내역 고유 식별자 idx
    private String chargeHistoryIdx;

    //거래처 테이블 연결 다 대 1
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "p_idx", referencedColumnName = "partner_idx")
    private WellPartnerEntity partner;

    @Column(name = "product_search_idx")  // 요금제 조회 내역과 연결되는 fk
    private String productSearchIdx;

    @Column(name = "charge_try_price")  // 충전시도하는 금액
    private int chargeTryPrice;

    @Column(name = "charge_device") // 충전 시도하는 디바이스
    private String chargeDevice;

    @Column(name = "charge_browser")  // 충전 시도하는 브라우저
    private String chargeBrowser;

    @Column(name = "charge_system") // 충전 시도하는 운영체제
    private String chargeSystem;

    @Column(name = "seq")  // 충전 시도하는 고유값(주문번호로 사용)
    private String seq;

    @Column(name = "regdt")  // 충전 시도 내역 로그 생성시간
    private LocalDateTime registerDate;
}
