package com.wellnetworks.wellcore.java.domain.employee;
//직원 그룹 테이블

import jakarta.persistence.*;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "employee_manager_group_tb", indexes = {@Index(name = "em_gkey", columnList = "employeeManagerGroupKey",unique = true)})
public class WellEmployeeManagerGroupEntity {

    @Id
    @Column(name = "em_gkey")//직원 관리자그룹
    private String employeeManagerGroupKey;

    @OneToMany(mappedBy = "emGroup") // 양방향 일때 직원 리스트 가져오기
    private List<WellEmployeeEntity> employee = new ArrayList<>();

    @Column(name = "em_g_name")//직원 그룹명
    private String employeeManagerName;

    @Column(name = "em_g_permissions") //직원 권한
    private String employeeManagerPermissions;

    @Column (name = "em_g_description") //직원 권한에 대한 설명
    private String employeeManagerDescription;

    @Column(name = "em_g_moddt") //권한채택일
    private LocalDateTime employeeManagerModifyDate;

    @Column(name = "em_g_regdt") //생성 날짜와 시간
    private LocalDateTime employeeManagerRegisterDate;


}
