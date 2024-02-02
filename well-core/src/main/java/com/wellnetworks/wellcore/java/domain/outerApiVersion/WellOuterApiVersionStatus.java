package com.wellnetworks.wellcore.java.domain.outerApiVersion;

import com.wellnetworks.wellcore.java.domain.BaseEntity;
import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

//외부api버전설정
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
public class WellOuterApiVersionStatus extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_version_status_id")
    private Long id;

    // 외부api버전과 다대일 관계
    @ManyToOne(fetch = LAZY)
    private WellOuterApiVersion outerApiVersion;

    // 통신사와 다대일 관계
    @ManyToOne(fetch = LAZY)
    private WellOperatorEntity operator;

    @Column(name = "external_api_flag") // 직접연동
    private Boolean isExternalApiFlag;

    @Column(name = "visible_flag") // 수동 충전
    private Boolean isVisibleFlag;

    @Column(name = "pds_flag") // pds 연동
    private Boolean isPdsFlag;

    @Column(name = "run_flag") //개통가능여부
    private Boolean isRunFlag;
}
