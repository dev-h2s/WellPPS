package com.wellnetworks.wellcore.repository

import org.springframework.data.repository.CrudRepository
import com.wellnetworks.wellcore.domain.WellUser
import java.util.Optional

interface WellUserRepository: CrudRepository<WellUser, String> {
    fun findByUserid(userid: String): Optional<WellUser>
    fun existsByUserid(userid: String): Boolean

}