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
    private String apiKeyIn;
    private LocalDate apiKeyInRegisterDate;
    private String partnerName; // 거래처의 거래처명
    private boolean apiKeyInEndFlag; // 기본값 false
    private boolean partnerAgreeFlag; // 기본값 false
    private String issuer;
    private List<String> serverUrl;
    private List<String> apiServerIp;
    private String memo;

    @Builder
    public WellApikeyInCreateDTO(String apiKeyIn, LocalDate apiKeyInRegisterDate, boolean apiKeyInEndFlag, boolean partnerAgreeFlag, String issuer
                                , List<String> serverUrl, List<String> apiServerIp, String memo) {
        this.apiKeyIn = apiKeyIn;
        this.apiKeyInRegisterDate = apiKeyInRegisterDate;
        this.apiKeyInEndFlag = apiKeyInEndFlag;
        this.partnerAgreeFlag = partnerAgreeFlag;
        this.issuer = issuer;
        this.serverUrl = serverUrl;
        this.apiServerIp = apiServerIp;
        this.memo = memo;
    }
}
