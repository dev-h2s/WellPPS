package com.wellnetworks.wellcore.java.domain.member;

import com.wellnetworks.wellcore.domain.converter.*;
import com.wellnetworks.wellcore.domain.dto.WellMemberDTOUpdate;
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO;
import com.wellnetworks.wellcore.domain.enums.*;
import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.ZonedDateTime;


@Entity
@Table(name="member_manager_group_tb")
public class WellMemberManagerGroupEntity {

    //맴버 관리자그룹
    @Id
    @Column(name = "mm_gkey")
    private String memberManagerGroupKey;
    //맴버 그룹명
    @Column(name = "mm_name")
    private String memberManagerName;
    @Column(name = "permissions") //맴버 권한
    private String permissions;
    @Column (name = "description") //맴버 권한에 대한 설명
    private String description;
    @Column(name = "mm_moddt") //권한채택일
    private ZonedDateTime memberManagerModifyDate;
    @Column(name = "mm_regdt") //생성 날짜와 시간
    private ZonedDateTime memberManagerRegisterDate;


}
