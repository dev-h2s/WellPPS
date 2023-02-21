package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellGroupEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface WellGroupRepository: CrudRepository<WellGroupEntity, String> {
    fun findByGroupPermissionKey (groupPermissionKey: String): Optional<WellGroupEntity>
    fun findAll(pageable: Pageable): Page<WellGroupEntity>
    fun deleteByGroupPermissionKey(groupPermissionKey: String): Optional<Int>
}