package com.wellnetworks.wellsecure.jwt

import org.springframework.security.core.userdetails.UserDetailsService
import java.util.*

class JwtTokenProvider(private val userDetailsService: UserDetailsService) {
    private var secretKey = ""

    // Token Expire Time 30M
    private val tokenValidTime = 30 * 60 * 1000L

    protected fun init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.toByteArray())
    }
}