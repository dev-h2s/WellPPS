package com.wellnetworks.wellwebapi.controller.init

import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.PermissionList
import com.wellnetworks.wellcore.service.WellUserService
import com.wellnetworks.wellwebapi.exception.BaseException
import com.wellnetworks.wellwebapi.exception.BaseResponseCode
import com.wellnetworks.wellwebapi.request.init.CreateAdminReq
import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime
import java.util.*

@RestController
@RequestMapping("/init")
class InitializeController(private val userService: WellUserService, private val passwordEncoder: PasswordEncoder) {
    @PostMapping("create_admin")
    fun createAdmin(@RequestBody createAdminReq: CreateAdminReq): ResponseEntity<BaseRes> {
        if (userService.dataTotalCount() == 0L) {
            val permissions: List<String> = listOf (
                PermissionList.PERMISSION_SUPERADMIN.PermitssionKey,
                PermissionList.PERMISSION_LOGIN.PermitssionKey,
            )
            val createAdmin = WellUserDTOCreate(createAdminReq.username, permissions,
                passwordEncoder.encode(createAdminReq.password))
            println(createAdmin)

            if (userService.createUser(createAdmin) == null)
            {
                throw BaseException(BaseResponseCode.FAILED_TO_SAVE_USER)
            }
            return ResponseEntity.ok(BaseRes(HttpStatus.OK, "Create super admin complete."))
        }

        throw BaseException(BaseResponseCode.BAD_REQUEST)
    }

    @GetMapping("hard_reset_system")
    fun hardresetSystem(): ResponseEntity<BaseRes> {

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, ""))
    }
}