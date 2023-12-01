package com.wellnetworks.wellcore.java.dto.VirtualAccount;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wellnetworks.wellcore.java.dto.PartnerUser.WellPartnerUserCreateDTO;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellVirtualAccountCreateDTO {
    private String virtualBankName;
    private String virtualAccount;
}
