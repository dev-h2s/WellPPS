package com.wellnetworks.wellcore.java.domain.backup.partner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerPermissionGroupEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
public class WellPartnerUserEntityBackup {

    @Id //거래처_id
    @Column(name = "p_id")
    private Long partnerId;

    @JsonIgnore //순환참조 문제 방지
    @OneToOne(fetch = LAZY, mappedBy = "partnerId") //거래처 1대1
    private WellPartnerEntityBackup partner; //거래처 엔티티 참조

    @ManyToOne(fetch = LAZY) //그룹별권한
    @JoinColumn(name = "pm_gkey", insertable = false, updatable = false)
    private WellPartnerPermissionGroupEntityBackup partnerManagerGroup; //거래처유저그룹 엔티티 참조

    @Column(name = "p_identification") //로그인시 아이디
    private String partnerIdentification;

    @Column(name = "pwd")//거래처_패스워드
    private String partnerUserPwd;

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

    @Column(name = "p_u_moddt") //유저정보 수정일자
    private LocalDateTime partnerUserModifyDate;

    @Column(name = "p_u_regdt") //유저정보 생성일자
    private LocalDateTime partnerUserRegisterDate;
}
