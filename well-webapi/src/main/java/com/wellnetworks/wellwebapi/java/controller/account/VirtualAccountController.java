package com.wellnetworks.wellwebapi.java.controller.account;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/account/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class VirtualAccountController {
    //개별 조회
    //상세 조회
    //리스트 조회
    //생성
    //엑셀 다운로드
    //수정
    //다중검색
    //삭제
}
