package com.wellnetworks.wellcore.java.dto.PIN;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellPinExcelCreateDTO {
    private String store; //입고처(거래처)
    private String operatorName; //통신사 (코드)
    private String productName; //요금제 (코드)
    private String pinNum; //PIN번호
    private String managementNum; //관리번호
    private Boolean isSaleFlag; //판매전용여부
}
