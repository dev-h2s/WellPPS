package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.dto.usersDTO
import com.wellnetworks.wellcore.repository.usersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class usersService {
    @Autowired
    lateinit var usersRepository: usersRepository

    fun getUserByUserID(userid: String): List<usersDTO> {
        val user = usersRepository.findByUserID(userid)
        return user.map { it.getUsersDTO() }
    }
}