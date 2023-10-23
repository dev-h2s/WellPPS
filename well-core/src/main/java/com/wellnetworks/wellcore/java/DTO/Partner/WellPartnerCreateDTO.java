package com.wellnetworks.wellcore.java.dto.Partner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApikeyInCreateDTO;
import com.wellnetworks.wellcore.java.dto.Diposit.WellDipositCreateDTO;
import com.wellnetworks.wellcore.java.dto.FIle.WellFIleCreateDTO;
import com.wellnetworks.wellcore.java.dto.FIle.WellPartnerFileCreateDTO;
import com.wellnetworks.wellcore.java.dto.PartnerUser.WellPartnerUserCreateDTO;
import com.wellnetworks.wellcore.java.dto.PartnerGroup.WellPartnerGroupCreateDTO;
import com.wellnetworks.wellcore.java.dto.VirtualAccount.WellVirtualAccountCreateDTO;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

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

