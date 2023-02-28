package com.wellnetworks.wellwebapi.controller.admin

import com.wellnetworks.wellcore.service.WellUserService
import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/admin/user/")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class UserController(private var userService: WellUserService) {

    @PutMapping("temp_pwd/{id}")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")
    fun updateTempPwd(@PathVariable id: String) : ResponseEntity<BaseRes> {
        val uuidIdx: String

        try{
            uuidIdx = UUID.fromString(id).toString()
        }catch (e: java.lang.Exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "문서 번호가 잘못되었습니다."))
        }

        try {
            //userService.updateTempPwdById(uuidIdx)
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "delete ok"))
    }
}