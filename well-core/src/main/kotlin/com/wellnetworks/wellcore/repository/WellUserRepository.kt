package com.wellnetworks.wellcore.repository
// user 관련 정보 crud 생성
import org.springframework.data.repository.CrudRepository
import com.wellnetworks.wellcore.domain.WellUserEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.util.Optional

interface WellUserRepository: CrudRepository<WellUserEntity, String>, JpaSpecificationExecutor<WellUserEntity> {
    // idx찾기
    fun findByIdx(idx: String): Optional<WellUserEntity>
    // 모든 정보 찾기
    fun findAll(pageable: Pageable): Page<WellUserEntity>
    // 그룹권한 키 개수
    fun countByGroupPermissionKey(gkey: String): Long
    // userId 찾기
    fun findByUserID(userID: String): Optional<WellUserEntity>
    // userID 값이 존재하는지 여부 판단
    fun existsByUserID(userID: String): Boolean
    // idx 삭제
    fun deleteByIdx(idx: String): Optional<Int>

}