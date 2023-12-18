package com.wellnetworks.wellcore.java.dto.Operator;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WellOperatorCreateDTO {
    @NotNull
    private String operatorName; //통신사명

    @NotNull
    @UpperCase(message = "통신사 코드는 무조건 대문자 3글자로 작성되어야 합니다.")
    @Size(min = 3, max = 3, message = "통신사 코드는 3글자여야 합니다.")
    private String operatorCode; // 통신사 코드

    private Boolean isOpeningSearchFlag; // 출력여부
}




