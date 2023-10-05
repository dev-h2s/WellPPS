package com.wellnetworks.wellcore.java.domain.product;
//요금제
import com.wellnetworks.wellcore.java.domain.opening.WellCommissionOpeningPolicyEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "product_tb", indexes = {@Index(name = "p_idx", columnList = "productIdx",unique = true)})
public class WellCommissionProductEntity {

    @Id
    @Column(name = "pr_idx", columnDefinition = "uniqueidentifier") // 생성 고유 값
    private String productIdx;

    //요금제 조회 테이블 연결 1대 다
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<WellCommissionProductSearchEntity> productSearch = new ArrayList<>();

    //개통정책 테이블 연결 1대 다
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<WellCommissionOpeningPolicyEntity> OpeningPolicy = new ArrayList<>();

    @Column(name = "o_idx") // 통신사 정보와 연결되는 FK
    private String operatorIdx;

    @Column(name = "pr_name")  // 요금제의 이름
    private String productName;

    @Column(name = "pr_code_in") // 우리가 거래처에게 제공하는 코드(내부api)
    private String productCodeIn;

    @Column(name = "pr_code_ex")  // 우리가 통신사에서 제공받은 코드(외부api)
    private String productCodeEx;

    @Column(name = "pr_type")  // 후불,정액,일반 (탑업 제외)
    private String productType;

    @Column(name = "pr_price") // 요금제에 따른 금액
    private Integer productPrice;

    @Column(name = "pr_info_data")  // 사용가능한 데이터 정보
    private String productInfoData;

    @Column(name = "pr_info_voice") // 사용 가능한 통화 시간 표시
    private String productInfoVoice;

    @Column(name = "pr_info_sms")  // 사용 가능한 문자 개수 표시
    private String productInfoSms;

    @Column(name = "pr_info_etc")  // 기타 통화 가능시간(영상/전국대표번호) ??
    private String productInfoEtc;

    @Column(name = "telecom_type")  // sk,lg,kt 여부
    private String telecomType;

    @Column(name = "monthly")  // 월 단위 요금제 사용 기간
    private Integer monthly;

    @Column(name = "memo")  // 요금제에 대한 특이사항 메모
    private String memo;

    @Column(name = "regdt")  // 요금제 생성 일자
    private LocalDateTime register_date;

    @Column(name = "moddt")  // 요금제 정보에 대한 수정 일자
    private LocalDateTime modify_date;
}
