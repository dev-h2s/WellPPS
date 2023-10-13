package com.wellnetworks.wellcore.java.dto.WellPartnerDTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WellPartnerUpdateDTO {
    private String partnerCode;
    private String partnerName;
    private String partnerType;
    private String partnerGroup;
    private String discountCategory;
    private String salesManager;
//    private String apiKey; //api의 내부 api를 가져옴 거래처.내부apikey(내부apikey_in)
    private String preApprovalNumber;
    private LocalDateTime subscriptionDate;
    private String transactionStatus;
    //private BigDecimal depositBalance;        예치금잔액은 가상계좌의 예치금에 존재함 거래처.가상계좌.에치금으로 가야됨
//    private String virtualAccount; 이건 신한 110...이런식으로 가상계좌 3분할되어있는 정보 연결예정
    private String specialPolicyOpening;
    private String specialPolicyCharge;
    private String partnerUpperId;
//    private String partnerName; 하부점은 상부점의 하부점을 의미하는 것이여서 상부점.하부점으로 가야됨
    private String ceoName;
    private String ceoTelephone;
    private String partnerTelephone;
    private String emailAddress;
    private String commissionDepositAccount;
    private String commissionBankName;
    private String commissionBankHolder;
    private String registrationNumber;
    private String registrationAddress;
    private String registrationDetailAddress;
    private String locationAddress;
    private String locationDetailAddress;
//    private String files; 파일들은 첨부파일에 종류별로 다름 거래처.거래처파일.첨부파일.파일종류(사업자등록증)
//    private String files; 거래처.거래처파일.첨부파일.파일종류(계약서)
//    private String files; 거래처.거래처파일.첨부파일.파일종류(대표자신분증)
//    private String files; 거래처.거래처파일.첨부파일.파일종류(매장사진)
//    private String files; 거래처.거래처파일.첨부파일.파일종류(대표자명함)
    private String partnerMemo;
    private LocalDateTime salesTeamVisitDate;
    private String salesTeamVisitMemo;
//    private String commissionPolicy; 아직 안정해짐
}

