package com.wellnetworks.wellcore.java.domain.partner;
// 거래처 그룹
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class WellPartnerGroupEntity {

    @Id //거래처그룹_id
    @GeneratedValue
    @Column(name = "p_group_id")
    private Long partnerGroupId;

    @Column(name = "p_group_name")
    private String PartnerGroupName;

    @OneToMany(mappedBy = "partnerGroup") // 파트너 리스트 가져오기(양방향)
    private List<WellPartnerEntity> partners = new ArrayList<>();

//    @OneToMany(mappedBy = "partnerGroupSign") // 파트너 회원가입 리스트 가져오기(양방향)
//    private List<WellPartnerSignEntity> partnerSign = new ArrayList<>();
}
