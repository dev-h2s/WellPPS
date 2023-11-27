package com.wellnetworks.wellcore.java.dto.APIKEYIN;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Validated
public class WellApiKeyUpdateDTO {
    private String apiKeyInIdx;
    private String apiKeyIn;
    private LocalDate apiKeyInRegisterDate;
    private boolean apiKeyInEndFlag;
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

    private boolean expire; // 만료여부판단
}
