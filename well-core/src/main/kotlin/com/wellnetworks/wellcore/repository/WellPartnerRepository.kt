package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellPartnerEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellPartnerRepository: CrudRepository<WellPartnerEntity, String> {
    fun findByCompanyName(partnerName: String): Optional<WellPartnerEntity>
}