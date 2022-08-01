package com.wellnetworks.wellwebapi.exception

import org.springframework.http.HttpStatus

enum class BaseResponseCode(status: HttpStatus, message: String) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "인증 오류"),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "중복 아이디"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "잘못된 계정 정보"),
    FAILED_TO_SAVE_USER(HttpStatus.NOT_FOUND, "사용자정보 갱신 실패"),
    OK(HttpStatus.OK, "요청 성공");

    public val status: HttpStatus = status
    public val message: String = message
}