package com.wellnetworks.wellcore.java.dto.partner;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class WellSubPartnerDetailDTO {
    private String subPartnerIdx; // 하위 파트너의 인덱스
    private String subPartnerName; // 하위 파트너의 이름
}