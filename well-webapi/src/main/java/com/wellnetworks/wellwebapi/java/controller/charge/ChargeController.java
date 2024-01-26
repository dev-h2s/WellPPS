//package com.wellnetworks.wellwebapi.java.controller.charge;
////
//
//import com.wellnetworks.wellcore.java.dto.Charge.WellChargeInsertDTO;
//import com.wellnetworks.wellcore.java.service.charge.ChargeService;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//public class ChargeController {
//    private final ChargeService chargeService;
//
//    // 변수 두개 받는 충전 체크 컨트롤러
//    @GetMapping("/check1")
//    public void check1(@Valid WellChargeInsertDTO dto) {
//        chargeService.chargeCheck(dto);
//    }
//
//
////    // 충전 시도 컨트롤러
////    // 서비스 연결
////
////    // 실제 충전 컨트롤러
////    // 서비스 연결
////
////    // 수동 충전 확인 컨트롤러
////    // 서비스 연결
//}
