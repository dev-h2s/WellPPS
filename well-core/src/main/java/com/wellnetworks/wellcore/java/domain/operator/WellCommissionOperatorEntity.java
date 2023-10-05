package com.wellnetworks.wellcore.java.domain.operator;
//통신사
import com.wellnetworks.wellcore.java.domain.opening.WellCommissionOpeningPolicyEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "operator_tb", indexes = {@Index(name = "operator_idx", columnList = "operatorIdx",unique = true)})
public class WellCommissionOperatorEntity {

    @Id
    @Column(name = "o_idx", columnDefinition = "uniqueidentifier", unique = true, nullable = false) // 생성 고유 값
    private String operatorIdx;

    //개통정책 테이블 연결 1대 다
    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WellCommissionOpeningPolicyEntity> OpeningPolicy = new ArrayList<>();

    @Column(name = "o_name") // 통신사 명
    private String operatorName;

    @Column(name = "o_code") // 통신사 명에따른 코드
    private String operatorCode;

    @Column(name = "external_api_flag", unique = true) // 통신사에서 제공하는 외부 api 접근 허용 유무로 인한 연동 가능 여부(체크)
    private Boolean externalApiFlag;

    @Column(name = "run_flag", nullable = false) // 현재 정책 운영중인 통신사 여부(체크)
    private Boolean runFlag;

    @Column(name = "visible_flag", nullable = false) // 개통내역 입력 시 요금제 값 출력 여부(체크)
    private Boolean visibleFlag;

    @Column(name = "opening_search_flag") // 개통 내역 상세검색 필터 값 출력 여부(체크)
    private Boolean openingSearchFlag;


}
