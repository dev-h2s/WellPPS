package com.wellnetworks.wellcore.java.domain.charge;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

// 충전 결과 내역
@Entity
@Getter
@Table(name = "charge_result_history_tb")
public class WellChargeResultHistoryEntity extends WellChargeHistoryEntity {

//    @Id
//    @Column(name = "charge_result_history_id", columnDefinition = "uniqueidentifier") // 충전 결과 내역 고유 값 idx
//    private Integer chargeResultHistoryId;

    @ManyToOne(fetch = FetchType.LAZY) // 충전시도내역 테이블과 연결되는 fk
    @JoinColumn(name = "charge_history_idx", insertable = false, updatable = false)
    private WellChargeHistoryEntity chargeHistory;

    @Column(name = "charge_start_dt") // 충전된 시작 시간
    private LocalDateTime chargeStartDt;

    @Column(name = "charge_end_dt") // 충전 종료 시간
    private LocalDateTime chargeEndDate;

    @Column(name = "charge_processing_dt")  // 충전되는데 걸린 소요시간
    private LocalDateTime chargeProcessingDate;

    @Column(name = "callback_request") // 콜백 요청값
    private String callbackRequest;

    @Column(name = "request_charge_count")  // 요청한 충전 횟수
    private Integer requestChargeCount;

    @Column(name = "completed_charge_count")  // 완료된 충전 횟수
    private Integer completedChargeCount;

    @Column(name = "charge_result") // 충전완료, 충전 실패 등 충전 결과
    private String chargeResult;

    @Column(name = "charge_price")  // 요금제 충전금액
    private Integer chargePrice;

    @Column(name = "progress") //진행도: 진행상황에 따라 다름(충전실패, 충전완료, 실패 등)
    private String progress;

    @Column(name = "moddt")  // 결과내역 변경일
    private LocalDateTime modifyDate;

    @Column(name = "modifier")  // 결과내역 변경자
    private String modifier;

    @Column(name = "memo")  // 충전결과내역 메모
    private String memo;

    @Column(name = "note")  // 충전결과내역 비고
    private String note;
}
