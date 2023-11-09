package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WellPartnerUserRepository extends JpaRepository<WellPartnerUserEntity, Long> {
    Optional<WellPartnerUserEntity> findByEmployeeIdentification(String employeeIdentification);
//    boolean existsByEmployeeIdentification(String employeeIdentification);

    // 아이디 중복검사
    boolean existsByEmployeeIdentification(String employeeIdentification);
    // 이미 JpaRepository에 있는 메서드이므로 삭제
    // Page<WellEmployeeUserEntity> findAll(Pageable pageable);

    // 이미 JpaRepository에 있는 메서드이므로 삭제
    // WellEmployeeUserEntity save(WellEmployeeUserEntity wellEmployeeUserEntity);

    // 메서드 이름 수정: 규칙에 따라서
    void deleteByEmployeeIdx(String employeeIdx);
}
