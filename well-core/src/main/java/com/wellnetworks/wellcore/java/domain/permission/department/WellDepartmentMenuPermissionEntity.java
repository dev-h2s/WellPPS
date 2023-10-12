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
@Table(name = "department_menu_permission_tb")
public class WellDepartmentMenuPermissionEntity {

//    부서 권한의 dropdown content의 각 권한 여부를 지정하기 위한 pk
    @Id
    @Column(name = "dep_menu_id") //거래처_id
    private Long depMenuId;
    //    부서 drop의 하위 컨텐트의 FK로 받는 id
    @ManyToOne
    @JoinColumn(name = "dep_drop_content_id", insertable = false, updatable = false)
    private WellDepartmentDropDownContentEntity dropdownContent;

    //    부서 입력 권한의 허용 여부
    @Column(name = "input_flag")
    private Boolean inputFlag;

    //    부서 조회 권한의 허용 여부
    @Column(name = "view_flag")
    private Boolean viewFlag;

    //    부서 수정 권한의 허용 여부
    @Column(name = "edit_flag")
    private Boolean editFlag;

    //    부서 삭제 권한의 허용 여부
    @Column(name = "delete_flag")
    private Boolean deleteFlag;

    //    부서 검색 권한의 허용 여부
    @Column(name = "search_flag")
    private Boolean searchFlag;

    //    부서 메뉴 권한의 허용 여부
    @Column(name = "excel_flag")
    private Boolean excelFlag;









}
