package com.wellnetworks.wellcore.java.dto.APIKEYIN;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Validated
public class WellApikeyInCreateDTO {
    private String apiKeyInIdx;
    private String apiKeyIn;
    private LocalDate apiKeyInRegisterDate;
    private boolean apiKeyInEndFlag; // 기본값 false
    private String issuer;
    private List<String> serverUrl;
    private List<String> apiServerIp;
    private String memo;
    private String partnerIdx;

    @Builder
    public WellApikeyInCreateDTO(String apiKeyInIdx, String apiKeyIn, LocalDate apiKeyInRegisterDate, boolean apiKeyInEndFlag, String issuer
                                , List<String> serverUrl, List<String> apiServerIp, String memo, String partnerIdx) {
        this.apiKeyInIdx = apiKeyInIdx;
        this.apiKeyIn = apiKeyIn;
        this.apiKeyInRegisterDate = apiKeyInRegisterDate;
        this.apiKeyInEndFlag = apiKeyInEndFlag;
        this.issuer = issuer;
        this.serverUrl = serverUrl;
        this.apiServerIp = apiServerIp;
        this.memo = memo;
        this.partnerIdx = partnerIdx;
    }
}
