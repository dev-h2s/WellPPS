package com.wellnetworks.wellcore.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wellnetworks.wellcore.domain.WellGroupEntity
import com.wellnetworks.wellcore.domain.dto.WellGroupDTO
import com.wellnetworks.wellcore.domain.enums.MenuPermissionAction
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

    // idx에 해당하는 그룹 가져오기
    fun getGroupByIdx(groupKey: String): Optional<WellGroupDTO> {
        val group = wellGroupRepository.findByGroupPermissionKey(groupKey)
        return group.map { it.toDto() }
    }

    // 그룹 페이지
    fun getGroup(pageable: Pageable): Page<WellGroupDTO> {
        return wellGroupRepository.findAll(pageable).map { it.toDto() }
    }

    //그룹 생성
    @Transactional
    fun createGroup(group: WellGroupDTO): Boolean {
        try {
            var _groupKey = group.GroupKey.uppercase()
            // 그룹 키가 "GROUP_"로 시작하지 않으면 앞에 "GROUP_"를 붙여줍니다.
            if (_groupKey.indexOf("GROUP_") < 0) {
                _groupKey = "GROUP_$_groupKey"
            }
            // WellGroupEntity 객체를 생성하여 그룹 정보를 설정
            var entity = WellGroupEntity(
                _groupKey, group.Label,
                group.GroupPermissionKeysStringList, group.Description,
            )
            // 그룹 정보를 저장
            var res = wellGroupRepository.save(entity)
        } catch (e: Exception) {
            return false
        }
        // 그룹 생성이 성공적으로 완료되면 true를 반환
        return true
    }

    // 그룹 업데이트
    @Transactional
    fun updateGroup(group: WellGroupDTO): Boolean {
        try {
            var _groupKey = group.GroupKey.uppercase()
            // 그룹 키가 "GROUP_"로 시작하지 않으면 앞에 "GROUP_"를 붙여줍니다.
            if (_groupKey.indexOf("GROUP_") < 0) {
                _groupKey = "GROUP_$_groupKey"
            }
            // 조회된 엔티티가 없을 경우 NULL반환, 엔티티 존재 시 다음 단계 진행
            val currentEntity = wellGroupRepository.findByGroupPermissionKey(_groupKey).orElse(null) ?: return false
            // 그룹 업데이트
            currentEntity.updateDto(group)
            // 그룹 정보를 저장
            wellGroupRepository.save(currentEntity)
        } catch (e: Exception) {
            return false
        }

        return true
    }

    // 그룹 삭제
    @Transactional
    fun deleteGroup(groupKey: String) {
        var _groupKey = groupKey.uppercase()
        // 그룹 키가 "GROUP_"로 시작하지 않으면 앞에 "GROUP_"를 붙여줍니다.
        if (_groupKey.indexOf("GROUP_") < 0) {
            _groupKey = "GROUP_$_groupKey"
        }
        // 그룹에 속한 사용자가 있는지 확인하고, 사용자가 있다면 예외를 던집니다.
        if (wellUserRepository.countByGroupPermissionKey(_groupKey) > 0) {
            throw Exception("User exists in group.")
        }
        // 그룹 엔티티를 삭제하고, 삭제된 개수를 확인합니다.
        val res = wellGroupRepository.deleteByGroupPermissionKey(_groupKey)
        // 삭제된 개수가 1이라면 삭제가 성공한 것으로 처리합니다.
        if (res.get() == 1) {
            return
        }
        // 삭제된 개수와 일치하지 않으면 예외를 던집니다.
        throw Exception("delete count not match.")
    }

    // 메뉴 권한 액션 목록을 반환하는 함수
    fun getMenuPermissionAction(): List<String> {
        val mapper = jacksonObjectMapper()
        return MenuPermissionAction.asMap().values.toString().map { it.toString() };
    }
}