package com.wellnetworks.wellcore.java.dto.partner;

import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
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
public class WellPartnerSearchDTO {
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
    @Column(name = "충전 할인율 구분")
    private String discountCategory;
    @Schema(description = "예치금잔액")
    private Integer dipositBalance;
    @Schema(description = "영업담당자")
    private String salesManager;
    @Schema(description = "대표자명")
    private String ceoName;
    @Schema(description = "대표자전화번호")
    private String ceoTelephone;
    @Schema(description = "거래유무")
    private String transactionStatus;
    @Schema(description = "작성자")
    private String writer;
    @Schema(description = "상부점 idx")
    private String partnerUpperIdx;
    @Schema(description = "상부점 명")
    private String partnerUpperName;
    @Schema(description = "개통점전환여부")
    private String openingStatus;
    @Schema(description = "회원가입 여부")
    private String registrationStatus; // 회원가입 승인 여부
    @Schema(description = "사업자등록증주소")
    private String registrationAddress;


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

        this.registrationAddress = entity.getRegistrationAddress();
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

    }
}
