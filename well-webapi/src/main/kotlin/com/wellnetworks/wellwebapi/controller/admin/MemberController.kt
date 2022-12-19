package com.wellnetworks.wellwebapi.controller.admin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.treeToValue
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTOCreate
import com.wellnetworks.wellcore.domain.dto.WellUserDTOCreate
import com.wellnetworks.wellcore.service.utils.SearchCriteria
import com.wellnetworks.wellcore.service.WellMemberInfoService
import com.wellnetworks.wellwebapi.response.BaseItemRes
import com.wellnetworks.wellwebapi.response.BaseListRes
import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/admin/hr/")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class MemberController(private var memberInfoService: WellMemberInfoService) {
    @GetMapping("member/{id}")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")
    fun getMember(@PathVariable id: String): ResponseEntity<BaseItemRes<WellMemberInfoDTO>> {
        val uuidIdx: UUID
        try {
            uuidIdx = UUID.fromString(id)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseItemRes(HttpStatus.BAD_REQUEST, "문서 번호가 잘못되었습니다."))
        }

        val member = memberInfoService.getMemberByIdx(uuidIdx)

        if (member.isEmpty)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseItemRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))

        return ResponseEntity.ok(BaseItemRes(HttpStatus.OK, "", member.get()))
    }

    @GetMapping("member")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")
    fun getMemberList(
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("page", defaultValue = "0") page: Int,
    ): ResponseEntity<BaseListRes<WellMemberInfoDTO>> {
        val searchKeywords: MutableList<SearchCriteria> = mutableListOf()

        val pageable: Pageable = PageRequest.of(page, size)

        // Todo : 검색조건 추가

        val memberList = memberInfoService.searchMember(pageable, searchKeywords)

        return ResponseEntity.ok(BaseListRes<WellMemberInfoDTO>(HttpStatus.OK, "",
            memberList.content, memberList.number, memberList.totalElements, memberList.totalPages))
    }

    @PostMapping("member")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")
    fun createMember(@RequestBody objUserPartner: ObjectNode): ResponseEntity<BaseRes> {
        val mapper = ObjectMapper()

        try {
            val user = mapper.treeToValue<WellUserDTOCreate>(objUserPartner.get("user"))
            val member = mapper.treeToValue<WellMemberInfoDTOCreate>(objUserPartner.get("member"))

            if (!memberInfoService.createMember(user, member))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "맴버 추가 실패"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "맴버 추가 성공"))
    }

    @PutMapping("member")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")
    fun updateMember(@RequestBody member: WellMemberInfoDTO): ResponseEntity<BaseRes> {
        if (!memberInfoService.updateMember(member))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트 실패"))

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "업데이트 성공"))
    }
}