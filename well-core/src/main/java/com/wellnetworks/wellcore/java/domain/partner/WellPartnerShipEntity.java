package com.wellnetworks.wellcore.java.domain.partner;

import jakarta.persistence.*;
import lombok.Getter;

// 업무제휴
@Entity
@Getter
public class WellPartnerShipEntity {
    @Id //업무제휴 고유id
    @Column(name = "partnership_id", columnDefinition = "uniqueidentifier", unique = true, nullable = false)
    private Integer partnershipId;

    @Column(name = "partnership_name") //업체명
    private String partnershipName;

    @Column(name = "sales_manager") //담당자 성명
    private String salesManager;

    @Column(name = "email") //이메일 주소
    private String email;

    @Column(name = "tel") //연락처
    private String tel;

    @Column(name = "note") //비고 내용
    private String note;

    @Column(name = "agreement") //개인정보수집동의여부
    private Boolean agreement;

}
