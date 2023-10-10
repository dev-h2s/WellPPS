package com.wellnetworks.wellsecure.request

import com.fasterxml.jackson.annotation.JsonProperty

data class UserLoginReqkt (
    @JsonProperty("username")
    val username: String,
    @JsonProperty("password")
    val password: String)
// JSON {"username":"", "password":""}
