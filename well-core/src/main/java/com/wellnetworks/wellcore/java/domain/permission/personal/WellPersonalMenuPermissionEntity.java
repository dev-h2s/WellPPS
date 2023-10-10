package com.wellnetworks.wellcore.java.domain.permission.personal;
// 개인 menu 권한

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "personal_menu_permission_tb", indexes = {@Index(name = "per_menu_id", columnList = "perMenuId",unique = true)})
public class WellPersonalMenuPermissionEntity {

    //    개인 권한의 dropdown content의 각 권한 여부를 지정하기위한 pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_menu_id")
    private Long perMenuId;

    //    개인 dropcontent의 FK로 받는 id 단방향
    @ManyToOne
    @JoinColumn(name = "per_drop_content_id")
    private WellPersonalDropContentEntity dropdownContent;

    //    개인 입력 권한의 허용 여부
    @Column(name = "input_flag")
    private Boolean inputFlag;

    //    개인 조회 권한의 허용 여부
    @Column(name = "view_flag")
    private Boolean viewFlag;

    //    개인 수정 권한의 허용 여부
    @Column(name = "edit_flag")
    private Boolean editFlag;

    //    개인 삭제 권한의 허용 여부
    @Column(name = "delete_flag")
    private Boolean deleteFlag;

    //    개인 검색 권한의 허용 여부
    @Column(name = "search_flag")
    private Boolean searchFlag;

    //    개인 메뉴 권한의 허용 여부
    @Column(name = "excel_flag")
    private Boolean excelFlag;
}
