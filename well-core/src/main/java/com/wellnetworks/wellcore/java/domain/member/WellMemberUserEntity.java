package com.wellnetworks.wellcore.java.domain.member;

import com.wellnetworks.wellcore.domain.converter.*;
import com.wellnetworks.wellcore.domain.dto.WellMemberDTOUpdate;
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO;
import com.wellnetworks.wellcore.domain.enums.*;
import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.ZonedDateTime;
@Entity
@Table(name = "member_user_tb", indexes = {@Index(name = "IX_tid", columnList = "tbl_id ASC")})
public class WellMemberUserEntity {
    //맴버 고유 식별자 idx
    @Id
    @Column(name = "m_idx", columnDefinition = "uniqueidentifier")
    private String memberIdx;
    //로그인시 아이디
    @Column(name = "m_identification")
    private String memberIdentification;
    //로그인시 패스워드
    @Column(name = "pwd")
    private String memberUserPwd;
    //권한
    @Column(name = "permissions")
    private String permissions;
    //임시비밀번호
    @Column(name = "tmp_pwd")
    private String tmpPwd;
    //임시비밀번호 만료날짜
    @Column(name = "tmp_pwd_expiration")
    private ZonedDateTime tmpPwdExpiration;
    //임시비밀번호 사용횟수
    @Column(name = "tmp_pwd_count")
    private int tmpPwdCount;
    //임시비밀번호 생성일자
    @Column(name = "tmp_pwd_dt")
    private ZonedDateTime tmpPwdDate;
    //유저정보 수정일자
    @Column(name = "m_u_moddt")
    private ZonedDateTime memberUserModifyDate;
    //유저정보 수정일자
    @Column(name = "m_u_regdt")
    private ZonedDateTime getMemberUserRegisterDate;
    //그룹식별자
    @Column(name = "Group_key")
    private String groupKey;
}
