package com.wellnetworks.wellchargeapi.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/charge/")
public class ChargeController {
    @GetMapping("list")
    public void getChargeList() {
        // 충전내역 가져오기
    }

    @PostMapping("list")
    public void createChargeList() {
        // 충전내역 입력
    }

    @PatchMapping("list")
    public void updateChargeList() {
        // 충전내역 수정
    }

    @DeleteMapping("list")
    public void deleteChargeList() {
        // 충전내역 삭제
    }
}
