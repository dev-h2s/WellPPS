package com.wellnetworks.wellcore.java.domain.member;
//맴버 그룹 테이블
import com.wellnetworks.wellcore.domain.converter.*;
import com.wellnetworks.wellcore.domain.dto.WellMemberDTOUpdate;
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO;
import com.wellnetworks.wellcore.domain.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "member_manager_group_tb", indexes = {@Index(name = "mm_gkey", columnList = "memberManagerGroupKey",unique = true)})
public class WellMemberManagerGroupEntity {

    @Id
    @Column(name = "mm_gkey")//맴버 관리자그룹
    private String memberManagerGroupKey;

    @OneToMany(mappedBy = "memberGroup") // 양방향 일때 맴버 리스트 가져오기
    private List<WellMemberInfoEntity> members = new ArrayList<>();

    @Column(name = "mm_name")//맴버 그룹명
    private String memberManagerName;

    @Column(name = "mm_permissions") //맴버 권한
    private String memberManagerPermissions;

    @Column (name = "mm_description") //맴버 권한에 대한 설명
    private String memberManagerDescription;

    @Column(name = "mm_moddt") //권한채택일
    private LocalDateTime memberManagerModifyDate;

    @Column(name = "mm_regdt") //생성 날짜와 시간
    private LocalDateTime memberManagerRegisterDate;


}
