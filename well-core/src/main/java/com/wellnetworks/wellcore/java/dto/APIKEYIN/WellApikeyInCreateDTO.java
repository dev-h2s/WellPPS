package com.wellnetworks.wellcore.java.dto.APIKEYIN;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Validated
public class WellApikeyInCreateDTO {
    private String apiKeyInIdx;
    private String apiKeyIn;
    private LocalDate apiKeyInRegisterDate;
    private boolean apiKeyInEndFlag; // 기본값 false
    private boolean partnerAgreeFlag; // 기본값 false
    private String issuer;
    private List<String> serverUrl;
    private List<String> apiServerIp;
    private String memo;
    private String partnerIdx;

    private boolean home;
    private boolean dream;
    private boolean valueCom;
    private boolean iz;
    private boolean asia;
    private boolean PDS;

    @Builder
    public WellApikeyInCreateDTO(String apiKeyInIdx, String apiKeyIn, LocalDate apiKeyInRegisterDate, boolean apiKeyInEndFlag, boolean partnerAgreeFlag, String issuer
                                , List<String> serverUrl, List<String> apiServerIp, String memo, String partnerIdx
                                , boolean home, boolean dream, boolean valueCom, boolean iz, boolean asia, boolean PDS) {
        this.apiKeyInIdx = apiKeyInIdx;
        this.apiKeyIn = apiKeyIn;
        this.apiKeyInRegisterDate = apiKeyInRegisterDate;
        this.apiKeyInEndFlag = apiKeyInEndFlag;
        this.partnerAgreeFlag = partnerAgreeFlag;
        this.issuer = issuer;
        this.serverUrl = serverUrl;
        this.apiServerIp = apiServerIp;
        this.memo = memo;
        this.partnerIdx = partnerIdx;

        this.home = home;
        this.dream = dream;
        this.valueCom = valueCom;
        this.iz = iz;
        this.asia = asia;
        this.PDS = PDS;
    }
}
