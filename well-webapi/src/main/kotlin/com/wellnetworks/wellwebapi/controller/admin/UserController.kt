package com.wellnetworks.wellwebapi.controller.admin

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wellnetworks.wellcore.domain.dto.WellPartnerDTO
import com.wellnetworks.wellcore.domain.dto.WellUserDTOCreate
import com.wellnetworks.wellcore.domain.dto.WellUserDTOUpdate
import com.wellnetworks.wellcore.domain.enums.MenuPermission
import com.wellnetworks.wellcore.domain.enums.MenuPermissionAction
import com.wellnetworks.wellcore.domain.enums.PermissionKey
import com.wellnetworks.wellcore.service.WellUserService
import com.wellnetworks.wellwebapi.exception.BaseException
import com.wellnetworks.wellwebapi.exception.BaseResponseCode
import com.wellnetworks.wellwebapi.request.init.CreateAdminReq
import com.wellnetworks.wellwebapi.request.init.UpdatePwd
import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import java.time.ZonedDateTime
import java.util.*

@RestController
@RequestMapping("/admin/user/")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class UserController(private var userService: WellUserService, private val passwordEncoder: PasswordEncoder) {

    @PutMapping("update_pwd")
    /*
    @PreAuthorize("@wellAuthorize.hasUserPermission('${PermissionKey.MEMBER}', '${PermissionKey.PARTNER}') or" +
            "@wellAuthorize.hasMenuPermission('${MenuPermission.MEMBER}'," +
            " '${MenuPermissionAction.UPDATE}', '${MenuPermissionAction.CREATE}')")

     */
    fun updatePwd(@RequestBody updatePwd: UpdatePwd): ResponseEntity<BaseRes> {
        try{
            if(updatePwd.type == 1){
                val updatePass = WellUserDTOUpdate(updatePwd.idx, null, null,
                    passwordEncoder.encode(updatePwd.password),null,null,
                    null,null, ZonedDateTime.now(),null)

                println(updatePass)

                if (!userService.updatePassword(updatePass))
                    throw BaseException(BaseResponseCode.FAILED_TO_SAVE_USER)

            }else if(updatePwd.type == 2){
                val tmpPassCount = (userService.getTmpPassCountByIdx(updatePwd.idx)+1).toByte()
                val updatePass = WellUserDTOUpdate(updatePwd.idx, null, null,
                   null, passwordEncoder.encode(updatePwd.password),ZonedDateTime.now().plusDays(5),
                    tmpPassCount,ZonedDateTime.now(), null,null)

                if (!userService.updatePassword(updatePass))
                    throw BaseException(BaseResponseCode.FAILED_TO_SAVE_USER)
            }


        }catch (e : Exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "업데이트 성공"))
    }

}
