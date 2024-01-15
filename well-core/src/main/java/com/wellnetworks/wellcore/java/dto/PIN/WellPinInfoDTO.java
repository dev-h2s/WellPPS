package com.wellnetworks.wellcore.java.dto.PIN;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellPinInfoDTO {
    private String store; //입고처(거래처)
    private String operatorName; //통신사 (코드)
    private String productName; //요금제 (코드)
    private String pinNum; //PIN번호
    private String managementNum; //관리번호
    private Boolean isSaleFlag; //판매전용여부

    public WellPinInfoDTO(String store, String operatorName, String productName, String pinNum, String managementNum, Boolean isSaleFlag) {
        this.store = store;
        this.operatorName = operatorName;
        this.productName = productName;
        this.pinNum = pinNum;
        this.managementNum = managementNum;
        this.isSaleFlag = isSaleFlag;
    }

}
