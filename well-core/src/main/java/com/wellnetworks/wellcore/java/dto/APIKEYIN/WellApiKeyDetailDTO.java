package com.wellnetworks.wellcore.java.dto.APIKEYIN;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellApiKeyDetailDTO {
    private String apiKeyInIdx;
    private String apiKeyIn;
    private LocalDate apiKeyInRegisterDate;

    private String partnerIdx;
    private String partnerName;

    private boolean apiKeyInEndFlag;
    private String issuer;
    private List<String> serverUrl;
    private List<String> apiServerIp;
    private String memo;

    private boolean home;
    private boolean dream;
    private boolean valueCom;
    private boolean iz;
    private boolean asia;
    private boolean PDS;

    public WellApiKeyDetailDTO(WellApikeyInEntity apikey, String partnerName) {


        this.apiKeyInIdx = apikey.getApiKeyInIdx();
        this.apiKeyIn = apikey.getApiKeyIn();
        this.apiKeyInRegisterDate = apikey.getApiKeyInRegisterDate();

        this.partnerIdx = apikey.getPartnerIdx();
        if (partnerIdx != null) {
            this.partnerName = partnerName;
        } else {
            this.partnerName = null;
        }


        this.apiKeyInEndFlag = apikey.isApiKeyInEndFlag();
        this.serverUrl = apikey.getServerUrl();
        this.apiServerIp = apikey.getApiServerIp();
        this.issuer = apikey.getIssuer();
        this.memo = apikey.getMemo();


        this.home = apikey.isHome();
        this.dream = apikey.isDream();
        this.valueCom = apikey.isValueCom();
        this.iz = apikey.isIz();
        this.asia = apikey.isAsia();
        this.PDS = apikey.isPDS();
    }
}
