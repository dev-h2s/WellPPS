package com.wellnetworks.wellcore.java.dto.VirtualAccount;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellVirtualAccountIssueDTO {
    private String partnerIdx;
    private String virtualBankName; // 선택한 은행 이름

    public WellVirtualAccountIssueDTO(WellVirtualAccountEntity virtualAccount, WellPartnerEntity partnerEntity
    ) {
        this.partnerIdx = partnerEntity.getPartnerIdx();
        this.virtualBankName = virtualAccount.getVirtualBankName();
    }
}
