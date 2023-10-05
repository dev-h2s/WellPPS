package com.wellnetworks.wellcore.java.domain.member;
//맴버 유저 테이블
import com.wellnetworks.wellcore.domain.converter.*;
import com.wellnetworks.wellcore.domain.dto.WellMemberDTOUpdate;
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO;
import com.wellnetworks.wellcore.domain.enums.*;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.Hibernate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "member_user_tb", indexes = {@Index(name = "m_idx", columnList = "memberIdx",unique = true)})
public class WellMemberUserEntity {

    @Id
    @Column(name = "m_idx", columnDefinition = "uniqueidentifier") //맴버 고유 식별자 idx
    private String memberIdx;

    @ManyToOne(fetch = FetchType.LAZY) //유저의 맴버 그룹_id
    @JoinColumn(name = "mm_gkey")
    private WellMemberManagerGroupEntity memberGroup;

    @Column(name = "m_identification") //로그인시 아이디
    private String memberIdentification;

    @Column(name = "pwd") //로그인시 비밀번호
    private String memberUserPwd;

    @Column(name = "permissions") //권한
    private String permissions;

    @Column(name = "tmp_pwd") //임시비밀번호
    private String tmpPwd;

    @Column(name = "tmp_pwd_expiration") //임시비밀번호 만료날짜
    private LocalDateTime tmpPwdExpiration;

    @Column(name = "tmp_pwd_count") //임시비밀번호 사용횟수
    private int tmpPwdCount;

    @Column(name = "tmp_pwd_dt") //임시비밀번호 생성일자
    private LocalDateTime tmpPwdDate;

    @Column(name = "m_u_moddt") //유저정보 수정일자
    private LocalDateTime memberUserModifyDate;

    @Column(name = "m_u_regdt") //유저정보 수정일자
    private LocalDateTime getMemberUserRegisterDate;

    @Column(name = "Group_key") //그룹식별자
    private String groupKey;
}
