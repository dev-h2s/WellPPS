package com.wellnetworks.wellcore.java.dto.PIN;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellReleaseListDTO {
    @Schema(description = "통신사", example = "벨류컴")
    private String operatorName;
    @Schema(description = "요금제", example = "문자 전용 요금제")
    private String productName;
    @Schema(description = "요금제 별 pin 개수(미사용 및 판매 전용)", example = "3")
    private Long productCount;

    @Builder
    public WellReleaseListDTO(String operatorName, String productName) {
        this.operatorName = operatorName;
        this.productName = productName;
    }
}
