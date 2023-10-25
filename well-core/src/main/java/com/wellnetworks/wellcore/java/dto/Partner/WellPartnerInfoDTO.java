package com.wellnetworks.wellcore.java.dto.Partner;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WellPartnerInfoDTO {
    private String partnerIdx;
    private String transactionStatus;
    private String fileKind;
    private LocalDateTime productRegisterDate;
    private LocalDateTime productModifyDate;
    private String partnerCode;
    private String partnerName;
    private String partnerType;
    private String discountCategory;
    private Integer dipositBalance;
    private String salesManager;
    private String ceoName;
    private String partnerTelephone;
    private String writer;
    private String partnerUpperIdx;
    private Integer size;
    private Integer page;

    public WellPartnerInfoDTO(WellPartnerEntity entity, List<WellFileStorageEntity> fileStorages, WellDipositEntity diposit) {
        this.partnerIdx = entity.getPartnerIdx();
        this.transactionStatus = entity.getTransactionStatus();

        for (WellFileStorageEntity fileStorage : fileStorages) {
            if (fileStorage != null) {
                 this.fileKind = fileStorage.getFileKind();
            }
        }

        this.productRegisterDate = entity.getProductRegisterDate();
        this.productModifyDate = entity.getProductModifyDate();
        this.partnerCode = entity.getPartnerCode();
        this.partnerName = entity.getPartnerName();
        this.partnerType = entity.getPartnerType();
        this.discountCategory = entity.getDiscountCategory();

        if (diposit != null) {
            this.dipositBalance = diposit.getDipositBalance();
        }

        this.salesManager = entity.getSalesManager();
        this.ceoName = entity.getCeoName();
        this.partnerTelephone = entity.getPartnerTelephone();
        this.writer = entity.getWriter();
        this.partnerUpperIdx = entity.getPartnerUpperIdx();
        this.size = entity.getSize();
        this.page = entity.getPage();
    }



    //동일한 거래처 나타내는지 판단
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WellPartnerInfoDTO other = (WellPartnerInfoDTO) obj;

        return partnerIdx != null && partnerIdx.equals(other.partnerIdx);
    }
}
