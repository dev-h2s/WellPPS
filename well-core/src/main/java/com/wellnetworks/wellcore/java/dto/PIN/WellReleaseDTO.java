package com.wellnetworks.wellcore.java.dto.PIN;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellReleaseDTO {
    private String operatorName; // 통신사 (코드)
    private String productName; // 요금제 (코드)
    private String pinNum; // PIN번호

    @Builder
    public WellReleaseDTO(String operatorName, String productName, String pinNum) {
        this.operatorName = operatorName;
        this.productName = productName;
        this.pinNum = pinNum;
    }
}
