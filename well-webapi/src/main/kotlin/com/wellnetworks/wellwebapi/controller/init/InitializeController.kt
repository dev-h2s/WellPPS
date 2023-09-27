package com.wellnetworks.wellwebapi.controller.init

import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.PermissionKey
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
    // 관리자 생성
    // 애플리케이션에 사용자가 아무도 등록되어 있지 않은 경우에만 실행
    @PostMapping("create_admin")
    fun createAdmin(@RequestBody createAdminReq: CreateAdminReq): ResponseEntity<BaseRes> {
        if (userService.dataTotalCount() == 0L) {
            val permissions: List<String> = listOf (
                PermissionKey.SUPER_ADMIN,
                PermissionKey.LOGIN,
            )
            val createAdmin = WellUserDTOCreate(createAdminReq.username, null, permissions,
                passwordEncoder.encode(createAdminReq.password))
            println(createAdmin)
            // 관리자 생성 실패 시 FAILED_TO_SAVE_USER
            if (userService.createUser(createAdmin) == null)
            {
                throw BaseException(BaseResponseCode.FAILED_TO_SAVE_USER)
            }
            // http OK 관리자 생성
            return ResponseEntity.ok(BaseRes(HttpStatus.OK, "Create super admin complete."))
        }
        // 생성 실패
        throw BaseException(BaseResponseCode.BAD_REQUEST)
    }

    //  시스템 하드 리셋
    @GetMapping("hard_reset_system")
    fun hardresetSystem(): ResponseEntity<BaseRes> {

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, ""))
    }
}