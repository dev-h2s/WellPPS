package com.wellnetworks.wellcore.java.dto.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellProductUpdateDTO {
    @NotNull(message = "출력 여부는 필수 항목입니다.")
    private Boolean visibleFlag;

    private Boolean openingHistorySearchFlag;

    @NotBlank(message = "요금제명은 필수 항목입니다.")
    private String productName;

    private String mvnoProductName;

    @NotBlank(message = "데이터는 필수 항목입니다.")
    private String data;

    @NotBlank(message = "음성은 필수 항목입니다.")
    private String voice;

    @NotBlank(message = "문자 정보는 필수 항목입니다.")
    private String sms;

    @NotBlank(message = "기타 정보는 필수 항목입니다.")
    private String etc;

    private String externalCode;

    private String memo;
}
