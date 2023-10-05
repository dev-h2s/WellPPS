package com.wellnetworks.wellcore.java.domain.opening;
//개통정책

import com.wellnetworks.wellcore.java.domain.operator.WellCommissionOperatorEntity;
import com.wellnetworks.wellcore.java.domain.product.WellCommissionProductEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@Table(name = "opening_policy_tb", indexes = {@Index(name = "version_id", columnList = "versionId", unique = true)})
public class WellCommissionOpeningPolicyEntity {

    @Id
    @Column(name = "version_id", columnDefinition = "uniqueidentifier") // 생성 고유 값 pk
    private String versionId;

    //요금제 테이블 연결 다 대 1
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pr_idx", referencedColumnName = "pr_idx", insertable = false, updatable = false)
    private WellCommissionProductEntity product;

    //통신사 테이블 연결 다 대 1
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pr_idx", referencedColumnName = "pr_idx", insertable = false, updatable = false)
    private WellCommissionOperatorEntity operator;

    @Column(name = "passport_type") // 등록증인지 여권인지 판단(등록증, 여권에 따라 수수료 달라짐)
    private String passportType;

    @Column(name = "regdt") // 버전 생성시간(화면에서 년과 월을 select)
    private String registerDate;

}
