package com.wellnetworks.wellcore.repository

import org.springframework.data.repository.CrudRepository
import com.wellnetworks.wellcore.domain.WellUserEntity
import java.util.Optional

interface WellUserRepository: CrudRepository<WellUserEntity, String> {
    fun findByUserid(userid: String): Optional<WellUserEntity>
    fun existsByUserid(userid: String): Boolean

}