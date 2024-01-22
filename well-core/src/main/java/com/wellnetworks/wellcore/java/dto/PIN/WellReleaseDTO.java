package com.wellnetworks.wellcore.java.dto.PIN;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellReleaseDTO {
    @Schema(description = "통신사", example = "벨류컴")
    private String operatorName;
    @Schema(description = "요금제", example = "문자 전용 요금제")
    private String productName;
    @Schema(description = "PIN번호", example = "PinNum-1")
    private String pinNum;

    @Builder
    public WellReleaseDTO(String operatorName, String productName, String pinNum) {
        this.operatorName = operatorName;
        this.productName = productName;
        this.pinNum = pinNum;
    }
}
