package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellPartnerEntity
import com.wellnetworks.wellcore.domain.enums.CompanyStateType
import com.wellnetworks.wellcore.domain.enums.CompanyType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellPartnerRepository: CrudRepository<WellPartnerEntity, String>, JpaSpecificationExecutor<WellPartnerEntity> {
    fun findByIdx(idx: UUID): Optional<WellPartnerEntity>
    fun findAll(pageable: Pageable): Page<WellPartnerEntity>
    fun findByCompanyName(partnerName: String): Optional<WellPartnerEntity>

    fun countByCompanyState(companyState: CompanyStateType): Long
}