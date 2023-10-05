package com.wellnetworks.wellcore.java.domain.partner;
// 거래처 그룹
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class WellPartnerGroupEntity {

    @Id //거래처그룹_id
    @Column(name = "p_group_id")
    private Long partnerGroupId;

    @OneToMany(mappedBy = "partnerGroup") // 파트너 리스트 가져오기(양방향)
    private List<WellPartnerEntity> partners = new ArrayList<>();

}
