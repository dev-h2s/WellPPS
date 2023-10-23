package com.wellnetworks.wellcore.java.dto.VirtualAccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wellnetworks.wellcore.java.dto.PartnerUser.WellPartnerUserCreateDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WellVirtualAccountCreateDTO {
    @JsonProperty("v_account_idx")
    private String virtualAccountIdx;
    @JsonProperty("p_idx")
    private WellPartnerUserCreateDTO partnerIdx;
    @JsonProperty("reg_dt")
    private LocalDateTime registerDate;
    @JsonProperty("writer")
    private String writer;
    @JsonProperty("v_bank_name")
    private String virtualBankName;
    @JsonProperty("v_account")
    private String virtualAccount;
    @JsonProperty("v_bank_holder")
    private String virtualBankHolder;
    @JsonProperty("issue_flag")
    private Boolean issueFlag;
    @JsonProperty("issue_date")
    private LocalDateTime issueDate;
}
