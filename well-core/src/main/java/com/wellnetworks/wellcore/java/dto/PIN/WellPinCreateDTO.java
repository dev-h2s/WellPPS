package com.wellnetworks.wellcore.java.dto.PIN;

import com.wellnetworks.wellcore.java.domain.pin.WellPinEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPinCreateDTO {
    @Schema(description = "입고처", example = "한패스")
    private String store;
    @Schema(description = "출고처", example = "한패스")
    private String release;
    @Schema(description = "통신사", example = "벨류컴")
    private String operatorName;
    @Schema(description = "요금제", example = "문자전용 요금제")
    private String productName;
    @Schema(description = "PIN 번호", example = "PinNum-1")
    private String pinNum;
    @Schema(description = "관리번호", example = "ManageNum-1")
    private String managementNum;
    @Schema(description = "사용유무", example = "true")
    private Boolean isUseFlag;
    @Schema(description = "사용자", example = "김진")
    private String user;
    @Schema(description = "판매전용여부", example = "true")
    private Boolean isSaleFlag;

    public WellPinEntity toEntity() {
        return WellPinEntity.builder()
                .operatorName(this.operatorName)
                .productName(this.productName)
                .pinNum(this.pinNum)
                .managementNum(this.managementNum)
                .store(this.store)
                .release(this.release)
                .isUseFlag(this.isUseFlag)
                .userName(this.user)
                .isSaleFlag(this.isSaleFlag)
                .build();
    }
}
