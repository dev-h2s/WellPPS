package com.wellnetworks.wellcore.java.repository.Partner;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerSignEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WellPartnerSignRepository extends JpaRepository<WellPartnerSignEntity, String>, JpaSpecificationExecutor<WellPartnerSignEntity> {



}
