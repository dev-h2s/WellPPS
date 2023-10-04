package com.wellnetworks.wellcore.java.domain.partner;
// 거래처 관리자 그룹
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.ZonedDateTime;

@Entity
@Getter
public class WellPartnerManagerGroupEntity {

    @Id @GeneratedValue //그룹별권한
    @Column(name = "pm_gkey")
    private String partnerManagerGroupKey;

    @Column(name = "pm_name") //그룹명
    private String partnerManagerGroupName;

    @Column(name = "pm_permission") //권한
    private String partnerManagerGroupPermission;

    @Column(name = "pm_description") //설명
    private String partnerManagerGroupDescription;

    @Column(name = "pm_moddt") //권한채택일
    private ZonedDateTime partnerManagerGroupModifyDate;

    @Column(name = "pm_regdt") //권한수정일
    private ZonedDateTime partnerManagerGroupRegisterDate;
}
