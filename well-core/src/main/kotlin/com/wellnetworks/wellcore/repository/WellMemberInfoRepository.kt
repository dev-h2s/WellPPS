package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellMemberInfoEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellMemberInfoRepository: CrudRepository<WellMemberInfoEntity, String> {

    fun findByName(name: String) : Optional<WellMemberInfoEntity>

}
