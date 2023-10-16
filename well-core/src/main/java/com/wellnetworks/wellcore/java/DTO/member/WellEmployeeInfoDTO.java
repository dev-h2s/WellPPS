package com.wellnetworks.wellcore.java.dto.member;
// 사원 관리 리스트에 뿌리기위한 DTO
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class WellEmployeeInfoDTO {
    private String employeeIdx; //pk
    private Integer employeeId; // 번호 id
    private String belong; // 소속
    private String name; // 이름
    private String department; // 부서
    private String position; // 직책
    private LocalDateTime employeeRegisterDate; // 등록일
    private String telPrivate; // 전화번호
    private String eMail; // 이매일
    private String employmentState; // 재직상태

    public WellEmployeeInfoDTO() {}
//    생성자
public WellEmployeeInfoDTO(WellEmployeeEntity entity,  List<WellFileStorageEntity> fileStorages) {
    this.employeeIdx = entity.getEmployeeIdx();
    this.employeeId = entity.getEmployeeId();
    this.belong = entity.getBelong();
    this.name = entity.getName();
    this.department = entity.getDepartment();
    this.position = entity.getPosition();
    this.employeeRegisterDate = entity.getEmployeeRegisterDate();
    this.telPrivate = entity.getTelPrivate();
    this.eMail = entity.getEMail();
    this.employmentState = entity.getEmploymentState();
}
}
