package com.wellnetworks.wellcore.java.domain.refreshtoken;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "partner_refresh_tokens")
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 포함한 생성자 생성
@Builder // 빌더 패턴 구현
public class PartnerRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "partner_idx")
    private WellPartnerUserEntity partnerUser;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    @Column(nullable = false)
    private Date expiryDate;

}