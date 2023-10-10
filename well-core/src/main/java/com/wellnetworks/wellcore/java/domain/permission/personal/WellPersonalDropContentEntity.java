package com.wellnetworks.wellcore.java.domain.permission.personal;
// 개인 dropwodn content
import com.wellnetworks.wellcore.java.domain.charge.WellChargeHistoryEntity;
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
@Table(name = "personal_dropdown_content_tb", indexes = {@Index(name = "per_drop_content_id", columnList = "perDropContentId",unique = true)})
public class WellPersonalDropContentEntity {

    //    개인 drop down들의 content를 지정하기 위한 pk
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "per_drop_content_id")
    private Long perDropContentId;

    // 개인 dropdown의 FK로 받는 id 단방향
    @ManyToOne
    @JoinColumn(name = "per_drop_id")
    private WellPersonalDropDownEntity personalDropDown;

    //    dropdowncontent의 이름
    @Column(name = "drop_content_name")
    private String contentName;
}
