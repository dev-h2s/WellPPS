package com.wellnetworks.wellwebapi.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus

open class BaseRes (
    @JsonProperty("status")
    val status: HttpStatus,
    @JsonProperty("message")
    val message: String
)