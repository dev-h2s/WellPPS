package com.wellnetworks.wellcore.java.domain.partner;
// 거래처 그룹
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class WellPartnerGroupEntity {

    @Id //거래처그룹_id
    @Column(name = "p_group_id")
    private Long partnerGroupId;
}
