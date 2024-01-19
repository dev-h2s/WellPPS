package com.wellnetworks.wellcore.java.dto.PIN;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellnetworks.wellcore.java.domain.pin.WellPinEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPinListDTO {
    private Long pinIdx;
    @JsonFormat(pattern = "yyyy-MM-dd.HH:mm:ss")
    private LocalDateTime createdAt; //생성일
    private String operatorName; //통신사 (코드)
    private String productName; //요금제 (코드)
    private String network; //통신망 (코드)
    private String pinNum; //PIN번호
    private String managementNum; //관리번호
    private String partnerIdx; //거래처 idx
    private String store; //입고처(거래처)
    private String release; //출고처(거래처)
    private String writer; //입력자
    private String user; //사용자
    @JsonFormat(pattern = "yyyy-MM-dd.HH:mm:ss")
    private LocalDateTime usedAt; //사용날짜
    private Boolean isSaleFlag; //판매전용여부

    @Builder
    public WellPinListDTO(WellPinEntity pinEntity, String operatorName, String productName, String storeName, String releaseName) {
        this.pinIdx = pinEntity.getPinIdx();
        this.operatorName = operatorName;
        this.productName = productName;
        this.store = storeName;
        this.release = releaseName;
        this.network = pinEntity.getNetwork();
        this.pinNum = pinEntity.getPinNum();
        this.managementNum = pinEntity.getManagementNum();
        this.user = pinEntity.getUserName();
        this.usedAt = pinEntity.getUsedAt();
        this.isSaleFlag = pinEntity.getIsSaleFlag();
        this.createdAt = pinEntity.getCreatedAt();
        this.writer = pinEntity.getWriter();
    }
}
