package com.wellnetworks.wellcore.java.dto.PIN;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellPinSearchDTO {
    @Schema(description = "판매전용여부", example = "true")
    private Boolean isSaleFlag;
    @Schema(description = "사용유무", example = "true")
    private Boolean isUseFlag;
    @Schema(description = "통신망", example = "통신망")
    private String network;
    @Schema(description = "통신사", example = "벨류컴")
    private String operatorName;
    @Schema(description = "요금제", example = "문자 전용 요금제")
    private String productName;
    @Schema(description = "PIN번호", example = "PinNum-1")
    private String pinNum;
    @Schema(description = "관리번호", example = "ManageNum-1")
    private String managementNum;
    @Schema(description = "입력자", example = "김진")
    private String writer;
    @Schema(description = "사용자", example = "김진")
    private String user;
}
