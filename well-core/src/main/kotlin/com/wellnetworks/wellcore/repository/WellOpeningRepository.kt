package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellOpeningEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellOpeningRepository: CrudRepository<WellOpeningEntity, String>,
    JpaSpecificationExecutor<WellOpeningEntity> {
    fun findByIdx(idx: String): Optional<WellOpeningEntity>

    fun findAll(pageable: Pageable): Page<WellOpeningEntity>

    fun deleteByIdx(idx: String): Optional<Int>


}