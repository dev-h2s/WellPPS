package com.wellnetworks.wellcore.repository

import org.springframework.data.repository.CrudRepository
import com.wellnetworks.wellcore.domain.Users

interface usersRepository: CrudRepository<Users, String> {
    fun findByUserID(userid: String): List<Users>
}