package com.wellnetworks.wellcore.java.dto.member;


import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WellEmployeeJoinDTO {
    private String employeeIdx; // user-idx
    private Integer employeeId;
    private String employeeIdentification; // user-id
    private String belong; // 소속회사
    private String department; // group -부서
    private String position; // 직책
    private String employmentState; // 재직 상태
    private String jobType; // 고용형태
    private LocalDateTime entryDatetime; // 입사일
    private LocalDateTime employmentQuitDatetime; //퇴사일
    private String employmentQuitType; // 퇴사사유
    private Float remainingLeaveDays; //잔여연차
    private String residentRegistrationNumber; // 주민번호
    private String telPrivate; // 휴대폰번호
    private Boolean isPhoneVerified; // user-휴대폰 인증 여부
    private String phoneVerificationCode;// user-휴대폰 인증 코드
    private Integer phoneVerificationAttempts;// user-휴대폰 인증 시도 횟수
    private LocalDateTime phoneVerificationExpiration;// user-휴대폰 인증 만료 시간
    private LocalDateTime phoneVerificationSentTime;// user-휴대폰 인증 코드 전송 시간
    private String telWork; // 업무 전화번호
    private String email; // 이메일 주소
    private String bankName; //은행 이름
    private String bankAccount; //계좌번호
    private String bankHolder; // 계좌 소유자
    private String homeAddress1; //도로명 주소
    private String homeAddress2; //상세 주소
    private Boolean externalAccessCert; // 인트라넷 외 접속 가능 여부
    private String memo; // 메모
    //이후 파일,권한등 고려..





}
