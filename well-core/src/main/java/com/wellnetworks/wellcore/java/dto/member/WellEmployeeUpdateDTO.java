package com.wellnetworks.wellcore.java.dto.member;
// 사원 관리 리스트에 뿌리기위한 DTO
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
public class WellEmployeeUpdateDTO {
    private String employeeIdx; // 변동 x
    private Integer employeeId;
    private String name;
    private String belong; // 소속 회사
    private String department; //부서
    private String position; // 직책
    private String employmentState; // 재직상태
    private String jobType; // 고용형태
    private LocalDateTime entryDatetime; // 입사일자
    private LocalDateTime employmentQuitDatetime; // 퇴사일자
    private String employmentQuitType; // 퇴사사유
    private Float remainingLeaveDays; // 잔여연차
    private String residentRegistrationNumber;//주민등록번호
    private Boolean certificationtel; // 전화 인증여부
    private String telWork; // 업무 전화번호
    private String eMail; // 이메일
    private String bankName; // 급여 입금계좌명
    private String bankAccount; // 급여 입금계좌번호
    private String bankHolder; // 예금주
    private String homeAddress1; // 자택 주소1
    private String homeAddress2; // 자택 주소2
    private Boolean externalAccessCert; // 외부접속 여부
    private String memo; // 메모
    private List<WellFileStorageEntity> files;
    //권한정보 들어와야함

//    private String telPrivate;
//    private String registrationNumber;
//    private boolean certificationEmail;
//    private boolean dbAccessPower;

//    private LocalDateTime employeeModifyDate;
//    private LocalDateTime employeeRegisterDate;

    //기본 생성자
    public WellEmployeeUpdateDTO() {
    }

//    생성자

public WellEmployeeUpdateDTO(WellEmployeeEntity entity, List<WellFileStorageEntity> files, WellEmployeeManagerGroupEntity group) {
    this.employeeIdx = entity.getEmployeeIdx();
    this.employeeId = entity.getEmployeeId();
    this.name = entity.getName();
    this.belong = entity.getBelong();
    this.department = group.getDepartment();
    this.position = entity.getPosition();
    this.employmentState = entity.getEmploymentState();
    this.jobType = entity.getJobType();
    this.entryDatetime = entity.getEntryDatetime();
    this.employmentQuitDatetime = entity.getEmploymentQuitDatetime();
    this.employmentQuitType = entity.getEmploymentQuitType();
    this.remainingLeaveDays = entity.getRemainingLeaveDays();
    this.residentRegistrationNumber = entity.getResidentRegistrationNumber();
    this.certificationtel = entity.getCertificationtel();
    this.telWork = entity.getTelWork();
    this.eMail = entity.getEmail();
    this.bankName = entity.getBankName();
    this.bankAccount = entity.getBankAccount();
    this.bankHolder = entity.getBankHolder();
    this.homeAddress1 = entity.getHomeAddress1();
    this.homeAddress2 = entity.getHomeAddress2();
    this.externalAccessCert = entity.getExternalAccessCert();
    this.memo = entity.getMemo();
    this.files = files;
}


}
