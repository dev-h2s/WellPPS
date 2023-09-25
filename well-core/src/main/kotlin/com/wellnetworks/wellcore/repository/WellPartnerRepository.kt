package com.wellnetworks.wellcore.repository
// crud문 실행
import com.wellnetworks.wellcore.domain.WellPartnerEntity
import com.wellnetworks.wellcore.domain.enums.CompanyStateType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellPartnerRepository: CrudRepository<WellPartnerEntity, String>, JpaSpecificationExecutor<WellPartnerEntity> {
    fun findByIdx(idx: String): Optional<WellPartnerEntity>
    fun findAll(pageable: Pageable): Page<WellPartnerEntity>
    fun findByCompanyName(partnerName: String): Optional<WellPartnerEntity>

    fun countByCompanyState(companyState: CompanyStateType): Long

    fun countByTaxRegistrationDocFileIdxAndContractDocFileIdx(taxRegistrationDocFileIdx: String, contractDocFileIdx: String): Long

    fun countByTaxRegistrationDocFileIdx(taxRegistrationDocFileIdx: String) : Long

    fun countByTaxRegistrationDocFileIdxIsNull() : Long

    fun countByContractDocFileIdxIsNull() : Long

    fun deleteByIdx(idx: String): Optional<Int>
}