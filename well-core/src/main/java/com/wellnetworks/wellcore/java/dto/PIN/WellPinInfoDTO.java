package com.wellnetworks.wellcore.java.dto.PIN;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPinInfoDTO {
    @Schema(description = "입고처", example = "김진")
    private String store;
    @Schema(description = "통신사", example = "김진")
    private String operatorName;
    @Schema(description = "요금제", example = "문자 전용 요금제")
    private String productName;
    @Schema(description = "PIN번호", example = "PinNum-1")
    private String pinNum;
    @Schema(description = "관리번호", example = "ManageNum-1")
    private String managementNum;
    @Schema(description = "판매전용여부", example = "true")
    private Boolean isSaleFlag;

    public WellPinInfoDTO(String store, String operatorName, String productName, String pinNum, String managementNum, Boolean isSaleFlag) {
        this.store = store;
        this.operatorName = operatorName;
        this.productName = productName;
        this.pinNum = pinNum;
        this.managementNum = managementNum;
        this.isSaleFlag = isSaleFlag;
    }

}
