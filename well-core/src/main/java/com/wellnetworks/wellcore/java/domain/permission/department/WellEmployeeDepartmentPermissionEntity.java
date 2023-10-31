package com.wellnetworks.wellcore.java.domain.permission.department;
// 직원과 부서 dropdown 테이블의 중간 테이블
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "employee_department_permission_tb")
public class WellEmployeeDepartmentPermissionEntity {

    //    중간테이블의 pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "em_dep_id")
    private Long emDepId;

    //    dropdown의 fk 양방향
    @JsonIgnore //순환참조 문제 방지
    @ManyToOne
    @JoinColumn(name = "dep_drop_id", insertable = false, updatable = false)
    private WellDepartmentDropDownEntity departmentDropDown;

    //    직원 테이블에서 받는 fk 단방향
    @ManyToOne
    @JoinColumn(name = "em_id", insertable = false, updatable = false)
    private WellEmployeeEntity employee;


}
