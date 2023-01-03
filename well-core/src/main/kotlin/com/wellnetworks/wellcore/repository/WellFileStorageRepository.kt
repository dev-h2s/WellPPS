package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellFileStorageEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellFileStorageRepository: CrudRepository<WellFileStorageEntity, String> {
    fun findFirstByIdx(idx: String): Optional<WellFileStorageEntity>
    fun findFirstByIdxAndPublicTrue(idx: String): Optional<WellFileStorageEntity>
    fun findFirstByIdxAndPublicFalse(idx: String): Optional<WellFileStorageEntity>
    fun existsByIdxAndPublicTrue(idx: String): Boolean
    fun existsByIdx(idx: String): Boolean
    fun deleteByIdx(idx: String): Optional<WellFileStorageEntity>
}