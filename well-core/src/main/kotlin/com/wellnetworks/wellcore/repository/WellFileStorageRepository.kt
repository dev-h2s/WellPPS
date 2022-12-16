package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellFileStorageEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellFileStorageRepository: CrudRepository<WellFileStorageEntity, String> {
    fun findFirstByIdx(idx: UUID): Optional<WellFileStorageEntity>
    fun findFirstByIdxAndPublic(idx: UUID, isPublic: Boolean): Optional<WellFileStorageEntity>
    fun existsByIdxAndPublic(idx: UUID, isPublic: Boolean): Boolean
    fun deleteByIdx(idx: UUID): Optional<WellFileStorageEntity>
}