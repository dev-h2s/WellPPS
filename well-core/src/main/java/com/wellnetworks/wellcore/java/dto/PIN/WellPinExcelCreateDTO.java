package com.wellnetworks.wellcore.java.dto.PIN;

import lombok.*;

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

    @Builder
    public WellPinExcelCreateDTO(String store, String operatorName, String productName, String pinNum, String managementNum, Boolean isSaleFlag) {
        this.store = store;
        this.operatorName = operatorName;
        this.productName = productName;
        this.pinNum = pinNum;
        this.managementNum = managementNum;
        this.isSaleFlag = isSaleFlag;
    }
}
