package com.wellnetworks.wellsecure.service

import com.wellnetworks.wellcore.domain.dto.WellGroupDTO
import com.wellnetworks.wellcore.repository.WellGroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Component
import java.util.*

@Component
@ComponentScan("com.wellnetworks.wellcore.repository")
class WellGroupSecurityService {
    @Autowired
    private lateinit var wellGroupRepository: WellGroupRepository

    fun getGroupByIdx(groupKey: String): Optional<WellGroupDTO> {
        val group = wellGroupRepository.findByGroupPermissionKey(groupKey)
        return group.map { it.toDto() }
    }
}