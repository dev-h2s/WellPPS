package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellOpeningEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellOpeningRepository: CrudRepository<WellOpeningEntity, String>,
    JpaSpecificationExecutor<WellOpeningEntity> {
        // idx 기반 개통 entity 조회
        // optional은 값이 존재하지 않을 수 있는 경우 코드를 작성할 수 있게 해준다 조회, 업데이트 사용
    fun findByIdx(idx: String): Optional<WellOpeningEntity>
    //페이지네이션된 형태로 모든 엔티티 조회시 사용(검색)
    fun findAll(pageable: Pageable): Page<WellOpeningEntity>
    // 삭제시 사용(삭제된 엔티티의 갯수를 알려주기 위한 int)
    fun deleteByIdx(idx: String): Optional<Int>


}