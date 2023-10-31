package com.wellnetworks.wellcore.java.dto.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class WellEmployeeManagerGroupDTO {

    private String employeeManagerGroupKey; // 직원 관리자그룹 키


    private String department;

    private String employeeManagerPermissions; // 직원 권한

    private String employeeManagerDescription; // 직원 권한에 대한 설명

    private LocalDateTime employeeManagerModifyDate; // 권한 채택일

    private LocalDateTime employeeManagerRegisterDate; // 생성 날짜와 시간


    public WellEmployeeManagerGroupDTO (WellEmployeeManagerGroupDTO groupDTO) {
                this.employeeManagerGroupKey = groupDTO.getEmployeeManagerGroupKey();
                this.department = groupDTO.getDepartment();
                this.employeeManagerPermissions = groupDTO.getEmployeeManagerPermissions();
                this.employeeManagerDescription = groupDTO.getEmployeeManagerDescription();
                this.employeeManagerModifyDate = groupDTO.getEmployeeManagerModifyDate();
                this.employeeManagerRegisterDate = groupDTO.getEmployeeManagerRegisterDate();
        ;

}

    public WellEmployeeManagerGroupDTO(WellEmployeeManagerGroupEntity wellEmployeeManagerGroupEntity) {
    }
}
