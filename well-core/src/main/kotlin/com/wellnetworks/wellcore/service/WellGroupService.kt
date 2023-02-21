package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellGroupEntity
import com.wellnetworks.wellcore.domain.dto.WellGroupDTO
import com.wellnetworks.wellcore.repository.WellGroupRepository
import com.wellnetworks.wellcore.repository.WellUserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class WellGroupService {
    @Autowired
    private lateinit var wellGroupRepository: WellGroupRepository

    @Autowired
    private lateinit var wellUserRepository: WellUserRepository

    fun getGroupByIdx(groupKey: String): Optional<WellGroupDTO> {
        val group = wellGroupRepository.findByGroupPermissionKey(groupKey)
        return group.map { it.toDto() }
    }

    fun getGroup(pageable: Pageable): Page<WellGroupDTO> {
        return wellGroupRepository.findAll(pageable).map { it.toDto() }
    }

    @Transactional
    fun createGroup(group: WellGroupDTO): Boolean {
        try {
            var _groupKey = group.GroupKey.uppercase()
            if (_groupKey.indexOf("GROUP_") < 0) {
                _groupKey = "GROUP_$_groupKey"
            }

            var entity = WellGroupEntity(
                _groupKey, group.Label,
                group.GroupPermissionKeysStringList, group.Description,
            )

            var res = wellGroupRepository.save(entity)
        } catch (e: Exception) {
            return false
        }

        return true
    }

    @Transactional
    fun updateGroup(group: WellGroupDTO): Boolean {
        try {
            var _groupKey = group.GroupKey.uppercase()
            if (_groupKey.indexOf("GROUP_") < 0) {
                _groupKey = "GROUP_$_groupKey"
            }

            val currentEntity = wellGroupRepository.findByGroupPermissionKey(_groupKey).orElse(null) ?: return false

            currentEntity.updateDto(group)
            wellGroupRepository.save(currentEntity)
        } catch (e: Exception) {
            return false
        }

        return true
    }

    @Transactional
    fun deleteGroup(groupKey: String) {
        var _groupKey = groupKey.uppercase()
        if (_groupKey.indexOf("GROUP_") < 0) {
            _groupKey = "GROUP_$_groupKey"
        }

        if (wellUserRepository.countByGroupPermissionKey(_groupKey) > 0) {
            throw Exception("User exists in group.")
        }

        val res = wellGroupRepository.deleteByGroupPermissionKey(_groupKey)

        if (res.get() == 1) {
            return
        }

        throw Exception("delete count not match.")
    }
}