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
    @Schema(description = "핀 고유번호", example = "1")
    private Long pinIdx;
    @Schema(description = "생성일", example = "2024-01-11.12:12:12")
    @JsonFormat(pattern = "yyyy-MM-dd.HH:mm:ss")
    private LocalDateTime createdAt;
    @Schema(description = "통신사", example = "벨류컴")
    private String operatorName;
    @Schema(description = "요금제", example = "문자 전용 요금제")
    private String productName;
    @Schema(description = "통신망", example = "통신망")
    private String network;
    @Schema(description = "PIN번호", example = "PinNum-1")
    private String pinNum;
    @Schema(description = "관리번호", example = "ManageNum-1")
    private String managementNum;
    @Schema(description = "입고처", example = "아이즈")
    private String store;
    @Schema(description = "출고처", example = "벨류컴")
    private String release;
    @Schema(description = "입력자", example = "김진")
    private String writer;
    @Schema(description = "사용자", example = "김진")
    private String user;
    @Schema(description = "사용날짜", example = "2024-01-11.12:12:12")
    @JsonFormat(pattern = "yyyy-MM-dd.HH:mm:ss")
    private LocalDateTime usedAt;
    @Schema(description = "판매전용여부", example = "true")
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
