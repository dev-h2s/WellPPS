package com.wellnetworks.wellcore.service

import com.wellnetworks.wellcore.domain.WellGroupEntity
import com.wellnetworks.wellcore.domain.dto.WellGroupDTO
import com.wellnetworks.wellcore.repository.WellGroupRepository
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

    fun getGroupByIdx(idx: String): Optional<WellGroupDTO> {
        val group = wellGroupRepository.findByIdx(idx)
        return group.map { it.toDto() }
    }

    fun getGroup(pageable: Pageable): Page<WellGroupDTO> {
        return wellGroupRepository.findAll(pageable).map { it.toDto() }
    }

    @Transactional
    fun createGroup(group: WellGroupDTO): Boolean {
        try {
            var uuidGroup = UUID.randomUUID().toString().uppercase()

            var entity = WellGroupEntity(
                uuidGroup, group.Label, group.PermissionKeysStringList, group.Description,
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
            val currentEntity = wellGroupRepository.findByIdx(group.Idx.toString().uppercase()).orElse(null) ?: return false

            currentEntity.updateDto(group)
            wellGroupRepository.save(currentEntity)
        } catch (e: Exception) {
            return false
        }

        return true
    }

    @Transactional
    fun deleteGroup(idx: String) {
        val res = wellGroupRepository.deleteByIdx(idx.uppercase())

        if (res.get() == 1) {
            return
        }

        throw Exception("delete count not match.")
    }
}