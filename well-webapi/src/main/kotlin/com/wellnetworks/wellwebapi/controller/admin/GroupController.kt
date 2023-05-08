package com.wellnetworks.wellwebapi.controller.admin

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wellnetworks.wellcore.domain.dto.WellGroupDTO
import com.wellnetworks.wellcore.domain.dto.WellPartnerDTO
import com.wellnetworks.wellcore.domain.dto.WellPermissionDTO
import com.wellnetworks.wellcore.domain.enums.*
import com.wellnetworks.wellcore.service.WellGroupService
import com.wellnetworks.wellwebapi.response.BaseItemRes
import com.wellnetworks.wellwebapi.response.BaseListRes
import com.wellnetworks.wellwebapi.response.BaseRes
import com.wellnetworks.wellwebapi.response.ParamEnumItemRes
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/hr/")
class GroupController(private var groupService: WellGroupService) {

    val menuPermissionListAll = MenuPermissionUtil.BuildPermissionStringAll()


    @GetMapping("group")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")
    fun getGroupPermissionList(
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("page", defaultValue = "0") page: Int,
    ): ResponseEntity<BaseListRes<WellGroupDTO>> {
        val pageable: Pageable = PageRequest.of(page, size)

        val groupList = groupService.getGroup(pageable)

        return ResponseEntity.ok(BaseListRes(HttpStatus.OK, "",
            groupList.content, groupList.number, groupList.totalElements, groupList.totalPages))
    }

    @PostMapping("group")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")
    fun createGroupPermission(@RequestPart("group") group: String): ResponseEntity<BaseRes> {
        val mapper = jacksonObjectMapper()

        try {
            val groupObj = mapper.readValue(group, WellGroupDTO::class.java)

            for (groupPermissionKeysString in groupObj.GroupPermissionKeysStringList) {

                if (!menuPermissionListAll.contains(groupPermissionKeysString)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."))
                }
            }

            if (!groupService.createGroup(groupObj))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "그룹 추가 실패"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "그룹 추가 성공"))
    }

    @PutMapping("group")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")
    fun updateGroupPermission(@RequestPart("group") group: String): ResponseEntity<BaseRes> {
        val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())

        try {
            val groupObj = mapper.readValue(group, WellGroupDTO::class.java)

            for (groupPermissionKeysString in groupObj.GroupPermissionKeysStringList) {
                if (!menuPermissionListAll.contains(groupPermissionKeysString)) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."))
                }
            }

            if (!groupService.updateGroup(groupObj))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트 실패"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseRes(HttpStatus.NOT_FOUND, "$"))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "업데이트 성공"))
    }

    @DeleteMapping("group/{gkey}")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")
    fun deleteGroupPermission(@PathVariable gkey: String): ResponseEntity<BaseRes> {
        try {
            groupService.deleteGroup(gkey);
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "삭제 성공"))
    }

    @GetMapping("group/actionList")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")
    fun menuAction(): ResponseEntity<BaseListRes<ParamEnumItemRes>> {
        var menuActionList : MutableList<ParamEnumItemRes> = mutableListOf()
        for (item in ActionList.values()) {
            menuActionList.add(
                ParamEnumItemRes(
                item.index(),
                item.key,
                item.toString()
            )
            )
        }

        return ResponseEntity.ok(
            BaseListRes<ParamEnumItemRes>(
                HttpStatus.OK, "ActionList",
                menuActionList,
            )
        )
    }

    @GetMapping("group/menuList")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")
    fun menuPermission(): ResponseEntity<BaseListRes<ParamEnumItemRes>> {
        var menuPermissionList : MutableList<ParamEnumItemRes> = mutableListOf()
        for (item in PermissionList.values()) {
            menuPermissionList.add(
                ParamEnumItemRes(
                    item.index(),
                    item.key,
                    item.toString()
                )
            )
        }

        return ResponseEntity.ok(
            BaseListRes<ParamEnumItemRes>(
                HttpStatus.OK, "MenuList",
                menuPermissionList,
            )
        )
    }
}