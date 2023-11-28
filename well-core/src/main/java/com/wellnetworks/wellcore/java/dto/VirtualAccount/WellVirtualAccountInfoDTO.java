package com.wellnetworks.wellcore.java.dto.VirtualAccount;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class WellVirtualAccountInfoDTO {
    private LocalDate registerDate;
    private String virtualBankName;
    private String virtualAccount;
    private String issuance;
    private String partnerIdx;
    private String partnerName;
    private LocalDateTime issueDate;
    private String writer;

    private Long issuedCount; // 발급
    private Long notIssuedCount; // 미발급
    private Long collectCount; // 회수


    //개별, 리스트
    // 검색
}
