package com.wellnetworks.wellwebapi.controller

import com.wellnetworks.wellcore.domain.RuleTypes
import com.wellnetworks.wellcore.domain.dto.WellUserCreateDTO
import com.wellnetworks.wellcore.service.WellUserService
import com.wellnetworks.wellsecure.jwt.JwtTokenProvider
import com.wellnetworks.wellwebapi.exception.BaseException
import com.wellnetworks.wellwebapi.exception.BaseResponseCode
import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.ZonedDateTime

@RestController
@RequestMapping("/")
class InitializeController(private val userService: WellUserService, private val jwtTokenProvider: JwtTokenProvider, private val passwordEncoder: PasswordEncoder) {
    @GetMapping("system_init")
    fun systemInit(): ResponseEntity<BaseRes> {
        if (userService.dataTotalCount() == 0L) {
            val createAdmin = WellUserCreateDTO("admin", RuleTypes.MEMBER, emptyList(), passwordEncoder.encode("admin"), ZonedDateTime.now(), ZonedDateTime.now())
            println(createAdmin)

            if (userService.createUser(createAdmin) == null)
            {
                throw BaseException(BaseResponseCode.FAILED_TO_SAVE_USER)
            }
            return ResponseEntity.ok(BaseRes(HttpStatus.OK, "Initialize Complete."))
        }

        throw BaseException(BaseResponseCode.BAD_REQUEST)
    }
}