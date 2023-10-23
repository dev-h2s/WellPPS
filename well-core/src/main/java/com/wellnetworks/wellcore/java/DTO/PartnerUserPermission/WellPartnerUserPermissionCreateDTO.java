package com.wellnetworks.wellcore.java.dto.PartnerUserPermission;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WellPartnerUserPermissionCreateDTO {
    @JsonProperty("pm_gkey")
    private String partnerManagerGroupKey;
    @JsonProperty("pm_name")
    private String partnerManagerGroupName;
    @JsonProperty("pm_permission")
    private String partnerManagerGroupPermission;
    @JsonProperty("pm_description")
    private String partnerManagerGroupDescription;
    @JsonProperty("pm_moddt")
    private LocalDateTime partnerManagerGroupModifyDate;
    @JsonProperty("pm_regdt")
    private LocalDateTime partnerManagerGroupRegisterDate;
}
