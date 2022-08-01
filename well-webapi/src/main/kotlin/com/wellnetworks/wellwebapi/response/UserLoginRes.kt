package com.wellnetworks.wellwebapi.response

import org.springframework.http.HttpStatus

class UserLoginRes(status: HttpStatus, message: String, var token: String): BaseRes(status, message)