package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface WellPartnerUserRepository extends JpaRepository<WellPartnerUserEntity, Long> {
    Optional<WellPartnerUserEntity> findByPartnerIdentification(String employeeIdentification);

    // 아이디 중복검사
    boolean existsByPartnerIdentification(String employeeIdentification);

    // 메서드 이름 수정: 규칙에 따라서
    void deleteByPartnerIdx(String employeeIdx);
}
