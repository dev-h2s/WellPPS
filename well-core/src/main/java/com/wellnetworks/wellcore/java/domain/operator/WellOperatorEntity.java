package com.wellnetworks.wellcore.java.domain.operator;
//통신사
import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
public class WellOperatorEntity {

    @Id
    @Column(name = "o_idx", columnDefinition = "uniqueidentifier") // 생성 고유 값
    private String operatorIdx;

    //요금제 테이블 연결 1대 다 양방향
    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WellProductEntity> products = new ArrayList<>();

    @Column(name = "o_name") // 통신사 명
    private String operatorName;

    @Column(name = "o_code") // 통신사 코드
    private String operatorCode;

    @Column(name = "o_search_flag") // 출력여부
    private Boolean isOpeningSearchFlag;

    @Column(name = "external_api_flag") // 직접연동
    private Boolean isExternalApiFlag;

    @Column(name = "visible_flag") // 수동 충전
    private Boolean isVisibleFlag;

    @Column(name = "pds_flag") // pds 연동
    private Boolean isPdsFlag;

    @Column(name = "run_flag") //개통가능여부
    private Boolean isRunFlag;

    public void setExternalApiFlag(boolean externalApiFlag) {this.isExternalApiFlag = externalApiFlag;}

    public void setVisibleFlag(boolean visibleFlag) {this.isVisibleFlag = visibleFlag;}

    public void setPdsFlag(boolean pdsFlag) {this.isPdsFlag = pdsFlag;}

    public void setRunFlag(boolean runFlag) {this.isRunFlag = runFlag;}
}
