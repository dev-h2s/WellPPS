package com.wellnetworks.wellcore.java.dto.APIKEYIN;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class  WellApiKeyInfoDTO {
    private LocalDate apiKeyInRegisterDate;
    private String partnerIdx;
    private String partnerName; // 거래처의 거래처명
    private String issuer;
    private String memo;

    //1개 조회, 리스트, 검색
    public WellApiKeyInfoDTO(WellApikeyInEntity apikey, String partnerName) {

        this.apiKeyInRegisterDate = apikey.getApiKeyInRegisterDate();
        this.partnerIdx = apikey.getPartnerIdx();
        if (partnerIdx != null) {
            this.partnerName = partnerName;
        } else {
            this.partnerName = null;
        }
        this.issuer = apikey.getIssuer();
        this.memo = apikey.getMemo();
    }
}
