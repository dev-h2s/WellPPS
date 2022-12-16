package com.wellnetworks.wellcore.repository

import com.wellnetworks.wellcore.domain.WellPermissionEntity
import org.springframework.data.repository.CrudRepository

interface WellPermissionRepository: CrudRepository<WellPermissionEntity, String> {

}