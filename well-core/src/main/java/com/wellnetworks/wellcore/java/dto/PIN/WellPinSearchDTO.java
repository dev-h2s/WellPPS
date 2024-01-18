package com.wellnetworks.wellcore.java.dto.PIN;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellPinSearchDTO {
    private Boolean isSaleFlag; //판매전용여부
    private Boolean isUseFlag; //사용유무
    private String network; //통신망 (코드)
    private String operatorName; //통신사 (코드)
    private String productName; //요금제 (코드)
    private String pinNum; //PIN번호
    private String managementNum; //관리번호
    private String writer; //입력자
    private String user; //사용자
}
