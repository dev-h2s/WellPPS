package com.wellnetworks.wellcore.java.domain.partner;
// 거래처 유저
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class WellPartnerUserEntity {

    @Id //거래처_id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "p_id")
    private Long partnerId;

    @OneToOne(fetch = LAZY) //거래처 1대1
    @JoinColumn(name = "partnerId")
    private WellPartnerEntity partner; //거래처 엔티티 참조

    @ManyToOne(fetch = LAZY) //그룹별권한
    @JoinColumn(name = "pm_gkey", insertable = false, updatable = false)
    private WellPartnerPermissionGroupEntity partnerManagerGroup; //거래처유저그룹 엔티티 참조

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
    private Integer tmpPwdCount;

    @Column(name = "tmp_pwd_dt") //임시비밀번호 생성일자
    private LocalDateTime tmpPwdDate;

    @Column(name = "p_u_moddt") //유저정보 수정일자
    private LocalDateTime partnerUserModifyDate;

    @Column(name = "p_u_regdt") //유저정보 생성일자
    private LocalDateTime partnerUserRegisterDate;

    @Builder
    public WellPartnerUserEntity(WellPartnerPermissionGroupEntity partnerManagerGroup, String partnerIdentification, String partnerUserPwd, String permissions, String tmpPwd, LocalDateTime tmpPwdExpiration, Integer tmpPwdCount, LocalDateTime tmpPwdDate, LocalDateTime partnerUserModifyDate, LocalDateTime partnerUserRegisterDate
                                ) {
        this.partnerManagerGroup = partnerManagerGroup;
        this.partnerIdentification = partnerIdentification;
        this.partnerUserPwd = partnerUserPwd;
        this.permissions = permissions;
        this.tmpPwd = tmpPwd;
        this.tmpPwdExpiration = tmpPwdExpiration;
        this.tmpPwdCount = tmpPwdCount;
        this.tmpPwdDate = tmpPwdDate;
        this.partnerUserModifyDate = partnerUserModifyDate;
        this.partnerUserRegisterDate = partnerUserRegisterDate;
    }
}
