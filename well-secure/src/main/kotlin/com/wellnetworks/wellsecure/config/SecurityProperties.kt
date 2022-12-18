package com.wellnetworks.wellsecure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

@ConfigurationProperties(prefix = "jwt-security")
@Validated
class SecurityProperties {
    @field:NotBlank
    @field:Size(min = 64)
    var secret = "vgqwUvZ_XfrsWNGtxUse_crb7@jx4F6KXbEhqNM2mPrAaRbZ6XvA8QWJYtkJzUyP"

    @field:Positive
    var expirationTime: Int = 31 // in Days

    @field:Positive
    var strength = 10

    val tokenPrefix = "Bearer "
    val headerString = "Authorization"
}