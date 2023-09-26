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
// passwordEncoder : 비밀번호 인코딩(해싱)을 수행하는 데 사용되는 기능
class UserController(private var userService: WellUserService, private val passwordEncoder: PasswordEncoder) {

    @PutMapping("update_pwd")
    /*
    @PreAuthorize("@wellAuthorize.hasUserPermission('${PermissionKey.MEMBER}', '${PermissionKey.PARTNER}') or" +
            "@wellAuthorize.hasMenuPermission('${MenuPermission.MEMBER}'," +
            " '${MenuPermissionAction.UPDATE}', '${MenuPermissionAction.CREATE}')")

     */
    // 비밀번호 업데이트 기능을 처리하는 메서드
    fun updatePwd(@RequestBody updatePwd: UpdatePwd): ResponseEntity<BaseRes> {
        try{
            if(updatePwd.type == 1){
                // type이 1인 경우, 비밀번호를 업데이트
                // 새로운 비밀번호를 암호화하고 업데이트
                val updatePass = WellUserDTOUpdate(updatePwd.idx, null, null,
                    passwordEncoder.encode(updatePwd.password),null,null,
                    null,null, ZonedDateTime.now(),null)

                println(updatePass)

                // 업데이트 실패
                if (!userService.updatePassword(updatePass))
                    throw BaseException(BaseResponseCode.FAILED_TO_SAVE_USER)


            }else if(updatePwd.type == 2){
                // type이 2인 경우, 임시 비밀번호를 업데이트
                // 임시 비밀번호 횟수를 증가시키고, 새로운 비밀번호를 암호화하고 업데이트
                val tmpPassCount = (userService.getTmpPassCountByIdx(updatePwd.idx)+1).toByte()
                val updatePass = WellUserDTOUpdate(updatePwd.idx, null, null,
                   null, passwordEncoder.encode(updatePwd.password),ZonedDateTime.now().plusDays(5),
                    tmpPassCount,ZonedDateTime.now(), null,null)

                //업데이트 실패
                if (!userService.updatePassword(updatePass))
                    throw BaseException(BaseResponseCode.FAILED_TO_SAVE_USER)
            }

        // 예외가 발생한 경우, 클라이언트에 오류 응답을 반환
        }catch (e : Exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }
        // 비밀번호 업데이트가 성공한 경우, 성공 응답을 반환
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "업데이트 성공"))
    }

}
