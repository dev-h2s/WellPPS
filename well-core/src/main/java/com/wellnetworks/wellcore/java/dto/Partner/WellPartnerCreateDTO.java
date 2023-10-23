package com.wellnetworks.wellcore.java.dto.Partner;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WellPartnerCreateDTO {
    @JsonProperty("p_code")
    private String parterCode;
    @JsonProperty("p_name")
    private String partnerName;
    @JsonProperty("p_type")
    private String partnerType;
    @JsonProperty("special_policy_opening")
    private boolean specialPolicyOpening;
    @JsonProperty("special_policy_charge")
    private boolean specialPolicyCharge;

    @JsonProperty("p_group_id")
    private Long partnerGroupId; // 거래처 그룹 정보 추가
}

