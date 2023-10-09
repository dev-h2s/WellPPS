package com.wellnetworks.wellcore.java.domain.permission.department;

import com.wellnetworks.wellcore.java.domain.charge.WellChargeHistoryEntity;
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
@Table(name = "department_dropdown_tb", indexes = {@Index(name = "dep_drop_id", columnList = "depDropId",unique = true)})
public class WellDepartmentDropDownEntity {

    //    부서 권한의 dropdown의 각 권한 여부를 지정하기 위한 pk
    @Id
    @Column(name = "dep_drop_id")
    private Long depDropId;

    //    부서 dropdown의 이름
    @Column(name = "dep_drop_menu_name")
    private String menuName;

}
