package com.wellnetworks.wellcore.java.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class WellEmployeeManagerGroupDTO {


    private String employeeManagerGroupKey; // 직원 관리자그룹 키

    private List<String> employeeIds; // 직원 ID 리스트 (Entity 내에 직접적인 엔터티 관계가 아닌 ID 목록으로 표현)

    private String employeeManagerName; // 직원 그룹명

    private String employeeManagerPermissions; // 직원 권한

    private String employeeManagerDescription; // 직원 권한에 대한 설명

    private LocalDateTime employeeManagerModifyDate; // 권한 채택일

    private LocalDateTime employeeManagerRegisterDate; // 생성 날짜와 시간

}
