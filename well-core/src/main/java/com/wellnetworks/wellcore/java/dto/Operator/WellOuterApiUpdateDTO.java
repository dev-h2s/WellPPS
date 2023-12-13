package com.wellnetworks.wellcore.java.dto.Operator;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellOuterApiUpdateDTO {
    //통신사명
    private String operatorName;
    //통신사코드
    private String operatorCode;
    //직접연동
    private Boolean isExternalApiFlag;
    //수동충전
    private Boolean isVisibleFlag;
    //PDS연동
    private Boolean isPdsFlag;
    //개통가능여부
    private Boolean isRunFlag;

}
