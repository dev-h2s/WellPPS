package com.wellnetworks.wellcore.java.dto.PIN;

import com.wellnetworks.wellcore.java.domain.pin.WellPinEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellPinCreateDTO {
    private String store; //입고처(거래처)
    private String release; //출고처(거래처)
    private String operatorName; //통신사 (코드)
    private String productName; //요금제 (코드)
    private String pinNum; //PIN번호
    private String managementNum; //관리번호
    private Boolean isUseFlag; //사용유무
    private String user; //사용자
    private Boolean isSaleFlag; //판매전용여부

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
