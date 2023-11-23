package com.wellnetworks.wellcore.java.repository.member;

import com.wellnetworks.wellcore.java.domain.refreshtoken.PartnerRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRefreshTokenRepository extends JpaRepository<PartnerRefreshToken, Long> {
    // 커스텀 쿼리 메서드를 여기에 추가할 수 있습니다.
}