package com.wellnetworks.wellcore.java.dto.VirtualAccount;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellVirtualAccountUpdateDTO {
    private String virtualAccountIdx;
    private String partnerIdx; // 새로 연결할 거래처의 ID
}
