package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.dto.WellUserDTO
import com.wellnetworks.wellcore.repository.WellUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class WellUserService {
    @Autowired
    lateinit var wellUserRepository: WellUserRepository

    fun getUserByUserID(userid: String): Optional<WellUserDTO> {
        val user = wellUserRepository.findByUserID(userid)
        return user.map { it.getWellUserDTO() }
    }

    fun existUserByUserID(userid: String): Boolean {
        return wellUserRepository.existByUserID(userid)
    }
}