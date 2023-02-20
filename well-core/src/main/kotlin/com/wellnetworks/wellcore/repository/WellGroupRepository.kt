package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellGroupEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface WellGroupRepository: CrudRepository<WellGroupEntity, String> {
    fun findByIdx(idx: String): Optional<WellGroupEntity>
    fun findAll(pageable: Pageable): Page<WellGroupEntity>
    fun deleteByIdx(idx: String): Optional<Int>
}