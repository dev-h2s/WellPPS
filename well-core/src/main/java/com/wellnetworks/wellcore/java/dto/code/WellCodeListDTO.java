package com.wellnetworks.wellcore.java.dto.code;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellCodeListDTO {

    @Schema(description = "코드 구분", example = "은행명, 국적...")
    private String cType;

    public WellCodeListDTO(String cType) {
        this.cType = cType;
    }
}
