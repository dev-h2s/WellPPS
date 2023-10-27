package com.wellnetworks.wellcore.java.dto.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WellEmployeeInfoDetailDTO {
    private String employeeIdx;
    private String employeeIdentification;
    //    private String tableID;
//    private Integer employeeId;
    private String name;
    private String belong;
    private String department;
    private String position;
    private String employmentState;
    private String jobType;
    private LocalDateTime entryDatetime;
    private LocalDateTime employmentQuitDatetime;
    private String employmentQuitType;
    private Float remainingLeaveDays;
    private String residentRegistrationNumber;
    private String telPrivate;
    private String telWork;
    private String email;
    private String bankName;
    private String bankAccount;
    private String bankHolder;
    private String homeAddress1;
    private String homeAddress2;
    private Boolean externalAccessCert;
    private String fileKind;
    private String memo;
    //권한정보
//    private String registrationNumber;
//    private String level;
//    private Boolean certificationtel;
//    private Boolean certificationEmail;

//    private Boolean dbAccessPower;

//    private LocalDateTime employeeModifyDate;
//    private LocalDateTime employeeRegisterDate;

    public WellEmployeeInfoDetailDTO(WellEmployeeEntity employeeEntity) {}

    public WellEmployeeInfoDetailDTO(WellEmployeeEntity entity, WellEmployeeUserEntity user, WellEmployeeManagerGroupEntity department, List<WellFileStorageEntity> filestorages) {
        this.employeeIdx = entity.getEmployeeIdx();
        this.employeeIdentification = user.getEmployeeIdentification();
//        this.tableID = entity.getTableID();
//        this.employeeId = entity.getEmployeeId();
        this.belong = entity.getBelong();
        this.name = entity.getName();
        this.department = department.getDepartment();
        this.email = entity.getEmail();
        this.telPrivate = entity.getTelPrivate();
        this.telWork = entity.getTelWork();
//        this.registrationNumber = entity.getRegistrationNumber();
        this.position = entity.getPosition();
//        this.level = entity.getLevel();
        this.homeAddress1 = entity.getHomeAddress1();
        this.homeAddress2 = entity.getHomeAddress2();
        this.bankName = entity.getBankName();
        this.bankAccount = entity.getBankAccount();
        this.bankHolder = entity.getBankHolder();
        this.employmentState = entity.getEmploymentState();
        this.jobType = entity.getJobType();
//        this.certificationtel = entity.getCertificationtel();
//        this.certificationEmail = entity.getCertificationEmail();
        this.externalAccessCert = entity.getExternalAccessCert();
        this.entryDatetime = entity.getEntryDatetime();
        this.employmentQuitDatetime = entity.getEmploymentQuitDatetime();
        this.employmentQuitType = entity.getEmploymentQuitType();
        this.remainingLeaveDays = entity.getRemainingLeaveDays();
        this.residentRegistrationNumber = entity.getResidentRegistrationNumber();
//        this.dbAccessPower = entity.getDbAccessPower();
        this.memo = entity.getMemo();
//        this.employeeModifyDate = entity.getEmployeeModifyDate();
//        this.employeeRegisterDate = entity.getEmployeeRegisterDate();

        for (WellFileStorageEntity fileStorage : filestorages) {
            if (fileStorage != null) {
                this.fileKind = fileStorage.getFileKind();
            }
        }
    }
}

