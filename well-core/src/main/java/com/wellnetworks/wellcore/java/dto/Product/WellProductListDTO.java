package com.wellnetworks.wellcore.java.dto.Product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WellProductListDTO {

    private String productName; //요금제명

    private String network; //통신망

    private String productType; //요금제구분

    private Integer baseFee; //기본요금

    private String data; //데이터

    private String voice; //음성

    private String sms; //문자

    private String etc; //기타

    private Boolean visibleFlag; // 출력여부
}
