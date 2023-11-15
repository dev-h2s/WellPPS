package com.wellnetworks.wellcore.java.dto.member;


import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class WellEmployeeJoinDTO {
    private String employeeIdx; // user-idx
    private Long employeeId;
//    private Integer Long;
    private String employeeIdentification; // user-id
    private String employeeName; // 사원명
    private String employeeManagerGroupKey; // 그룹 에서 받은 fk
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
    private String employeeUserPwd; // user-패스워드
    private String tmpPwd; // user-임시패스워드
    private LocalDateTime employeeRegisterDate;

    private List<String> fileKinds = new ArrayList<>();
    private List<MultipartFile> files;





    @QueryProjection

    public WellEmployeeJoinDTO(String employeeIdx, Long employeeId, String employeeIdentification, String employeeName, String belong, String department, String position,
                               String employmentState, String jobType, LocalDateTime entryDatetime, LocalDateTime employmentQuitDatetime, String employmentQuitType,
                               Float remainingLeaveDays, String residentRegistrationNumber, String telPrivate, Boolean isPhoneVerified, String phoneVerificationCode,
                               Integer phoneVerificationAttempts, LocalDateTime phoneVerificationExpiration, LocalDateTime phoneVerificationSentTime, String telWork,
                               String email, String bankName, String bankAccount, String bankHolder, String homeAddress1, String homeAddress2, Boolean externalAccessCert,
                               String memo, String employeeUserPwd, String tmpPwd, LocalDateTime employeeRegisterDate, String employeeManagerGroupKey, List<String> fileKinds) {
        this.employeeIdx = employeeIdx;
        this.employeeId = employeeId;
        this.employeeIdentification = employeeIdentification;
        this.employeeName = employeeName;
        this.belong = belong;
        this.department = department;
        this.position = position;
        this.employmentState = employmentState;
        this.jobType = jobType;
        this.entryDatetime = entryDatetime;
        this.employmentQuitDatetime = employmentQuitDatetime;
        this.employmentQuitType = employmentQuitType;
        this.remainingLeaveDays = remainingLeaveDays;
        this.residentRegistrationNumber = residentRegistrationNumber;
        this.telPrivate = telPrivate;
        this.isPhoneVerified = isPhoneVerified;
        this.phoneVerificationCode = phoneVerificationCode;
        this.phoneVerificationAttempts = phoneVerificationAttempts;
        this.phoneVerificationExpiration = phoneVerificationExpiration;
        this.phoneVerificationSentTime = phoneVerificationSentTime;
        this.telWork = telWork;
        this.email = email;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.bankHolder = bankHolder;
        this.homeAddress1 = homeAddress1;
        this.homeAddress2 = homeAddress2;
        this.externalAccessCert = externalAccessCert;
        this.memo = memo;
        this.tmpPwd = tmpPwd;
        this.employeeUserPwd = employeeUserPwd;
        this.employeeRegisterDate = employeeRegisterDate;
        this.employeeManagerGroupKey = employeeManagerGroupKey;
        this.fileKinds = fileKinds;
    }
}
