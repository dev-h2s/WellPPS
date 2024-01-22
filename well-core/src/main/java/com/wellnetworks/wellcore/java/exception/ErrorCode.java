package com.wellnetworks.wellcore.java.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "INVALID INPUT VALUE"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "RESOURCE NOT FOUND"); // 세미콜론으로 수정

    private final HttpStatus status;
    private final String message;
}
