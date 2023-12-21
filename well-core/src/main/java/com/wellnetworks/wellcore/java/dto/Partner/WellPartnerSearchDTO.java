package com.wellnetworks.wellcore.java.dto.Partner;

        import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
        import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
        import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
        import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
        import lombok.AccessLevel;
        import lombok.Data;
        import lombok.NoArgsConstructor;

        import java.time.LocalDate;
        import java.util.ArrayList;
        import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellPartnerSearchDTO {
    private String partnerIdx;
    private List<WellFileDetailDTO> fileDetails = new ArrayList<>();
    private LocalDate subscriptionDate;
    private String partnerCode;
    private String partnerName;
    private String partnerType;
    private String discountCategory;
    private Integer dipositBalance;
    private String salesManager;
    private String ceoName;
    private String ceoTelephone;
    private String transactionStatus;
    private String writer;
    private String partnerUpperIdx;
    private String partnerUpperName;

    //리스트
    public WellPartnerSearchDTO(WellPartnerEntity entity, List<WellPartnerFIleStorageEntity> fileStorages, Integer dipositBalance
            , String partnerUpperName
    ) {
        this.partnerIdx = entity.getPartnerIdx();
        for (WellPartnerFIleStorageEntity fileStorage : fileStorages) {
            if (fileStorage != null && fileStorage.getFile() != null) {
                WellFileDetailDTO fileDetail = new WellFileDetailDTO();
                fileDetail.setFileId(fileStorage.getFile().getId());
                fileDetail.setOriginFileName(fileStorage.getFile().getOriginFileName());
                fileDetail.setFileKind(fileStorage.getFile().getFileKind());
                this.fileDetails.add(fileDetail);
            }
        }
        if (entity.getSubscriptionDate() != null){
            this.subscriptionDate = LocalDate.from(entity.getSubscriptionDate());
        }
        this.partnerCode = entity.getPartnerCode();
        this.partnerName = entity.getPartnerName();
        this.partnerType = entity.getPartnerType();
        this.discountCategory = entity.getDiscountCategory();

        this.dipositBalance = dipositBalance;

        this.salesManager = entity.getSalesManager();
        this.ceoName = entity.getCeoName();
        this.ceoTelephone = entity.getCeoTelephone();
        this.transactionStatus = entity.getTransactionStatus();
        this.writer = entity.getWriter();
        this.partnerUpperIdx = entity.getPartnerUpperIdx();
        if (partnerUpperIdx != null) {
            this.partnerUpperName = partnerUpperName;
        }
        else {
            this.partnerUpperName = null;
        }
    }
}
