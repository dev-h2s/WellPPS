package com.wellnetworks.wellcore.java.dto.member;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class WellEmployeeUserDTO {

    private String employeeIdx;
    private String emGroup;
    private String employeeIdentification;
    private String employeeUserPwd;
    private String permissions;
    private String tmpPwd;
    private LocalDateTime tmpPwdExpiration;
    private int tmpPwdCount;
    private LocalDateTime tmpPwdDate;
    private LocalDateTime employeeUserModifyDate;
    private LocalDateTime getemployeeUserRegisterDate;
    private String groupKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WellEmployeeUserDTO that = (WellEmployeeUserDTO) o;
        return Objects.equals(employeeIdx, that.employeeIdx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeIdx);
    }
}
