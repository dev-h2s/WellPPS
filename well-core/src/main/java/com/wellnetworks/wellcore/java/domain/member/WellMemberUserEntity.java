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

    @Id
    @Column(name = "m_idx", columnDefinition = "uniqueidentifier") //맴버 고유 식별자 idx
    private String memberIdx;

    @Column(name = "m_identification") //로그인시 아이디
    private String memberIdentification;

    @Column(name = "pwd") //로그인시 비밀번호
    private String memberUserPwd;

    @Column(name = "permissions") //권한
    private String permissions;

    @Column(name = "tmp_pwd") //임시비밀번호
    private String tmpPwd;

    @Column(name = "tmp_pwd_expiration") //임시비밀번호 만료날짜
    private ZonedDateTime tmpPwdExpiration;

    @Column(name = "tmp_pwd_count") //임시비밀번호 사용횟수
    private int tmpPwdCount;

    @Column(name = "tmp_pwd_dt") //임시비밀번호 생성일자
    private ZonedDateTime tmpPwdDate;

    @Column(name = "m_u_moddt") //유저정보 수정일자
    private ZonedDateTime memberUserModifyDate;

    @Column(name = "m_u_regdt") //유저정보 수정일자
    private ZonedDateTime getMemberUserRegisterDate;

    @Column(name = "Group_key") //그룹식별자
    private String groupKey;
}
