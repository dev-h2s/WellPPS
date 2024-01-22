package com.wellnetworks.wellcore.java.dto.PIN;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellnetworks.wellcore.java.domain.pin.WellPinEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPinListDTO {
    @Schema(description = "핀 고유번호")
    private Long pinIdx;
    @Schema(description = "생성일")
    @JsonFormat(pattern = "yyyy-MM-dd.HH:mm:ss")
    private LocalDateTime createdAt;
    @Schema(description = "통신사")
    private String operatorName;
    @Schema(description = "요금제")
    private String productName;
    @Schema(description = "통신망")
    private String network;
    @Schema(description = "PIN 번호")
    private String pinNum;
    @Schema(description = "관리번호")
    private String managementNum;
    @Schema(description = "입고처")
    private String store;
    @Schema(description = "출고처")
    private String release;
    @Schema(description = "입력자")
    private String writer;
    @Schema(description = "사용자")
    private String user;
    @Schema(description = "사용날짜")
    @JsonFormat(pattern = "yyyy-MM-dd.HH:mm:ss")
    private LocalDateTime usedAt;
    @Schema(description = "판매전용여부")
    private Boolean isSaleFlag;

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
