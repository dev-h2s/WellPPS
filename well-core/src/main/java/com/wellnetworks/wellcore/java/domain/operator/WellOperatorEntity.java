package com.wellnetworks.wellcore.java.domain.operator;
//통신사
import com.wellnetworks.wellcore.java.domain.outerApiVersion.WellOuterApiVersionStatus;
import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WellOperatorEntity {

    @Id
    @Column(name = "o_idx", columnDefinition = "uniqueidentifier") // 생성 고유 값
    private String operatorIdx;

    //요금제 테이블 연결 1대 다 양방향
    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WellProductEntity> products = new ArrayList<>();

    // 버전 설정과 일대다 관계
    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WellOuterApiVersionStatus> versionStatuses = new ArrayList<>();

    @Column(name = "o_name") // 통신사 명
    private String operatorName;

    @Column(name = "o_code") // 통신사 코드
    private String operatorCode;

    @Column(name = "version_id") // 버전id
    private Float versionId;

    @Column(name = "version_name")//버전 이름
    private String versionName;

    @Column(name = "o_search_flag") // 출력여부
    private Boolean isOpeningSearchFlag;

//    @Column(name = "external_api_flag") // 직접연동
//    private Boolean isExternalApiFlag;
//
//    @Column(name = "visible_flag") // 수동 충전
//    private Boolean isVisibleFlag;
//
//    @Column(name = "pds_flag") // pds 연동
//    private Boolean isPdsFlag;
//
//    @Column(name = "run_flag") //개통가능여부
//    private Boolean isRunFlag;

    public void setOperatorName(String operatorName) {this.operatorName = operatorName;}

    public void setIsOpeningSearchFlag(boolean isOpeningSearchFlag) {this.isOpeningSearchFlag = isOpeningSearchFlag;}

//    public void setExternalApiFlag(boolean externalApiFlag) {this.isExternalApiFlag = externalApiFlag;}
//
//    public void setVisibleFlag(boolean visibleFlag) {this.isVisibleFlag = visibleFlag;}
//
//    public void setPdsFlag(boolean pdsFlag) {this.isPdsFlag = pdsFlag;}
//
//    public void setRunFlag(boolean runFlag) {this.isRunFlag = runFlag;}

    @Builder
    public WellOperatorEntity(String operatorName, String operatorCode, Boolean isOpeningSearchFlag
//                              ,Boolean isExternalApiFlag, Boolean isVisibleFlag, Boolean isPdsFlag, Boolean isRunFlag
                              ,Float versionId, String versionName
                              ) {
        this.operatorIdx = UUID.randomUUID().toString(); // 빌더를 통해 객체를 생성할 때 UUID를 할당합니다.
        this.operatorName = operatorName;
        this.operatorCode = operatorCode;
        this.isOpeningSearchFlag = isOpeningSearchFlag;
//        this.isExternalApiFlag = isExternalApiFlag;
//        this.isVisibleFlag = isVisibleFlag;
//        this.isPdsFlag = isPdsFlag;
//        this.isRunFlag = isRunFlag;
        this.versionId = versionId;
        this.versionName = versionName;
    }
}
