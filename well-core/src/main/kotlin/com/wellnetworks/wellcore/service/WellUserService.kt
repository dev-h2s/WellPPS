package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellUserEntity
import com.wellnetworks.wellcore.domain.dto.WellUserCreateDTO
import com.wellnetworks.wellcore.domain.dto.WellUserDTO
import com.wellnetworks.wellcore.repository.WellUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.ZonedDateTime
import java.util.Optional
import java.util.UUID

@Component
class WellUserService {
    @Autowired
    lateinit var wellUserRepository: WellUserRepository

    fun getUserByUserID(userid: String): Optional<WellUserDTO> {
        val user = wellUserRepository.findByUserid(userid)
        return user.map { it.getWellUserDTO() }
    }

    fun existByUserID(userid: String): Boolean {
        return wellUserRepository.existsByUserid(userid)
    }

    @Transactional
    fun createUser(user: WellUserCreateDTO): UUID? {
        val uuidUser = UUID.randomUUID()
        val createUser = WellUserEntity(uuidUser,
            user.userid, user.rule, user.permission, user.password_hash,
            "", ZonedDateTime.now(), 0, false, ZonedDateTime.now())
        try {
            wellUserRepository.save(createUser)
        } catch (e: IllegalArgumentException) {
            return null
        }
        return uuidUser
    }

    fun dataTotalCount(): Long {
        return wellUserRepository.count()
    }
}