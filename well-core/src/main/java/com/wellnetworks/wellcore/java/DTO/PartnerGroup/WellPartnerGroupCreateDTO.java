package com.wellnetworks.wellcore.java.dto.PartnerGroup;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WellPartnerGroupCreateDTO {
    @JsonProperty("p_group_id")
    private Long partnerGroupId;

    @Column(name = "p_group_name")
    private String PartnerGroupName;
}
