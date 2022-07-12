package com.wellnetworks.wellcore.repository

import org.springframework.data.repository.CrudRepository
import com.wellnetworks.wellcore.domain.WellUser
import java.util.Optional

interface WellUserRepository: CrudRepository<WellUser, String> {
    fun findByUserID(userid: String): Optional<WellUser>
    fun existByUserID(userid: String): Boolean
}