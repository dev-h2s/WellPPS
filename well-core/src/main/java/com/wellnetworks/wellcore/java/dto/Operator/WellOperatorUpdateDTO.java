package com.wellnetworks.wellcore.java.dto.Operator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellOperatorUpdateDTO {
    @NotBlank
    private String operatorName; //통신사명

    private Boolean isOpeningSearchFlag; // 출력여부
}
