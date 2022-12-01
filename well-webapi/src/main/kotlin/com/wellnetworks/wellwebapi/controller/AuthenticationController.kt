package com.wellnetworks.wellwebapi.controller

import com.wellnetworks.wellcore.domain.dto.WellUserDTO
import com.wellnetworks.wellcore.service.WellUserService
import com.wellnetworks.wellsecure.jwt.JwtTokenProvider
import com.wellnetworks.wellsecure.service.WellUserDetailService
import com.wellnetworks.wellwebapi.exception.BaseException
import com.wellnetworks.wellwebapi.exception.BaseResponseCode
import com.wellnetworks.wellwebapi.request.UserLoginReq
import com.wellnetworks.wellwebapi.response.UserLoginRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/")
class AuthenticationController(private val userService: WellUserService, private val jwtTokenProvider: JwtTokenProvider, private val passwordEncoder: PasswordEncoder) {
    //@PostMapping("/register")
    //fun register(@RequestBody userRegisterReq: UserRegisterReq): ResponseEntity<UserRegisterRes> {

    //}

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
}