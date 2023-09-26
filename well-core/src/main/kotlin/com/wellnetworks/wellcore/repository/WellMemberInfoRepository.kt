package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellMemberInfoEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellMemberInfoRepository: CrudRepository<WellMemberInfoEntity, String>, JpaSpecificationExecutor<WellMemberInfoEntity> {
    // member idx로 조회
    fun findByIdx(idx: String): Optional<WellMemberInfoEntity>
    //  페이지네이션을 적용하여 모든 맴버 엔티티 검색
    fun findAll(pageable: Pageable): Page<WellMemberInfoEntity>
    // member name으로 조회
    fun findByName(name: String) : Optional<WellMemberInfoEntity>
    // idx로 삭제
    fun deleteByIdx(idx: String): Optional<Int>

}
