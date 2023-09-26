package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellUserEntity
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.PermissionKey
import com.wellnetworks.wellcore.repository.WellPermissionRepository
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
    // lateinit : 프로퍼티의 초기화를 미루는 데 사용
    private lateinit var wellUserRepository: WellUserRepository

    @Autowired
    private lateinit var wellPermissionRepository: WellPermissionRepository

    // Enum 클래스 정의 -> db찾는데 쓰이는 키로 보임
    enum class SearchDBKey(val key: String) {
        START_DATE("")
    }

    // 사용자 아이디로 사용자 정보 가져오기
    fun getUserByUserID(userid: String): Optional<WellUserDTO> {
        val user = wellUserRepository.findByUserID(userid)
        return user.map { it.getWellUserDTO() }
    }

    // 사용자 아이디로 사용자가 존재하는지 확인
    fun existByUserID(userid: String): Boolean {
        return wellUserRepository.existsByUserID(userid)
    }
/*
    fun deleteUserById(idx: String): Optional<WellPartnerDTO> {
        val partner = wellUserRepository.deleteByIdx(idx)
        return partner.map { it.toDto() }
    }
*/
// 사용자 생성
    @Transactional
    // 트랜잭션은 데이터베이스 작업을 안전하게 처리하기 위한 스프링 어노테이션으로, 메서드 실행 도중 에러가 발생하면 모든 변경 사항을 원래 상태로 되돌려 줌
    fun createUser(user: WellUserDTOCreate): String? {
        val uuidUser = UUID.randomUUID().toString().uppercase() // guid생성 예정
        val createUser = WellUserEntity(uuidUser,
            user.UserID, user.PermissionsKeysStringList, user.GroupPermissionIdx?.uppercase(),
            user.Password_Hash, "", ZonedDateTime.now(), 0, ZonedDateTime.now())
        try {
            //db에 create문 생성 후 저장
            wellUserRepository.save(createUser)
        } catch (e: IllegalArgumentException) {
            return null
        }
        return uuidUser
    }

    // 데이터 총 개수 가져오기
    fun dataTotalCount(): Long {
        return wellUserRepository.count()
    }

    // 권한 목록 가져오기
    fun getPermissionList(): List<WellPermissionDTO> {
        val permissions = wellPermissionRepository.findAll()

        return permissions.map { it.getWellPermisionDTO() }
    }

    // 비밀번호 업데이트
    @Transactional(rollbackFor = [Exception::class])
    //  메서드 실행 중에 예외가 발생하면 예외 종류가 Exception인 경우에만 롤백(변경 사항 취소)을 수행한다는 것을 나타내는 어노테이션
    fun updatePassword(user: WellUserDTOUpdate): Boolean {

        try {
            val currentEntity = wellUserRepository.findByIdx(user.Idx.uppercase()).orElse(null) ?: return false
            val permissions: List<String> = listOf (
                PermissionKey.MEMBER_SCREENING,
            )

            currentEntity.updateDto(user)

            wellUserRepository.save(currentEntity)
        }catch (e: Exception) {
            return false
        }

        return true
    }

    // 특정 사용자의 임시 비밀번호 생성 횟수 가져오기
    fun getTmpPassCountByIdx(idx: String): Byte {
        val user = wellUserRepository.findByIdx(idx)

        return  user.get().temporaryPasswordCreateCount
    }


}