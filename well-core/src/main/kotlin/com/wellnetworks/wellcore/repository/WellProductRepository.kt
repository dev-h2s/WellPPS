package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import java.util.*

//crud를 위한 메서드를 상속
interface WellProductRepository: CrudRepository<WellProductEntity, String>, JpaSpecificationExecutor<WellProductEntity> {
    // idx 기반 개통 entity 조회
    // optional은 값이 존재하지 않을 수 있는 경우 코드를 작성할 수 있게 해준다 조회, 업데이트 사용
    fun findByIdx(idx: String): Optional<WellProductEntity>
    //페이지네이션된 형태로 모든 엔티티 조회시 사용(검색)
    fun findAll(pageable: Pageable): Page<WellProductEntity>
    // 삭제시 사용(삭제된 엔티티의 갯수를 알려주기 위한 int)
    fun deleteByIdx(idx: String): Optional<Int>
   //제품 카운트 관련
    fun countBySort1Equals(type: Int): Long
    //제품 카운트 관련
    fun countByRunFlagIsTrueAndSort1Equals(type: Int): Long

    //fun countByProductNameIsNotNull(): Long

    //fun countByRunFlagIsTrueAndProductNameIsNotNull(): Long


}