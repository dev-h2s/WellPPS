package com.wellnetworks.wellwebapi.exception

import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(value = [RuntimeException::class])    //Runtime Error 터지면 아무거나 다잡음 -> 지정하지 않았는데 처리 되었음 -> Advice 명시 해주거나, 정말 표준적인 것만 처리
    fun exception(e: RuntimeException): ResponseEntity<BaseRes> {
        val errorMessage = BaseRes(
            HttpStatus.NOT_FOUND,
            e.message ?: ""
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<BaseRes> {

        val errorMessage = BaseRes(
            HttpStatus.NOT_FOUND,
            ex.message ?: ""
        )
        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler
    fun handleArticleNotFoundException(ex: ArticleNotFoundException): ResponseEntity<BaseRes> {

        val errorMessage = BaseRes(
            HttpStatus.NOT_FOUND,
            ex.message ?: ""
        )
        return ResponseEntity(errorMessage, HttpStatus.NOT_FOUND)
    }
}

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