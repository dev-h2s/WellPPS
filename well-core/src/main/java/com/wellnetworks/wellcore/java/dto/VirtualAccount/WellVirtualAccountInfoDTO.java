package com.wellnetworks.wellcore.java.dto.VirtualAccount;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellVirtualAccountInfoDTO {
    private String virtualAccountIdx;
    private LocalDate registerDate;
    private String virtualBankName;
    private String virtualAccount;
    private String issuance;
    private String partnerIdx;
    private String partnerName;
    @JsonFormat(pattern = "yyyy.MM.dd.HH:mm:ss")
    private LocalDateTime issueDate;
    private String writer;

    //리스트, 검색
    public WellVirtualAccountInfoDTO(WellVirtualAccountEntity virtualAccount, WellPartnerEntity partnerEntity, String partnerName
    ) {

        this.virtualAccountIdx = virtualAccount.getVirtualAccountIdx();
        this.registerDate = virtualAccount.getRegisterDate();
        this.virtualBankName = virtualAccount.getVirtualBankName();
        this.virtualAccount = virtualAccount.getVirtualAccount();
        this.issuance = virtualAccount.getIssuance();
        if (partnerEntity != null) {
            this.partnerIdx = String.valueOf(partnerEntity.getPartnerIdx());
            this.partnerName = partnerName;
        } else {
            this.partnerIdx = "";
            this.partnerName = "";
        }
        this.issueDate = virtualAccount.getIssueDate();
        this.writer = virtualAccount.getWriter();
    }


}
