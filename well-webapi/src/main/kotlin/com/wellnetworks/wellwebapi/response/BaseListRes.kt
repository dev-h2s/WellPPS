package com.wellnetworks.wellwebapi.response

import org.springframework.http.HttpStatus

open class BaseListRes<T>(
    status: HttpStatus, message: String = "",
    var items: List<T>?,
    var currentPage: Int? = null,
    var totalItems: Long? = null,
    var totalPages: Int? = null
    ): BaseRes(status, message) {

}