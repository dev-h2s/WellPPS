package com.wellnetworks.wellcore.java.dto.partner;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WellSubPartnerDetailDTO {
    @Schema(description = "하위 파트너의 인덱스")
    private String subPartnerIdx;
    @Schema(description = "하위 파트너의 이름")
    private String subPartnerName;
}