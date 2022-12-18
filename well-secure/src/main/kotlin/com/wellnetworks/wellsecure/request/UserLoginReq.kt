package com.wellnetworks.wellsecure.request

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Getter
import lombok.Setter

data class UserLoginReq (
    @JsonProperty("username")
    val username: String,
    @JsonProperty("password")
    val password: String)
// JSON {"username":"", "password":""}
