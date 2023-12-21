package com.wellnetworks.wellcore.java.dto.Product;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellProductCreateDTO {
    @NotNull(message = "출력 여부는 필수 항목입니다.")
    private Boolean visibleFlag;

    private Boolean openingHistorySearchFlag;

    @NotBlank(message = "통신사명은 필수 항목입니다.")
    private String operatorName;

    @NotBlank(message = "통신망은 필수 항목입니다.")
    private String network;

    @NotNull(message = "기본요금은 필수 항목입니다.")
    private Integer baseFee;

    @NotBlank(message = "요금제 구분은 필수 항목입니다.")
    private String productType;

    @NotBlank(message = "요금제명은 필수 항목입니다.")
    private String productName;

    @NotBlank(message = "데이터는 필수 항목입니다.")
    private String data;

    @NotBlank(message = "음성은 필수 항목입니다.")
    private String voice;

    @NotBlank(message = "문자 정보는 필수 항목입니다.")
    private String sms;

    @NotBlank(message = "기타 정보는 필수 항목입니다.")
    private String etc;

    @NotBlank(message = "내부 코드는 필수 항목입니다.")
    private String internalCode;

    private String externalCode;

    private String mvnoProductName;
    private String memo;
}

