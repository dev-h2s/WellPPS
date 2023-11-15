package com.wellnetworks.wellcore.java.dto.APIKEYIN;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellApiKeyInfoDTO {
    private LocalDate apiKeyInRegisterDate;
    private String partnerName; // 거래처의 거래처명
    private String issuer;
    private String memo;

    //1개 조회
    //검색
}
