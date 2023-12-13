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
    private Boolean isExternalApiFlag;
    //수동충전
    private Boolean isVisibleFlag;
    //PDS연동
    private Boolean isPdsFlag;
    //개통가능여부
    private Boolean isRunFlag;

    //외부api 리스트
    public WellOuterApiListDTO(WellOperatorEntity operatorEntity
    ) {
        this.operatorName = operatorEntity.getOperatorName();
        this.isExternalApiFlag = operatorEntity.getIsExternalApiFlag();
        this.isVisibleFlag = operatorEntity.getIsVisibleFlag();
        this.isPdsFlag = operatorEntity.getIsPdsFlag();
        this.isRunFlag = operatorEntity.getIsRunFlag();
    }
}
