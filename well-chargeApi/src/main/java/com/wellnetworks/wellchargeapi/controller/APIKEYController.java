package com.wellnetworks.wellchargeapi.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;

// 내부 apikey관련 컨트롤러 -> 충전 모듈에 존재하는게 맞나?? 코어 모듈에 있어야된다고 생각
public class APIKEYController {

    @GetMapping("list")
    public void getAPIKEYList() {
        // 내부 apikey 내역 가져오기
    }

    @PostMapping("list")
    public void createAPIKEYList() {
        // 내부 apikey 내역 생성
    }

    @PatchMapping("list")
    public void updateAPIKEYList() {
        // 내부 apikey 내역 업데이트
    }

    @DeleteMapping("list")
    public void deleteAPIKEYList() {
        // 내부 apikey 내역 삭제
    }
}
