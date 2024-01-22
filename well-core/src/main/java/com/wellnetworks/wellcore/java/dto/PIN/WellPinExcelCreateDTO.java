package com.wellnetworks.wellcore.java.dto.PIN;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellPinExcelCreateDTO {
    @Schema(description = "입고처", example = "한패스")
    private String store;
    @Schema(description = "통신사", example = "벨류컴")
    private String operatorName;
    @Schema(description = "요금제", example = "문자전용 요금제")
    private String productName;
    @Schema(description = "PIN번호", example = "PinNum-1")
    private String pinNum;
    @Schema(description = "관리번호", example = "ManageNum-1")
    private String managementNum;
    @Schema(description = "판매전용여부", example = "true")
    private Boolean isSaleFlag;

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
