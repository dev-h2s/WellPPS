package com.wellnetworks.wellcore.java.dto.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellEmployeeFileCreateDTO;
import com.wellnetworks.wellcore.java.dto.FIle.WellFIleCreateDTO;
import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class WellEmployeeInfoDetailDTO {
    private String employeeIdx;
    private String employeeIdentification;
    //    private String tableID;
//    private Integer employeeId;
    private String employeeName;
    private String belong;
    private String department;
    private String position;
    private String employmentState;
    private String jobType;
    private LocalDate entryDate;
    private LocalDate retireDate;
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
    private String memo;


//    private List<String> fileKinds = new ArrayList<>();
//    private List<Long> fileIds = new ArrayList<>();
//    private String fileKind;
//    private Long fileId;
//    private List<WellEmployeeFileCreateDTO> files = new ArrayList<>();
    private List<WellFileDetailDTO> fileDetails = new ArrayList<>();

    //권한정보
//    private String registrationNumber;
//    private String level;
//    private Boolean certificationtel;
//    private Boolean certificationEmail;

//    private Boolean dbAccessPower;

//    private LocalDateTime employeeModifyDate;
//    private LocalDateTime employeeRegisterDate;

    public WellEmployeeInfoDetailDTO(WellEmployeeEntity employeeEntity) {
    }

    public WellEmployeeInfoDetailDTO(WellEmployeeEntity entity, WellEmployeeUserEntity user, WellEmployeeManagerGroupEntity department, List<WellEmployeeFileStorageEntity> fileStorages) {
        this.employeeIdx = entity.getEmployeeIdx();
        this.employeeIdentification = user.getEmployeeIdentification();
//        this.tableID = entity.getTableID();
//        this.employeeId = entity.getEmployeeId();
        this.belong = entity.getBelong();
        this.employeeName = entity.getEmployeeName();
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
        this.entryDate = entity.getEntryDate();
        this.retireDate = entity.getRetireDate();
        this.employmentQuitType = entity.getEmploymentQuitType();
        this.remainingLeaveDays = entity.getRemainingLeaveDays();
        this.residentRegistrationNumber = entity.getResidentRegistrationNumber();
//        this.dbAccessPower = entity.getDbAccessPower();
        this.memo = entity.getMemo();

        for (WellEmployeeFileStorageEntity fileStorage : fileStorages) {
            if (fileStorage != null && fileStorage.getFile() != null) {
                WellFileDetailDTO fileDetail = new WellFileDetailDTO();
                fileDetail.setFileId(fileStorage.getFile().getId());
                fileDetail.setOriginFileName(fileStorage.getFile().getOriginFileName());
                fileDetail.setFileKind(fileStorage.getFile().getFileKind());
                this.fileDetails.add(fileDetail);
            }
        }

        List<String> requiredFileKinds = Arrays.asList("첨부파일1", "첨부파일2", "첨부파일3", "첨부파일4", "첨부파일5");

        // 파일 종류별로 첫 번째 항목을 찾고, 없으면 빈 정보를 추가
        for (String fileKind : requiredFileKinds) {
            WellFileDetailDTO fileDetail = fileStorages.stream()
                    .filter(fileStorage -> fileStorage != null && fileStorage.getFile() != null && fileStorage.getFile().getFileKind().equals(fileKind))
                    .map(fileStorage -> new WellFileDetailDTO(fileStorage.getFile().getId(), fileStorage.getFile().getOriginFileName(), fileStorage.getFile().getFileKind()))
                    .findFirst()
                    .orElse(new WellFileDetailDTO(null, null, fileKind));

            this.fileDetails.add(fileDetail);
        }

        // 중복 제거
        this.fileDetails = this.fileDetails.stream().distinct().collect(Collectors.toList());
    }

}

