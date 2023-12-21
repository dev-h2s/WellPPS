//package com.wellnetworks.wellwebapi.java.exception;
//
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//// api 사용하는 클라이언트에 예외처리 코드를 제공하는 클래스
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    // IllegalArgumentException에 대한 예외 처리
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
//        // 구체적인 오류 내용을 클라이언트에게 전달합니다.
//        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), ex);
//        return new ResponseEntity<>(apiError, apiError.getStatus());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
//        // 일반적인 오류 메시지를 클라이언트에게 전달합니다.
//        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", ex);
//        return new ResponseEntity<>(apiError, apiError.getStatus());
//    }
//}