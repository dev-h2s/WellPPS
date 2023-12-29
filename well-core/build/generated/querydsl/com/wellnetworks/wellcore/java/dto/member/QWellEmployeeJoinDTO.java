package com.wellnetworks.wellcore.java.dto.member;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.ConstructorExpression;
import javax.annotation.processing.Generated;

/**
 * com.wellnetworks.wellcore.java.dto.member.QWellEmployeeJoinDTO is a Querydsl Projection type for WellEmployeeJoinDTO
 */
@Generated("com.querydsl.codegen.DefaultProjectionSerializer")
public class QWellEmployeeJoinDTO extends ConstructorExpression<WellEmployeeJoinDTO> {

    private static final long serialVersionUID = 1133388141L;

    public QWellEmployeeJoinDTO(com.querydsl.core.types.Expression<String> employeeIdx, com.querydsl.core.types.Expression<Long> employeeId, com.querydsl.core.types.Expression<String> employeeIdentification, com.querydsl.core.types.Expression<String> employeeName, com.querydsl.core.types.Expression<String> belong, com.querydsl.core.types.Expression<String> department, com.querydsl.core.types.Expression<String> position, com.querydsl.core.types.Expression<String> employmentState, com.querydsl.core.types.Expression<String> jobType, com.querydsl.core.types.Expression<java.time.LocalDate> entryDate, com.querydsl.core.types.Expression<java.time.LocalDate> retireDate, com.querydsl.core.types.Expression<String> employmentQuitType, com.querydsl.core.types.Expression<Float> remainingLeaveDays, com.querydsl.core.types.Expression<String> residentRegistrationNumber, com.querydsl.core.types.Expression<String> telPrivate, com.querydsl.core.types.Expression<Boolean> isPhoneVerified, com.querydsl.core.types.Expression<String> phoneVerificationCode, com.querydsl.core.types.Expression<Integer> phoneVerificationAttempts, com.querydsl.core.types.Expression<java.time.LocalDateTime> phoneVerificationExpiration, com.querydsl.core.types.Expression<java.time.LocalDateTime> phoneVerificationSentTime, com.querydsl.core.types.Expression<String> telWork, com.querydsl.core.types.Expression<String> email, com.querydsl.core.types.Expression<String> bankName, com.querydsl.core.types.Expression<String> bankAccount, com.querydsl.core.types.Expression<String> bankHolder, com.querydsl.core.types.Expression<String> homeAddress1, com.querydsl.core.types.Expression<String> homeAddress2, com.querydsl.core.types.Expression<Boolean> externalAccessCert, com.querydsl.core.types.Expression<String> memo, com.querydsl.core.types.Expression<String> employeeUserPwd, com.querydsl.core.types.Expression<String> tmpPwd, com.querydsl.core.types.Expression<java.time.LocalDateTime> employeeRegisterDate, com.querydsl.core.types.Expression<String> employeeManagerGroupKey, com.querydsl.core.types.Expression<? extends java.util.List<String>> fileKinds) {
        super(WellEmployeeJoinDTO.class, new Class<?>[]{String.class, long.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, java.time.LocalDate.class, java.time.LocalDate.class, String.class, float.class, String.class, String.class, boolean.class, String.class, int.class, java.time.LocalDateTime.class, java.time.LocalDateTime.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, boolean.class, String.class, String.class, String.class, java.time.LocalDateTime.class, String.class, java.util.List.class}, employeeIdx, employeeId, employeeIdentification, employeeName, belong, department, position, employmentState, jobType, entryDate, retireDate, employmentQuitType, remainingLeaveDays, residentRegistrationNumber, telPrivate, isPhoneVerified, phoneVerificationCode, phoneVerificationAttempts, phoneVerificationExpiration, phoneVerificationSentTime, telWork, email, bankName, bankAccount, bankHolder, homeAddress1, homeAddress2, externalAccessCert, memo, employeeUserPwd, tmpPwd, employeeRegisterDate, employeeManagerGroupKey, fileKinds);
    }

}

