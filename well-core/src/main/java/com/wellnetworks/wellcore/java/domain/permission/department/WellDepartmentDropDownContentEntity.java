package com.wellnetworks.wellcore.java.domain.permission.department;
// 부서 dropdown 컨텐츠
import com.wellnetworks.wellcore.java.domain.charge.WellChargeHistoryEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductSearchEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "department_dropdown_content_tb", indexes = {@Index(name = "dep_drop_content_id", columnList = "depDropContentId",unique = true)})
public class WellDepartmentDropDownContentEntity {


    //    부서 drop down들의 content를 지정하기 위한 pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dep_drop_content_id")
    private Long depDropContentId;

    //    부서 dropdown의 FK로 받는 id
    @ManyToOne
    @JoinColumn(name = "dep_drop_id")
    private WellDepartmentDropDownEntity departmentDropdown;

    //    dropdowncontent의 이름
    @Column(name = "em_id")
    private Long employeeId;


}
