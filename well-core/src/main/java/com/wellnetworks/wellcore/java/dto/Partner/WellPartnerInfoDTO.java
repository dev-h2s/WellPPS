package com.wellnetworks.wellcore.java.dto.Partner;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class WellPartnerInfoDTO {
    private String partnerIdx;
    private String transactionStatus;
    private List<String> fileKinds = new ArrayList<>();
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

    public WellPartnerInfoDTO(WellPartnerEntity entity, List<WellPartnerFIleStorageEntity> fileStorages, WellDipositEntity diposit) {
        this.partnerIdx = entity.getPartnerIdx();
        this.transactionStatus = entity.getTransactionStatus();

        for (WellPartnerFIleStorageEntity fileStorage : fileStorages) {
            if (fileStorage != null) {
                String fileKind = fileStorage.getFile().getContentType(); // 파일 저장소 엔티티의 종류 가져오기
                // fileKind와 원하는 종류를 비교하여 일치하는 경우에만 리스트에 추가
                if (fileKind.equals(fileKind)) {
                    fileKinds.add(fileStorage.getFile().getContentType()); // 첨부파일 엔티티를 리스트에 추가
                }
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
