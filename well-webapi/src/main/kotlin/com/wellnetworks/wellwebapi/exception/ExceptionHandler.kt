package com.wellnetworks.wellwebapi.exception

import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(BaseException::class)
    protected fun handleBaseException(e: BaseException): ResponseEntity<BaseRes> {
        return ResponseEntity.status(e.baseResponseCode.status)
            .body(BaseRes(e.baseResponseCode.status, e.baseResponseCode.message))
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    protected fun handleMissingRequestBody(e: HttpMessageNotReadableException): ResponseEntity<BaseRes> {
        return ResponseEntity.badRequest().body(BaseRes(HttpStatus.BAD_REQUEST, e.message.toString()))
    }
}