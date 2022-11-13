package com.wellnetworks.wellwebapi.controller

import com.wellnetworks.wellcore.domain.dto.WellMemberInfoCreateDTO
import com.wellnetworks.wellcore.service.WellMemberInfoService
import com.wellnetworks.wellwebapi.exception.BaseException
import com.wellnetworks.wellwebapi.exception.BaseResponseCode
import com.wellnetworks.wellwebapi.request.RegisterMemberReq
import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime
import java.util.*

@RestController
@RequestMapping
class MemberRegisterController(private val memberInfoService: WellMemberInfoService) {
    @PostMapping("registerMember")
    fun registerMember(@RequestBody registerMemberReq: RegisterMemberReq): ResponseEntity<BaseRes>{

        /**
         * RequestBody 유효성 검사
         * */

        val createMember = WellMemberInfoCreateDTO(
            UUID.randomUUID(),"",emptyList(),registerMemberReq.name,"","",
        "","",emptyList(),emptyList(),emptyList(),"","",emptyList(),"","",
            emptyList(),emptyList(),phone_cert = false,email_cert = false,ZonedDateTime.now(),ZonedDateTime.now(),emptyList(),
            true,"", ZonedDateTime.now(), ZonedDateTime.now())
            println(createMember)

        if (memberInfoService.createMember(createMember) == null) {
            throw BaseException(BaseResponseCode.FAILED_TO_SAVE_USER)
        }
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "register Member OK"))
    }
}