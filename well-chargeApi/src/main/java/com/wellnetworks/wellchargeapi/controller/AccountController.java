package com.wellnetworks.wellchargeapi.controller;

import org.springframework.web.bind.annotation.*;

// 가상계좌 관련 컨트롤러

@RestController
@RequestMapping("/account/")
public class AccountController {

    @GetMapping("list")
    public void getAccountList() {
        // 가상계좌 내역 가져오기
    }

    @PostMapping("list")
    public void createAccountList() {
        // 가상계좌 내역 생성
    }

    @PatchMapping("list")
    public void updateAccountList() {
        // 가상계좌 내역 업데이트
    }

    @DeleteMapping("list")
    public void deleteAccountList() {
        // 가상계좌 내역 삭제
    }

}
