package com.wellnetworks.wellcore.java.domain.partner;
// 거래처 유저
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.ZonedDateTime;

@Entity
@Getter
public class WellPartnerUserEntity {

    @Id @GeneratedValue //거래처_idx
    @Column(name = "p_idx", columnDefinition = "uniqueidentifier")
    private String partnerIdx;

    @Column(name = "pm_gkey") //그룹별권한
    private String partnerManagerGroupKey;

    @Column(name = "p_identification") //로그인시 아이디
    private String partnerIdentification;

    @Column(name = "pwd")//거래처_패스워드
    private String partnerUserPwd;

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

    @Column(name = "p_u_moddt") //유저정보 수정일자
    private ZonedDateTime partnerUserModifyDate;

    @Column(name = "p_u_regdt") //유저정보 생성일자
    private ZonedDateTime partnerUserRegisterDate;

}
