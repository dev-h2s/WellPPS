package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellGroupEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import java.util.Optional

interface WellGroupRepository: CrudRepository<WellGroupEntity, String> {
    // 그룹 권한 키 찾기
    fun findByGroupPermissionKey (groupPermissionKey: String): Optional<WellGroupEntity>
    // 모든 정보 찾기
    fun findAll(pageable: Pageable): Page<WellGroupEntity>
    // 그룹 권한 키 삭제
    fun deleteByGroupPermissionKey(groupPermissionKey: String): Optional<Int>
}