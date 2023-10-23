package com.wellnetworks.wellcore.java.dto.APIKEYIN;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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
    @JsonProperty("in_api_flag")
    private Boolean inApiFlag;
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
