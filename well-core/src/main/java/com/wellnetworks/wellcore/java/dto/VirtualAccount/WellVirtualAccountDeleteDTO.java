package com.wellnetworks.wellcore.java.dto.VirtualAccount;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Validated
public class WellVirtualAccountDeleteDTO {
    private String virtualAccountIdx;
}