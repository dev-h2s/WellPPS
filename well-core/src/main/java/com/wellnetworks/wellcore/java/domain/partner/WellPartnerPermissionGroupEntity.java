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

    @OneToMany(mappedBy = "partnerManagerGroupKey") // 유저 리스트 가져오기(양방향)
    private List<WellPartnerUserEntity> partnerUsers = new ArrayList<>();

    @Column(name = "department")//부서명
    private String department;

    @Column(name = "pm_g_permissions") //직원 권한
    private String partnerManagerPermissions;

    @Column (name = "pm_g_description") //직원 권한에 대한 설명
    private String partnerManagerDescription;

    @Column(name = "pm_g_moddt") //권한채택일
    private LocalDateTime partnerManagerModifyDate;

    @Column(name = "pm_g_regdt") //생성 날짜와 시간
    private LocalDateTime partnerManagerRegisterDate;
}
