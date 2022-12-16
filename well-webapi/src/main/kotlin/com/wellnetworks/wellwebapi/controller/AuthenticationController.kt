package com.wellnetworks.wellwebapi.controller

import com.wellnetworks.wellcore.domain.dto.WellPartnerDTOSignup
import com.wellnetworks.wellcore.domain.dto.WellPermissionDTO
import com.wellnetworks.wellcore.domain.dto.WellUserDTO
import com.wellnetworks.wellcore.service.WellPartnerService
import com.wellnetworks.wellcore.service.WellUserService
import com.wellnetworks.wellsecure.jwt.JwtTokenProvider
import com.wellnetworks.wellsecure.service.WellUserDetailService
import com.wellnetworks.wellwebapi.exception.BaseException
import com.wellnetworks.wellwebapi.exception.BaseResponseCode
import com.wellnetworks.wellwebapi.request.UserLoginReq
import com.wellnetworks.wellwebapi.response.BaseListRes
import com.wellnetworks.wellwebapi.response.BaseRes
import com.wellnetworks.wellwebapi.response.UserLoginRes
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
abstract class AuthenticationController(
    private val userService: WellUserService, private val partnerService: WellPartnerService,
    private val jwtTokenProvider: JwtTokenProvider, private val passwordEncoder: PasswordEncoder) {

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
    }

    @PostMapping("signup", consumes = [ MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE])
    fun signup(@RequestBody signupPartner: WellPartnerDTOSignup, @RequestPart("file") files: List<MultipartFile>): ResponseEntity<BaseRes> {

        if (signupPartner.CEO_Name.isNullOrEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "대표자 이름이 없습니다."))

        if (signupPartner.CEO_Telephone.isNullOrEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "대표자 연락처가 없습니다."))

        if (signupPartner.Tax_Email.isNullOrEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "이메일 주소가 없습니다."))

        if (files.count() > 2)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "잘못된 파일 등록입니다."))

        if (!signupPartner.Tax_Number.isNullOrEmpty())
            if (files.count() != 2)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "사업자 등록증과 대표자 신분증이 업로드 되지 않았습니다."))

        if (partnerService.signupPartner(signupPartner, files)) {
            return ResponseEntity.ok(BaseRes(HttpStatus.OK,""))
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "시스템 오류로 등록에 실패하였습니다."))
    }

    @GetMapping("permission_list")
    @PostAuthorize("isAuthenticated()")
    fun getPermissionList(): ResponseEntity<BaseListRes<WellPermissionDTO>> {
        return ResponseEntity.ok(BaseListRes<WellPermissionDTO>(HttpStatus.OK, "",
            userService.getPermissionList()))
    }

}