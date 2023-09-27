package com.wellnetworks.wellchargeapi.controller;
// 예치금 관련 컨트롤러

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diposit/")
public class DipositController {

    @GetMapping("list")
    public void getDipositList() {
        // 예치금 내역 가져오기
    }

    @PostMapping("list")
    public void createDipositList() {
        // 예치금 내역 생성
    }

    @PatchMapping("list")
    public void updateDipositList() {
        // 예치금 내역 업데이트
    }

    @DeleteMapping("list")
    public void deleteDipositList() {
        // 예치금 내역 삭제
    }
}
