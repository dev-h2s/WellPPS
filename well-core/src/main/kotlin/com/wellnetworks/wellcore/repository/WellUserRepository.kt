package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellPartnerEntity
import org.springframework.data.repository.CrudRepository
import com.wellnetworks.wellcore.domain.WellUserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional
import java.util.UUID

interface WellUserRepository: CrudRepository<WellUserEntity, String>, JpaSpecificationExecutor<WellUserEntity> {
    fun findByIdx(idx: String): Optional<WellUserEntity>
    fun findAll(pageable: Pageable): Page<WellUserEntity>
    fun findByUserID(userID: String): Optional<WellUserEntity>
    fun existsByUserID(userID: String): Boolean
    fun deleteByIdx(idx: String): Optional<Int>

}