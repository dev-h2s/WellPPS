package com.wellnetworks.wellcore.java.dto.Diposit;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellDipositCreateDTO {
    private String dipositIdx;
    //거래처idx
    private String partnerIdx;
    //거래처명
    private String partnerName;
    //예치금 조정
    private boolean dipositAdjustment;
    //조정사유
    private String dipositStatus;
    //입금액
    private Integer dipositAmount;
    //문자전송여부
    private boolean messageFlag;
    //메모
    private String memo;
}
