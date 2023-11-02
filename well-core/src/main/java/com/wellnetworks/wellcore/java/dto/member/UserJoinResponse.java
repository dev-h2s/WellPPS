package com.wellnetworks.wellcore.java.dto.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;

public class UserJoinResponse {
    private String employeeIdentification;
    private String employeeName;

    public UserJoinResponse(WellEmployeeUserEntity user, WellEmployeeEntity employee) {
        this.employeeIdentification = user.getEmployeeIdentification();
        this.employeeName = employee.getEmployeeName();
    }
}
