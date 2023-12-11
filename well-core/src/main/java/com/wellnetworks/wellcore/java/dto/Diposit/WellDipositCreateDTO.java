package com.wellnetworks.wellcore.java.dto.Diposit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellDipositCreateDTO {
    private String dipositIdx;
    //거래처idx
    @NotBlank(message = "거래처명은 필수 입력 항목입니다.")
    private String partnerIdx;
    //거래처명
    private String partnerName;
    //예치금 조정
    private boolean dipositAdjustment = true;
    //조정사유
    @NotBlank(message = "조정사유는 필수 입력 항목입니다.")
    private String dipositStatus;
    //입금액
    @NotNull
    private Integer dipositAmount;
    //문자전송여부
    private boolean messageFlag = false;
    //메모
    private String memo;
}
