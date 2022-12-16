package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellMemberInfoEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellMemberInfoRepository: CrudRepository<WellMemberInfoEntity, String>, JpaSpecificationExecutor<WellMemberInfoEntity> {

    fun findByIdx(idx: UUID): Optional<WellMemberInfoEntity>
    fun findAll(pageable: Pageable): Page<WellMemberInfoEntity>
    fun findByName(name: String) : Optional<WellMemberInfoEntity>

}
