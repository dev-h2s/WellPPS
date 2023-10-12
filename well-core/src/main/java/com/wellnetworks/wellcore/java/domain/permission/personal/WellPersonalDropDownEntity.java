package com.wellnetworks.wellcore.java.domain.permission.personal;
// 개인 dropdown
import com.wellnetworks.wellcore.java.domain.charge.WellChargeHistoryEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductSearchEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "personal_dropdown_tb")
public class WellPersonalDropDownEntity {
    // 개인 권한의 dropdown의 각 권한 여부를 지정하기 위한 pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_drop_id")
    private Long perDropId;

    // 직원 테이블에서 받는 fk 단방향
    @OneToOne
    @JoinColumn(name = "em_id", insertable = false, updatable = false)
    private WellEmployeeEntity employee;

    // 개인 dropdown의 이름
    @Column(name = "per_drop_name")
    private String dropName;
}
