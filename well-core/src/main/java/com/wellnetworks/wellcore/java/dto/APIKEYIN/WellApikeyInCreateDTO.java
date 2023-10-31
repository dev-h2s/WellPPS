package com.wellnetworks.wellcore.java.dto.APIKEYIN;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WellApikeyInCreateDTO {
    @JsonProperty("api_key_in_idx")
    private String apiKeyInIdx;
    @JsonProperty("api_key_in")
    private String apiKeyIn;
    @JsonProperty("api_key_in_reg_dt")
    private LocalDateTime apiKeyInRegisterDate;
    @JsonProperty("api_key_in_end_flag")
    private Boolean apiKeyInEndFlag;
    @JsonProperty("api_key_in_update")
    private LocalDateTime apiKeyInUpdate;
    @JsonProperty("p_agree_flag")
    private Boolean partnerAgreeFlag;
    @JsonProperty("issuer")
    private String issuer;
    @JsonProperty("server_url")
    private String serverUrl;
    @JsonProperty("api_server_ip")
    private String apiServerIp;
    @JsonProperty("memo")
    private String memo;
}
