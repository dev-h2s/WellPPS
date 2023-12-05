package com.wellnetworks.wellcore.java.dto.member;
// 사원 관리 리스트에 뿌리기위한 DTO
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WellEmployeeInfoDTO {
    private String employeeIdx; //pk
    private Long employeeId; // 번호 id
    private String belong; // 소속
    private String employeeName; // 이름
    private String department; // 부서
    private String position; // 직급
    private LocalDateTime employeeUserRegisterDate; // 등록일
    private String telPrivate; // 전화번호
    private String email; // 이매일
    private String employmentState; // 재직상태

    //    생성자
    public WellEmployeeInfoDTO(WellEmployeeEntity employeeEntity, WellEmployeeManagerGroupEntity managerGroup, WellEmployeeUserEntity user) {
        this.employeeIdx = employeeEntity.getEmployeeIdx();
        this.employeeId = employeeEntity.getEmployeeId();
        this.belong = employeeEntity.getBelong();
        this.employeeName = employeeEntity.getEmployeeName();
        this.department = (managerGroup != null) ? managerGroup.getDepartment() : null;
        this.position = employeeEntity.getPosition();
        this.employeeUserRegisterDate = user.getEmployeeUserRegisterDate();
        this.telPrivate = employeeEntity.getTelPrivate();
        this.email = employeeEntity.getEmail();
        this.employmentState = employeeEntity.getEmploymentState();
    }



}

