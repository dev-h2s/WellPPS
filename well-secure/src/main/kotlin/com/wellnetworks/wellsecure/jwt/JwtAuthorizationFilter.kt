package com.wellnetworks.wellsecure.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.wellnetworks.wellcore.repository.WellUserRepository
import com.wellnetworks.wellsecure.config.SecurityProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.stereotype.Component
import java.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlin.jvm.optionals.getOrNull

class JwtAuthorizationFilter(
    authManager: AuthenticationManager,
    private val securityProperties: SecurityProperties,
    private val tokenProvider: TokenProvider
): BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(securityProperties.headerString)
        if (header == null || !header.startsWith(securityProperties.tokenPrefix)) {
            chain.doFilter(req, res)
            return
        }

        println("header : $header")
        tokenProvider.getAuthentication(header)?.also { authentication ->
            SecurityContextHolder.getContext().authentication = authentication
        }

        chain.doFilter(req, res)
    }
}