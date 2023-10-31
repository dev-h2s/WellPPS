package com.wellnetworks.wellcore.java.domain.partner;
// 거래처 관리자 그룹

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WellPartnerPermissionGroupEntity {

    @Id //그룹별권한
    @Column(name = "pm_gkey")
    private String partnerManagerGroupKey;

    @OneToMany(mappedBy = "partnerManagerGroup") // 유저 리스트 가져오기(양방향)
    private List<WellPartnerUserEntity> partnerUsers = new ArrayList<>();

    @Column(name = "pm_name") //그룹명
    private String partnerManagerGroupName;

    @Column(name = "pm_permission") //권한
    private String partnerManagerGroupPermission;

    @Column(name = "pm_description") //설명
    private String partnerManagerGroupDescription;

    @Column(name = "pm_moddt") //권한채택일
    private LocalDateTime partnerManagerGroupModifyDate;

    @Column(name = "pm_regdt") //권한수정일
    private LocalDateTime partnerManagerGroupRegisterDate;
}
