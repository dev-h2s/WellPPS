package com.wellnetworks.wellchargeapi.controller;

import org.springframework.web.bind.annotation.*;

// 충전 정책 컨트롤러
@RestController
@RequestMapping("/charge/policy/")
public class ChargingPolicyController {

    @GetMapping("charging")
    public void getAPIKEYList() {
        // 충전 정책 가져오기
    }

    @PostMapping("charging")
    public void createAPIKEYList() {
        // 충전 정책 생성
    }

    @PatchMapping("charging")
    public void updateAPIKEYList() {
        // 충전 정책 업데이트
    }

    @DeleteMapping("charging")
    public void deleteAPIKEYList() {
        // 충전 정책 삭제
    }

}
