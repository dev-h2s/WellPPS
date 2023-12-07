package com.wellnetworks.wellcore.java.dto.Diposit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellDipositListDTO {
    @JsonFormat(pattern = "yyyy.MM.dd.HH:mm:ss")
    private LocalDateTime registerDate;
    private String partnerIdx;
    private String partnerName;
    private boolean dipositAdjustment;
    private String dipositStatus;
    private Integer dipositAmount;
    private Integer dipositBalance;
    private String memo;
    private String writer;

    //리스트 & 검색
    public WellDipositListDTO(WellDipositEntity dipositEntity, WellPartnerEntity partnerEntity, String partnerName
                            ) {

        this.registerDate = dipositEntity.getRegisterDate();
        if (partnerEntity != null) {
            this.partnerIdx = String.valueOf(partnerEntity.getPartnerIdx());
            this.partnerName = partnerName;
        } else {
            this.partnerIdx = "";
            this.partnerName = "";
        }
        this.dipositAdjustment = dipositEntity.isDipositAdjustment();
        this.dipositStatus = dipositEntity.getDipositStatus();
        this.dipositAmount = (dipositEntity.getDipositAmount() != null) ? dipositEntity.getDipositAmount() : 0;
        this.dipositBalance = (dipositEntity.getDipositBalance() != null) ? dipositEntity.getDipositBalance() : 0;
        this.memo = dipositEntity.getMemo();
        this.writer = dipositEntity.getWriter();
    }

}
