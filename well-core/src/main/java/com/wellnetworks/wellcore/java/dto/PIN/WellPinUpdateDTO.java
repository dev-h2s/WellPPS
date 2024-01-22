package com.wellnetworks.wellcore.java.dto.PIN;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPinUpdateDTO {
    @Schema(description = "핀 고유번호", example = "1")
    private Long pinIdx;
    @Schema(description = "입고처", example = "한패스")
    private String store;
    @Schema(description = "출고처", example = "한패스")
    private String release;
    @Schema(description = "통신사", example = "벨류컴")
    private String operatorName;
    @Schema(description = "요금제", example = "문자전용 요금제")
    private String productName;
    @Schema(description = "PIN 번호", example = "PinNum-1")
    private String pinNum;
    @Schema(description = "관리번호", example = "ManageNum-1")
    private String managementNum;
    @Schema(description = "사용유무", example = "true")
    private Boolean isUseFlag;
    @Schema(description = "사용자", example = "김진")
    private String user;
    @Schema(description = "판매전용여부", example = "true")
    private Boolean isSaleFlag;
}
