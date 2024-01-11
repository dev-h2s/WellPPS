package com.wellnetworks.wellwebapi.java.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

    public static ResponseEntity<?> createOkResponse(Object items, String message, Page<?> page) {
        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", page.getNumber());
        response.put("items", items);
        response.put("message", message);
        response.put("status", "OK");
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        return ResponseEntity.ok(response);
    }

    public static ResponseEntity<?> createErrorResponse(Exception e) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ERROR");
        response.put("message", "서버 오류 발생: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
