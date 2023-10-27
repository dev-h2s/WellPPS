package com.wellnetworks.wellcore.java.dto.PartnerUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerPermissionGroupEntity;
import com.wellnetworks.wellcore.java.dto.PartnerUserPermission.WellPartnerUserPermissionCreateDTO;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WellPartnerUserCreateDTO {
    @JsonProperty("p_id")
    private Long partnerId;
    @JsonProperty("pm_gkey")
    private WellPartnerUserPermissionCreateDTO partnerManagerGroupKey;
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
    private int tmpPwdCount;
    @JsonProperty("tmp_pwd_dt")
    private LocalDateTime tmpPwdDate;
    @JsonProperty("p_u_moddt")
    private LocalDateTime partnerUserModifyDate;
    @JsonProperty("p_u_regdt")
    private LocalDateTime partnerUserRegisterDate;
}
