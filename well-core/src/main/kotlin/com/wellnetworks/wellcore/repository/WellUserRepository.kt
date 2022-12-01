package com.wellnetworks.wellcore.repository

import org.springframework.data.repository.CrudRepository
import com.wellnetworks.wellcore.domain.WellUserEntity
import java.util.Optional

interface WellUserRepository: CrudRepository<WellUserEntity, String> {
    fun findByUserID(userID: String): Optional<WellUserEntity>
    fun existsByUserID(userID: String): Boolean

}