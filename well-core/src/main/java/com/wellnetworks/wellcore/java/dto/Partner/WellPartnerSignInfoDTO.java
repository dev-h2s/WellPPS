package com.wellnetworks.wellcore.java.dto.Partner;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
import jakarta.persistence.Column;
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
public class WellPartnerSignInfoDTO {
    private String partnerIdx;
    private List<WellFileDetailDTO> fileDetails = new ArrayList<>();

// 요청일자 넣어야함
    private String discountCategory; // 가입형태
    private String partnerName; //거래처명
    private String ceoName; //대표자명
    private Boolean visitStatus; //방문요청여부
    private String registrationAddress; // 방문주소
    private LocalDateTime openingVisitRequestDate; //방문 희망일자
    private String registrationStatus; // 회원가입 승인 여부(승인대기)
    private String rejectionReason; // 회원가입 거부사유
    // 검수일자
    //검수자



    //리스트
    public WellPartnerSignInfoDTO(WellPartnerEntity entity, List<WellPartnerFIleStorageEntity> fileStorages, WellDipositEntity diposit
            , String partnerUpperName) {
        this.partnerIdx = entity.getPartnerIdx();
        this.discountCategory = entity.getDiscountCategory();
        this.partnerName = entity.getPartnerName();
        this.ceoName = entity.getCeoName();
        this.visitStatus =entity.getVisitStatus();
        this.registrationAddress = entity.getRegistrationAddress();
        this.openingVisitRequestDate = entity.getOpeningVisitRequestDate();
        this.registrationStatus = entity.getRegistrationStatus();
        this.rejectionReason = entity.getRejectionReason();

        for (WellPartnerFIleStorageEntity fileStorage : fileStorages) {
            if (fileStorage != null && fileStorage.getFile() != null) {
                WellFileDetailDTO fileDetail = new WellFileDetailDTO();
                fileDetail.setFileId(fileStorage.getFile().getId());
                fileDetail.setOriginFileName(fileStorage.getFile().getOriginFileName());
                fileDetail.setFileKind(fileStorage.getFile().getFileKind());
                this.fileDetails.add(fileDetail);
            }
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
