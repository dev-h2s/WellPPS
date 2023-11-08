package com.wellnetworks.wellcore.java.dto.member;
// 사원 관리 리스트에 뿌리기위한 DTO
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
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
    private LocalDateTime employeeRegisterDate; // 등록일
    private String telPrivate; // 전화번호
    private String email; // 이매일
    private String employmentState; // 재직상태

    //    생성자
    public WellEmployeeInfoDTO(WellEmployeeEntity Entity, WellEmployeeManagerGroupEntity managerGroupEntity) {
        this.employeeIdx = Entity.getEmployeeIdx();
        this.employeeId = Entity.getEmployeeId();
        this.belong = Entity.getBelong();
        this.employeeName = Entity.getEmployeeName();
        this.department = (managerGroupEntity != null) ? managerGroupEntity.getDepartment() : null;
        this.position = Entity.getPosition();
        this.employeeRegisterDate = Entity.getEmployeeRegisterDate();
        this.telPrivate = Entity.getTelPrivate();
        this.email = Entity.getEmail();
        this.employmentState = Entity.getEmploymentState();
    }


}

