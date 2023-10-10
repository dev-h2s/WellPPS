package com.wellnetworks.wellsecure.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.wellnetworks.wellsecure.config.SecurityPropertieskt
import com.wellnetworks.wellsecure.request.UserLoginReqkt
import lombok.RequiredArgsConstructor
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

@RequiredArgsConstructor
class JwtAuthenticationFilterkt(
    private val authManager: AuthenticationManager,
    private val securityProperties: SecurityPropertieskt,
    private val tokenProvider: TokenProviderkt
    ): UsernamePasswordAuthenticationFilter() {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        req: HttpServletRequest,
        res: HttpServletResponse?
    ): Authentication {
        return try {
            val mapper = ObjectMapper()

            val creds = mapper.readValue(req.inputStream, UserLoginReqkt::class.java)

            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    creds.username,
                    creds.password,
                    ArrayList()
                )
            )
        } catch (e: IOException) {
            throw AuthenticationServiceException(e.message)
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain?,
        authentication: Authentication
    ) {
        val token = tokenProvider.createToken(authentication)
        res.addHeader(securityProperties.headerString, securityProperties.tokenPrefix + token)
    }
}