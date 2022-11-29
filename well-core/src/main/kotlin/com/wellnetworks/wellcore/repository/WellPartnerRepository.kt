package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellPartnerEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellPartnerRepository: CrudRepository<WellPartnerEntity, String> {
    fun findByUser_idx(uuid: UUID): Optional<WellPartnerEntity>
}