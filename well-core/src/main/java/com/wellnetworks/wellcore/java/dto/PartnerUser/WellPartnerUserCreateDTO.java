package com.wellnetworks.wellcore.java.dto.PartnerUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerPermissionGroupEntity;
import com.wellnetworks.wellcore.java.dto.PartnerUserPermission.WellPartnerUserPermissionCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WellPartnerUserCreateDTO {
    @JsonProperty("pm_gkey")
    private String partnerManagerGroupKey;
    @JsonProperty("p_identification")
    private String partnerIdentification;
    @JsonProperty("pwd")
    private String partnerUserPwd;
    @JsonProperty("permissions")
    private String permissions;
    @JsonProperty("tmp_pwd")
    private String tmpPwd;
    @JsonProperty("tmp_pwd_expiration")
    private LocalDateTime tmpPwdExpiration;
    @JsonProperty("tmp_pwd_count")
    private Integer tmpPwdCount;
    @JsonProperty("tmp_pwd_dt")
    private LocalDateTime tmpPwdDate;
    @JsonProperty("p_u_moddt")
    private LocalDateTime partnerUserModifyDate;
    @JsonProperty("p_u_regdt")
    private LocalDateTime partnerUserRegisterDate;
}
