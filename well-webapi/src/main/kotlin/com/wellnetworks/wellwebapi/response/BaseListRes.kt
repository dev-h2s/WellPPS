package com.wellnetworks.wellwebapi.response

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus

open class BaseListRes<T>(
    status: HttpStatus, message: String = "",
    @JsonProperty("items")
    val items: List<T>?,
    @JsonProperty("currentPage")
    val currentPage: Int? = null,
    @JsonProperty("totalItems")
    val totalItems: Long? = null,
    @JsonProperty("totalPages")
    val totalPages: Int? = null
    ): BaseRes(status, message) {

}