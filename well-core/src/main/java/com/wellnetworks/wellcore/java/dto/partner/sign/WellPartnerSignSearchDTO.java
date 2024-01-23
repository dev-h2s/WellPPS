package com.wellnetworks.wellcore.java.dto.partner.sign;

import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellPartnerSignSearchDTO {
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

    private String registrationAddress;
    //검색시 필요
    private LocalDate reviewDate;// 검수일자
    private String reviewer;//검수자
    private LocalDate signRequestDate;// (회원가입) 요청일자
    private Boolean visitStatus; //방문요청여부
    private LocalDateTime openingVisitRequestDate; //방문 희망일자
    private String registrationStatus; // 회원가입 승인 여부(승인대기)
    private String rejectionReason; // 회원가입 거부사유

    //리스트
    public WellPartnerSignSearchDTO(WellPartnerEntity entity, List<WellPartnerFIleStorageEntity> fileStorages, Integer dipositBalance
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
        this.reviewDate = entity.getReviewDate();// 검수일자
        this.reviewer = entity.getReviewer();//검수자
        if (entity.getSignRequestDate() != null) {
            this.signRequestDate = LocalDate.from(entity.getSignRequestDate());// (회원가입) 요청일자
        } else {
            this.signRequestDate = null; //
        }
        this.visitStatus = entity.getVisitStatus(); //방문요청여부
        this.openingVisitRequestDate = entity.getOpeningVisitRequestDate(); //방문 희망일자
        this.registrationStatus = entity.getRegistrationStatus(); // 회원가입 승인 여부(승인대기)
        this.rejectionReason = entity.getRejectionReason(); // 회원가입 거부사유
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
