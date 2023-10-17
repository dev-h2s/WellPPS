package com.wellnetworks.wellcore.java.domain.backup.partner;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class WellPartnerGroupEntityBackup {

    @Id //거래처그룹_id
    @Column(name = "p_group_id")
    private Long partnerGroupId;

    @OneToMany(mappedBy = "partnerGroup") // 파트너 리스트 가져오기(양방향)
    private List<WellPartnerEntityBackup> partners = new ArrayList<>();

}