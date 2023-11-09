package com.wellnetworks.wellcore.java.dto.Partner;

import com.querydsl.core.annotations.QueryProjection;
import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPartnerInfoDTO {
    private String partnerIdx;
    private List<String> fileKinds = new ArrayList<>();
    private LocalDateTime productModifyDate;
    private String partnerCode;
    private String partnerName;
    private String partnerType;
    private String discountCategory;
    private Integer dipositBalance;
    private String salesManager;
    private String ceoName;
    private String ceoTelephone;
    private String writer;
    private String partnerUpperIdx;
    private String partnerUpperName;
    private Integer size;
    private Integer page;

    private Long registeredCount;
    private Long preRegisteredCount;
    private Long managementCount;
    private Long suspendedCount;


    private WellPartnerRepository wellPartnerRepository;

    //거래처 1개, 리스트
    public WellPartnerInfoDTO(WellPartnerEntity entity, List<WellPartnerFIleStorageEntity> fileStorages, WellDipositEntity diposit
                                , Long registeredCount, Long preRegisteredCount, Long managementCount, Long suspendedCount
            , String partnerUpperName
                                ) {
        this.partnerIdx = entity.getPartnerIdx();
        for (WellPartnerFIleStorageEntity fileStorage : fileStorages) {
            if (fileStorage != null) {
                String fileKind = fileStorage.getFile().getFileKind(); // 파일 저장소 엔티티의 종류 가져오기
                // fileKind와 원하는 종류를 비교하여 일치하는 경우에만 리스트에 추가
                if (fileKind.equals(fileKind)) {
                    fileKinds.add(fileStorage.getFile().getFileKind()); // 첨부파일 엔티티를 리스트에 추가
                }
            }
        }
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
        this.ceoTelephone = entity.getCeoTelephone();
        this.writer = entity.getWriter();
        this.partnerUpperIdx = entity.getPartnerUpperIdx();
        if (partnerUpperIdx != null) {
            this.partnerUpperName = partnerUpperName;
        }
        else {
            this.partnerUpperName = null;
        }
        this.size = entity.getSize();
        this.page = entity.getPage();

        this.registeredCount = registeredCount;
        this.preRegisteredCount = preRegisteredCount;
        this.managementCount = managementCount;
        this.suspendedCount = suspendedCount;
    }

    //거래처 검색
    public WellPartnerInfoDTO(WellPartnerEntity entity, List<WellPartnerFIleStorageEntity> fileStorages, WellDipositEntity diposit
                              , String partnerUpperName
    ) {
        this.partnerIdx = entity.getPartnerIdx();
        for (WellPartnerFIleStorageEntity fileStorage : fileStorages) {
            if (fileStorage != null) {
                String fileKind = fileStorage.getFile().getFileKind(); // 파일 저장소 엔티티의 종류 가져오기
                // fileKind와 원하는 종류를 비교하여 일치하는 경우에만 리스트에 추가
                if (fileKind.equals(fileKind)) {
                    fileKinds.add(fileStorage.getFile().getFileKind()); // 첨부파일 엔티티를 리스트에 추가
                }
            }
        }
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
        this.ceoTelephone = entity.getCeoTelephone();
        this.writer = entity.getWriter();
        this.partnerUpperIdx = entity.getPartnerUpperIdx();
        if (partnerUpperIdx != null) {
            this.partnerUpperName = partnerUpperName;
        }
        else {
            this.partnerUpperName = null;
        }
        this.size = entity.getSize();
        this.page = entity.getPage();
    }
}