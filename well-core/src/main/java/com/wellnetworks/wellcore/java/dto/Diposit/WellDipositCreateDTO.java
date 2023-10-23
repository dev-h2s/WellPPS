package com.wellnetworks.wellcore.java.dto.Diposit;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wellnetworks.wellcore.java.dto.VirtualAccount.WellVirtualAccountCreateDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WellDipositCreateDTO {
    @JsonProperty("dipo_idx")
    private String dipositIdx;
    @JsonProperty("v_account_idx")
    private WellVirtualAccountCreateDTO virtualAccountIdx;
    @JsonProperty("dipo_balance")
    private Integer dipositBalance;
    @JsonProperty("depo_adjustment")
    private String depositAdjustment;
    @JsonProperty("inc_dec_details")
    private String incDecDetails;
    @JsonProperty("charge_amount")
    private Integer chargeAmount;
    @JsonProperty("depo_amount")
    private Integer depositAmount;
    @JsonProperty("deduction_amount")
    private Integer deductionAmount;
    @JsonProperty("depo_status")
    private String depositStatus;
    @JsonProperty("depositor")
    private String depositor;
    @JsonProperty("reg_dt")
    private LocalDateTime registerDate;
    @JsonProperty("memo")
    private String memo;
    @JsonProperty("contents")
    private String contents;
    @JsonProperty("writer")
    private String writer;

}
