package com.wellnetworks.wellcore.java.dto.PIN;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellReleaseDTO {
    private String operatorName; //통신사 (코드)
    private String productName; //요금제 (코드)
    private String pinNum; //PIN번호

    private String release; //출고처(거래처)
    private Boolean isUseFlag; //사용유무
}
