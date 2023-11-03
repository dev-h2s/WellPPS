package com.wellnetworks.wellwebapi.java.controller.member;

import com.wellnetworks.wellcore.java.service.member.TokenResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {

    private String message;
    private TokenResponse data; // TokenResponse 클래스도 정의되어 있어야 합니다.

    // 기본 생성자
    public ApiResponse() {}

    // 메시지와 데이터를 포함하는 생성자
    public ApiResponse(String message, TokenResponse data) {
        this.message = message;
        this.data = data;
    }
}
