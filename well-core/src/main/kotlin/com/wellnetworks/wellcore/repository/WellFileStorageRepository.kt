package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellFileStorageEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellFileStorageRepository: CrudRepository<WellFileStorageEntity, String> {
    fun findFirstByIdx(idx: UUID): Optional<WellFileStorageEntity>
    fun findFirstByIdxAndPublicTrue(idx: UUID): Optional<WellFileStorageEntity>
    fun findFirstByIdxAndPublicFalse(idx: UUID): Optional<WellFileStorageEntity>
    fun existsByIdxAndPublicTrue(idx: UUID): Boolean
    fun existsByIdxAndPublicFalse(idx: UUID): Boolean
    fun deleteByIdx(idx: UUID): Optional<WellFileStorageEntity>
}