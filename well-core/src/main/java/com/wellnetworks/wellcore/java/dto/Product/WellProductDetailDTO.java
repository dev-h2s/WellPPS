package com.wellnetworks.wellcore.java.dto.Product;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WellProductDetailDTO {
    private Boolean visibleFlag; // 출력여부

    private Boolean openingHistorySearchFlag; //개통내역검색여부

    private String operatorName; //통신사

    private String network; //통신망

    private Integer baseFee; //기본요금

    private String productType; //요금제구분

    private String productName; //요금제명

    private String mvnoProductName; //MVNO 요금제명

    private String data; //데이터

    private String voice; //음성

    private String etc; //기타

    private String sms; //문자

    private String internalCode; //요금제코드(내부코드)

    private String externalCode; //요금제코드(외부코드)

    private String memo; // 메모
}
