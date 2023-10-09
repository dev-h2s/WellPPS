package com.wellnetworks.wellcore.java.domain.permission.department;
// 직원과 부서 dropdown 테이블의 중간 테이블
import com.wellnetworks.wellcore.java.domain.charge.WellChargeHistoryEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductSearchEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "employee_department_permission_tb", indexes = {@Index(name = "em_dep_id", columnList = "emDepId",unique = true)})
public class WellEmployeeDepartmentPermissionEntity {

    //    중간테이블의 pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "em_dep_id")
    private Long emDepId;

    //    dropdown의 fk
    @ManyToOne
    @JoinColumn(name = "dep_drop_id")
    private WellDepartmentDropDownEntity departmentDropDown;

    //    직원 테이블에서 받는 fk
    @ManyToOne
    @JoinColumn(name = "em_id")
    private WellEmployeeEntity employee;


}
