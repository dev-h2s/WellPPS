//package com.wellnetworks.wellwebapi.java.exception;
//
//import org.springframework.http.HttpStatus;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class ApiError {
//
//    private HttpStatus status;
//    private LocalDateTime timestamp;
//    private String message;
//    private List<String> errors;
//
//    public ApiError(HttpStatus status, String message, Throwable ex) {
//        this.timestamp = LocalDateTime.now();
//        this.status = status;
//        this.message = message;
//        this.errors = List.of(ex.getLocalizedMessage());
//    }
//
//    // Getter and setters for each field
//    public HttpStatus getStatus() {
//        return status;
//    }
//
//    public void setStatus(HttpStatus status) {
//        this.status = status;
//    }
//
//    public LocalDateTime getTimestamp() {
//        return timestamp;
//    }
//
//    public void setTimestamp(LocalDateTime timestamp) {
//        this.timestamp = timestamp;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public List<String> getErrors() {
//        return errors;
//    }
//
//    public void setErrors(List<String> errors) {
//        this.errors = errors;
//    }
//
//    // toString method for logging
//    @Override
//    public String toString() {
//        return "ApiError{" +
//                "status=" + status +
//                ", timestamp=" + timestamp +
//                ", message='" + message + '\'' +
//                ", errors=" + errors +
//                '}';
//    }
//}
