package com.wellnetworks.wellcore.java.dto.partner;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellPartnerInfoDTO {
    @Schema(description = "거래처idx")
    private String partnerIdx;
    @Schema(description = "파일리스트")
    private List<WellFileDetailDTO> fileDetails = new ArrayList<>();
    @Schema(description = "가입승인날짜")
    private LocalDate subscriptionDate;
    @Schema(description = "거래처 로그인 id로쓰는 코드")
    private String partnerCode;
    @Schema(description = "거래처 이름")
    private String partnerName;
    @Schema(description = "거래처 타입")
    private String partnerType;
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private String discountCategory;
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private Integer dipositBalance;
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private String salesManager;
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private String ceoName;
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private String ceoTelephone;
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private String transactionStatus;
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private String writer;
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private String partnerUpperIdx;
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private String partnerUpperName;
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private String openingStatus; // 개통점 신청 여부
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private String registrationStatus; // 회원가입 승인 여부
    @Schema(description = "파트너 로그인 id로쓰는 코드")
    private String registrationAddress;

    //리스트
    public WellPartnerInfoDTO(WellPartnerEntity entity, List<WellPartnerFIleStorageEntity> fileStorages, WellDipositEntity diposit
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
        this.openingStatus = entity.getOpeningStatus();
        this.registrationStatus = entity.getRegistrationStatus();
        this.dipositBalance = diposit != null ? diposit.getDipositBalance() : 0;

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
        List<String> requiredFileKinds = Arrays.asList("사업자등록증", "계약서", "대표자신분증", "매장사진", "대표자명함");

        // 파일 종류별로 첫 번째 항목을 찾고, 없으면 빈 정보를 추가
        for (String fileKind : requiredFileKinds) {
            WellFileDetailDTO fileDetail = fileStorages.stream()
                    .filter(fileStorage -> fileStorage != null && fileStorage.getFile() != null && fileStorage.getFile().getFileKind().equals(fileKind))
                    .map(fileStorage -> new WellFileDetailDTO(fileStorage.getFile().getId(), fileStorage.getFile().getOriginFileName(), fileStorage.getFile().getFileKind()))
                    .findFirst()
                    .orElse(new WellFileDetailDTO(null, null, fileKind));

            this.fileDetails.add(fileDetail);
        }

        // 중복 제거
        this.fileDetails = this.fileDetails.stream().distinct().collect(Collectors.toList());

        this.registrationAddress = entity.getRegistrationAddress();
    }
}
