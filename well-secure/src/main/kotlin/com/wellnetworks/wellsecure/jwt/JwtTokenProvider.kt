package com.wellnetworks.wellsecure.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.io.File
import java.nio.charset.StandardCharsets
import java.time.ZonedDateTime
import java.util.*
import javax.annotation.PostConstruct
import javax.crypto.SecretKey
import javax.servlet.http.HttpServletRequest

@Component
class JwtTokenProvider(private val userDetailsService: UserDetailsService) {

    var SecretKeyStr: String = "vgqwUvZ_XfrsWNGtxUse_crb7@jx4F6KXbEhqNM2mPrAaRbZ6XvA8QWJYtkJzUyP"
    private lateinit var secretKey: SecretKey

    // Token Expire Time 30M
    private val tokenValidTime = 30 * 60 * 1000L

    @PostConstruct
    protected fun init() {
        println("JWT SecretKey Text: $SecretKeyStr")
        secretKey = Keys.hmacShaKeyFor(SecretKeyStr.toByteArray(StandardCharsets.UTF_8))
    }

    fun createToken(userPk: String): String {
        val claims: Claims = Jwts.claims().setSubject(userPk)
        claims["userPK"] = userPk
        val now = Date()
        return Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + tokenValidTime))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact()
    }

    fun getAuthentication(token: String): Authentication {
        val userDetails = userDetailsService.loadUserByUsername(getUserPk(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    fun getUserPk(token: String): String {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).body.subject
    }

    fun resolveToken(request: HttpServletRequest): String? {
        return request.getHeader("Authorization")
    }

    fun validateToken(jwtToken: String): Boolean {
        return try {
            val claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(jwtToken)
            !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            false
        }
    }
}