package com.wellnetworks.wellcore.java.domain.employee;
//직원 그룹 테이블

import com.wellnetworks.wellcore.java.DTO.WellEmployeeManagerGroupDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Getter
@Table(name = "employee_manager_group_tb")
public class WellEmployeeManagerGroupEntity{

    @Id
    @Column(name = "em_gkey")//직원 관리자그룹
    private String employeeManagerGroupKey;

    @OneToMany(mappedBy = "emGroup") // 양방향 일때 직원 리스트 가져오기
    private List<WellEmployeeUserEntity> employee = new ArrayList<>();

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


//    public WellEmployeeManagerGroupDTO toDto() {
//        // WellEmployeeEntity에서 ID만 추출
////        List<String> employeeIds = employee.stream()
////                .map(WellEmployeeUserEntity::getId) // 가정: WellEmployeeEntity에 getId() 메서드가 있다고 가정합니다.
////                .collect(Collectors.toList());
//
//        return new WellEmployeeManagerGroupDTO(
//                this.employeeManagerGroupKey,
//                employeeIds,  // 여기에 추가
//                this.employeeManagerName,
//                this.employeeManagerPermissions,
//                this.employeeManagerDescription,
//                this.employeeManagerModifyDate,
//                this.employeeManagerRegisterDate
//        );
//    }
}
