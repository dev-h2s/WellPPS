package com.wellnetworks.wellcore.java.dto.Operator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WellOperatorDetailDTO {
    private String operatorName; //통신사명

    private String operatorCode; // 통신사 코드

    private Boolean isOpeningSearchFlag; // 출력여부
}
