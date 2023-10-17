package com.wellnetworks.wellcore.java.domain.backup.partner;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class WellPartnerPermissionGroupEntityBackup {

    @Id //그룹별권한
    @Column(name = "pm_gkey")
    private String partnerManagerGroupKey;

    @OneToMany(mappedBy = "partnerManagerGroup") // 유저 리스트 가져오기(양방향)
    private List<WellPartnerUserEntityBackup> partnerUsers = new ArrayList<>();

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