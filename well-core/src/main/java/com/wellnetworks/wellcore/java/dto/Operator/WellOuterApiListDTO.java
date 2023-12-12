package com.wellnetworks.wellcore.java.dto.Operator;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellOuterApiListDTO {
    //통신사명
    private String operatorName;
    //직접연동
    private boolean isExternalApiFlag;
    //수동충전
    private boolean isVisibleFlag;
    //PDS연동
    private boolean isPdsFlag;
    //개통가능여부
    private boolean isRunFlag;

    //외부api 리스트
    public WellOuterApiListDTO(WellOperatorEntity operatorEntity
    ) {
        this.operatorName = operatorEntity.getOperatorName();
        this.isExternalApiFlag = operatorEntity.isExternalApiFlag();
        this.isVisibleFlag = operatorEntity.isVisibleFlag();
        this.isPdsFlag = operatorEntity.isPdsFlag();
        this.isRunFlag = operatorEntity.isRunFlag();
    }
}
