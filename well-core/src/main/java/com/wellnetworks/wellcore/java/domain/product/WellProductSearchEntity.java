package com.wellnetworks.wellcore.java.domain.product;
//요금제 조회 내역
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "product_search_tb", indexes = {@Index(name = "pr_search_idx", columnList = "memberManagerGroupKey",unique = true)})
public class WellProductSearchEntity {

    @Id
    @Column(name = "pr_search_idx", columnDefinition = "uniqueidentifier") // 생성 고유 값
    private String prSearchIdx;

    //요금제 테이블 연결 다 대 1 양방향
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pr_idx", referencedColumnName = "pr_idx")
    private WellProductEntity product;

    //거래처 테이블 연결 다 대 1 양방향
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "p_idx", referencedColumnName = "partner_idx")
    private WellPartnerEntity partner;

    @Column(name = "search_request_dt")  // 조회 요청을 한 시간
    private LocalDateTime productNamSearchRequestDate;

    @Column(name = "search_response_dt") // 조회 요청중 응답시간
    private LocalDateTime searchResponseDate;

    @Column(name = "search_processing_dt")  // 조회 요청하는데 걸린 소요시간
    private LocalDateTime searchProcessingDate;

    @Column(name = "charge_flag")  // 충전이 허용되어있는지에 대한 여부
    private Boolean chargeFlag;

    @Column(name = "search_flag") // 조회 요청을 했는지 안했는지
    private Boolean searchFlag;

    @Column(name = "response_flag")  // 조회 응답을 했는지 안했는지
    private Boolean responseFlag;

    @Column(name = "max_charge_count") // 조회를 요청한 ip주소
    private Integer maxChargeCount;

    @Column(name = "request_ip")  // 조회를 요청한 디바이스 기기
    private String requestIp;

    @Column(name = "request_device") // 조회를 요청한 웹 브라우저
    private String requestDevice;

    @Column(name = "request_browser")  // 조회를 요청한 운영체제
    private String requestBrowser;

    @Column(name = "request_system")  // 조회를 요청한 운영체제
    private String requestSystem;

}
