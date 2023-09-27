package com.wellnetworks.wellchargeapi.controller;

import org.springframework.web.bind.annotation.*;

// 개통 정책 컨트롤러 -> 메인 모듈에 존재하도 되지 않을까?

@RestController
@RequestMapping("/open/policy/")
public class OpeningPolicyController {

    @GetMapping("opening")
    public void getAPIKEYList() {
        // 개통정책 가져오기
    }

    @PostMapping("opening")
    public void createAPIKEYList() {
        // 개통정책 생성
    }

    @PatchMapping("opening")
    public void updateAPIKEYList() {
        // 개통정책 업데이트
    }

    @DeleteMapping("opening")
    public void deleteAPIKEYList() {
        // 개통정책 삭제
    }
}
