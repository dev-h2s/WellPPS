package com.wellnetworks.wellsecure.jwt

import com.wellnetworks.wellsecure.config.SecurityPropertieskt
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class JwtAuthorizationFilterkt(
    authManager: AuthenticationManager,
    private val securityProperties: SecurityPropertieskt,
    private val tokenProvider: TokenProviderkt
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