package com.wellnetworks.wellcore.java.domain.refreshtoken;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 포함한 생성자 생성
@Builder // 빌더 패턴 구현
@Table(name = "partner_refresh_tokens")
public class PartnerRefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_idx")
    private WellPartnerUserEntity partnerUser;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    @Column(nullable = false)
    private Date expiryDate;

    public PartnerRefreshTokenEntity(WellPartnerUserEntity partnerUser, String refreshToken, Date expiryDate) {
        this.partnerUser = partnerUser;
        this.refreshToken = refreshToken;
        this.expiryDate = expiryDate;
    }

    public static PartnerRefreshTokenEntity createToken(WellPartnerUserEntity partner, String token, Date expiryDate) {
        // 부분 생성자를 사용하여 새로운 인스턴스를 생성
        return new PartnerRefreshTokenEntity(partner, token, expiryDate);
    }

    // 리프레쉬 토큰 업데이트 메소드
    public void updateToken(String newToken, Date newExpiryDate) {
        this.refreshToken = newToken;
        this.expiryDate = newExpiryDate;
    }
}
