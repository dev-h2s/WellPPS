package com.wellnetworks.wellcore.java.DTO.member;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Getter
@Setter
public class WellEmployeeDTO {
    private String employeeIdx;
    private String tableID;
    private String employeeId;
    private String currentEmployment;
    private String name;
    private String eMail;
    private String telPrivate;
    private String telWork;
    private String registrationNumber;
    private String department;
    private String jobPosition;
    private byte level;
    private String homeAddress1;
    private String homeAddress2;
    private String bankName;
    private String bankAccount;
    private String bankHolder;
    private String employmentState;
    private String jobType;
    private boolean certificationtel;
    private boolean certificationEmail;
    private LocalDateTime entryDatetime;
    private LocalDateTime employmentQuitDatetime;
    private String employmentQuitType;
    private boolean dbAccessPower;
    private String memo;
    private LocalDateTime employeeModifyDate;
    private LocalDateTime employeeRegisterDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WellEmployeeDTO that = (WellEmployeeDTO) o;
        return Objects.equals(employeeIdx, that.employeeIdx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeIdx);
    }
}

