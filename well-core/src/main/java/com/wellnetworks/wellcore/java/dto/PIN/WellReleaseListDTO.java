package com.wellnetworks.wellcore.java.dto.PIN;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellReleaseListDTO {
    private String operatorName; //통신사 (코드)
    private String productName; //요금제 (코드)
    private Long productCount; //요금제 별 pin 개수(미사용 및 판매 전용)

    @Builder
    public WellReleaseListDTO(String operatorName, String productName) {
        this.operatorName = operatorName;
        this.productName = productName;
    }
}
