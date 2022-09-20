package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellMemberInfo
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellMemberInfoRepository: CrudRepository<WellMemberInfo, String> {

    fun findByName(name: String) : Optional<WellMemberInfo>

}
