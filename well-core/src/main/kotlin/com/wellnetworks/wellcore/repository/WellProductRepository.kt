package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellPartnerEntity
import com.wellnetworks.wellcore.domain.WellProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellProductRepository: CrudRepository<WellProductRepository, String>, JpaSpecificationExecutor<WellProductEntity> {
    fun findByIdx(idx: String): Optional<WellProductEntity>

    fun findAll(pageable: Pageable): Page<WellProductEntity>

    fun deleteByIdx(idx: String): Optional<Int>

}