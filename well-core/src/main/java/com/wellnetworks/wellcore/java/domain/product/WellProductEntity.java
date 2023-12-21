package com.wellnetworks.wellcore.java.domain.product;
//요금제
import com.wellnetworks.wellcore.java.domain.opening.WellCommissionOpeningPolicyEntity;
import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.dto.Product.WellProductUpdateDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class WellProductEntity {

    @Id
    @Column(name = "pr_idx", columnDefinition = "uniqueidentifier") // 생성 고유 값
    private String productIdx = UUID.randomUUID().toString();

    //요금제 조회 테이블 연결 1대 다 양방향
    @OneToMany(mappedBy = "product")
    private List<WellProductSearchEntity> productSearch = new ArrayList<>();

    //개통정책 테이블 연결 1대 다
    @OneToMany(mappedBy = "product")
    private List<WellCommissionOpeningPolicyEntity> OpeningPolicy = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "o_idx") // 통신사 정보와 연결되는 FK 양방향
    private WellOperatorEntity operator;

    @Column(name = "visible_flag") // 출력여부
    private Boolean visibleFlag;

    @Column(name = "o_history_search_flag") //개통내역검색여부
    private Boolean openingHistorySearchFlag;

    @Column(name = "network") //통신망
    private String network;

    @Column(name = "base_fee") //기본요금
    private Integer baseFee;

    @Column(name = "pr_type") //요금제구분
    private String productType;

    @Column(name = "pr_name") //요금제명
    private String productName;

    @Column(name = "mvno_pr_name") //MVNO 요금제명
    private String mvnoProductName;

    @Column(name = "data") //데이터
    private String data;

    @Column(name = "voice") //음성
    private String voice;

    @Column(name = "etc") //기타
    private String etc;

    @Column(name = "sms") //문자
    private String sms;

    @Column(name = "internal_code") //요금제코드(내부코드)
    private String internalCode;

    @Column(name = "external_code") //요금제코드(외부코드)
    private String externalCode;

    @Column(name = "memo") // 메모
    private String memo;

    @Builder
    public WellProductEntity(String productIdx, WellOperatorEntity operator, Boolean visibleFlag,
                             Boolean openingHistorySearchFlag, String network, Integer baseFee,
                             String productType, String productName, String mvnoProductName,
                             String data, String voice, String etc, String sms,
                             String internalCode, String externalCode, String memo) {
        this.productIdx = productIdx != null ? productIdx : UUID.randomUUID().toString();
        this.operator = operator;
        this.visibleFlag = visibleFlag;
        this.openingHistorySearchFlag = openingHistorySearchFlag;
        this.network = network;
        this.baseFee = baseFee;
        this.productType = productType;
        this.productName = productName;
        this.mvnoProductName = mvnoProductName;
        this.data = data;
        this.voice = voice;
        this.etc = etc;
        this.sms = sms;
        this.internalCode = internalCode;
        this.externalCode = externalCode;
        this.memo = memo;
    }

    public void updateFromDTO(WellProductUpdateDTO dto) {
        if (dto.getVisibleFlag() != null) {
            this.visibleFlag = dto.getVisibleFlag();
        }
        if (dto.getOpeningHistorySearchFlag() != null) {
            this.openingHistorySearchFlag = dto.getOpeningHistorySearchFlag();
        }
        if (dto.getProductName() != null) {
            this.productName = dto.getProductName();
        }
        if (dto.getMvnoProductName() != null) {
            this.mvnoProductName = dto.getMvnoProductName();
        }
        if (dto.getData() != null) {
            this.data = dto.getData();
        }
        if (dto.getVoice() != null) {
            this.voice = dto.getVoice();
        }
        if (dto.getSms() != null) {
            this.sms = dto.getSms();
        }
        if (dto.getEtc() != null) {
            this.etc = dto.getEtc();
        }
        if (dto.getExternalCode() != null) {
            this.externalCode = dto.getExternalCode();
        }
        if (dto.getMemo() != null) {
            this.memo = dto.getMemo();
        }
    }
}
