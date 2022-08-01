package com.wellnetworks.wellwebapi.response

import org.springframework.http.HttpStatus

open class BaseRes (
    var status: HttpStatus,
    var message: String
)