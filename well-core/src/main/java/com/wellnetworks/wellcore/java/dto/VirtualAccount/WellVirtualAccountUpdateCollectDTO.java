package com.wellnetworks.wellcore.java.dto.VirtualAccount;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellVirtualAccountUpdateCollectDTO {
    private String virtualAccountIdx;
    private String issuance;
}