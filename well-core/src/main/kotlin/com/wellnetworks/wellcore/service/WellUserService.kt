package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellUserEntity
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.PermissionList
import com.wellnetworks.wellcore.domain.enums.TableIDList
import com.wellnetworks.wellcore.repository.WellPermissionRepository
import com.wellnetworks.wellcore.repository.WellUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.ZonedDateTime
import java.util.Optional
import java.util.UUID

@Component
class WellUserService {
    @Autowired
    private lateinit var wellUserRepository: WellUserRepository

    @Autowired
    private lateinit var wellPermissionRepository: WellPermissionRepository

    enum class SearchDBKey(val key: String) {
        START_DATE("")
    }

    fun getUserByUserID(userid: String): Optional<WellUserDTO> {
        val user = wellUserRepository.findByUserID(userid)
        return user.map { it.getWellUserDTO() }
    }

    fun existByUserID(userid: String): Boolean {
        return wellUserRepository.existsByUserID(userid)
    }
/*
    fun deleteUserById(idx: String): Optional<WellPartnerDTO> {
        val partner = wellUserRepository.deleteByIdx(idx)
        return partner.map { it.toDto() }
    }
*/
    @Transactional
    fun createUser(user: WellUserDTOCreate): String? {
        val uuidUser = UUID.randomUUID().toString().uppercase()
        val createUser = WellUserEntity(uuidUser,
            user.UserID, user.PermissionsKeysStringList, user.Password_Hash,
            "", ZonedDateTime.now(), 0, ZonedDateTime.now())
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

    fun getPermissionList(): List<WellPermissionDTO> {
        val permissions = wellPermissionRepository.findAll()

        return permissions.map { it.getWellPermisionDTO() }
    }
/*
    @Transactional(rollbackFor = [Exception::class])
    fun updateTempPwdById(user: WellMemberDTOUpdate, files: List<MultipartFile>?): Boolean {


        return true
    }

 */
}