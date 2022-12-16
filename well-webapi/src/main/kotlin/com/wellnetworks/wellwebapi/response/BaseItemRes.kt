package com.wellnetworks.wellwebapi.response

import org.springframework.http.HttpStatus

class BaseItemRes<T>(status: HttpStatus, message: String = "",var item: T? = null): BaseRes(status, message)