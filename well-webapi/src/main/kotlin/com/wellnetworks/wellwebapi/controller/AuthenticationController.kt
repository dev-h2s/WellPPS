package com.wellnetworks.wellwebapi.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wellnetworks.wellcore.domain.dto.WellPartnerDTOCreate
import com.wellnetworks.wellcore.domain.dto.WellPartnerDTOSignup
import com.wellnetworks.wellcore.domain.dto.WellPermissionDTO
import com.wellnetworks.wellcore.domain.dto.WellUserDTOCreate
import com.wellnetworks.wellcore.service.WellPartnerService
import com.wellnetworks.wellcore.service.WellUserService
import com.wellnetworks.wellwebapi.response.BaseListRes
import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*
import kotlin.math.sign

@RestController
@RequestMapping("/")
class AuthenticationController(
    private val userService: WellUserService, private val partnerService: WellPartnerService,
    private val passwordEncoder: PasswordEncoder) {
/*
    @PostMapping("login")
    fun login(@RequestBody userLoginReq: UserLoginReq): ResponseEntity<UserLoginRes> {
        if (!userService.existByUserID(userLoginReq.username)) {
            throw BaseException(BaseResponseCode.USER_NOT_FOUND)
        }

        val user: Optional<WellUserDTO> = userService.getUserByUserID(userLoginReq.username)

        if (!passwordEncoder.matches(userLoginReq.password, user.get().Password_Hash)) {
            throw BaseException(BaseResponseCode.INVALID_PASSWORD)
        }

        val userLoginRes: UserLoginRes = UserLoginRes(HttpStatus.OK, "", jwtTokenProvider.createToken(userLoginReq.username))
        return ResponseEntity.ok(userLoginRes)
    }*/

    @PostMapping("signup", consumes = [ MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE])
    fun signup(@RequestPart("user") userJsonString: String,
               @RequestPart("signup") signupJsonString: String,
               @RequestPart("file") files: List<MultipartFile>
    ): ResponseEntity<BaseRes> {

        val mapper = jacksonObjectMapper()

        try {
            val user = mapper.readValue(userJsonString, WellUserDTOCreate::class.java)
            val signup = mapper.readValue(signupJsonString, WellPartnerDTOSignup::class.java)

            if (signup.CEO_Name.isNullOrEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "대표자 이름이 없습니다."))

            if (signup.CEO_Telephone.isNullOrEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "대표자 연락처가 없습니다."))

            if (signup.Tax_Email.isNullOrEmpty())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "이메일 주소가 없습니다."))

            if (files.count() > 2)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "잘못된 파일 등록입니다."))

            if (!signup.Tax_Number.isNullOrEmpty())
                if (files.count() != 2)
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "사업자 등록증과 대표자 신분증이 업로드 되지 않았습니다."))

            if (!partnerService.signupPartner(user, signup, files))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입 요청 실패"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }
/*
        if (partnerJsonString.CEO_Name.isNullOrEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "대표자 이름이 없습니다."))

        if (partnerJsonString.CEO_Telephone.isNullOrEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "대표자 연락처가 없습니다."))

        if (partnerJsonString.Tax_Email.isNullOrEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "이메일 주소가 없습니다."))

        if (files.count() > 2)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "잘못된 파일 등록입니다."))

        if (!partnerJsonString.Tax_Number.isNullOrEmpty())
            if (files.count() != 2)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "사업자 등록증과 대표자 신분증이 업로드 되지 않았습니다."))

        if (partnerService.signupPartner(partnerJsonString, files)) {
            return ResponseEntity.ok(BaseRes(HttpStatus.OK,""))
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "시스템 오류로 등록에 실패하였습니다."))
        */

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "회원가입 요청 성공"))
    }

    @GetMapping("permission_list")
    @PostAuthorize("isAuthenticated()")
    fun getPermissionList(): ResponseEntity<BaseListRes<WellPermissionDTO>> {
        return ResponseEntity.ok(BaseListRes<WellPermissionDTO>(HttpStatus.OK, "",
            userService.getPermissionList()))
    }

}