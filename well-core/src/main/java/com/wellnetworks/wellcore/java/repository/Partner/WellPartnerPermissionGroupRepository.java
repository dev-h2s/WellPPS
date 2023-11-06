package com.wellnetworks.wellcore.java.repository.Partner;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerPermissionGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WellPartnerPermissionGroupRepository extends JpaRepository<WellPartnerPermissionGroupEntity, String> {

    Optional<WellPartnerPermissionGroupEntity> findByDepartment(String department);
}
