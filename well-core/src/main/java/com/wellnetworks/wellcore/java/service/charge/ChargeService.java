//package com.wellnetworks.wellcore.java.service.charge;
//
//import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
//import com.wellnetworks.wellcore.java.dto.Charge.WellChargeInsertDTO;
//import com.wellnetworks.wellcore.java.repository.operator.WellOperatorRepository;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class ChargeService {
//    private final WellOperatorRepository operatorRepository;
//
//    @Transactional
//    public void chargeCheck(WellChargeInsertDTO dto) {
//        String uniqueKey = System.currentTimeMillis() + UUID.randomUUID().toString(); // 고유값
//
//        //1. 통신사 외부 api연동관리의 직접연동 true 인지 확인(통신사들 전부 조회) -> for문으로 전체 WellOperatorEntity를 조회해서 통신사의 isExternalApiFlag가 true인 값, 즉 통신사의 isExternalApiFlag값))
//        // 1 기능
//        List<WellOperatorEntity> operatorEntities = new ArrayList<>();
//        for (WellOperatorEntity operator : operatorRepository.findAll()) {
//            if (operator.getIsExternalApiFlag() && isChannelNameTrue(operator)) {
//                for (WellOperatorEntity operator : operatorEntities) {
//                    // 여기서 필요한 비즈니스 로직을 처리하고 리턴값을 생성
//                    if (isConditionSatisfied(operator)) {
//                        if (operator.getIsRunFlag()) {
//                            // 해당 통신사의 isRunFlag가 true일 경우에만 처리
//                            switch (operator.getOperatorName()) {
//                                case "통신사1": // 통신사1에 대한 처리
//                                    // 특정 작업 수행
//                                    // ...
//                                    // 리턴 코드 설정
//                                    String channelName1 = operator.getOperatorName(); // 통신사명으로 변경
//                                    operator.setRunFlag(false); // isRunFlag를 false로 변경
//                                    // externalCode(요금제코드)가 일반요금제인지 정액요금제인지 확인
//                                    if (isGeneralFee(operator)) {
//                                        return ChargeResult.success(operator.getExternalCode(), channelName1, "요금제1", "일반요금제", 1000); // 예시 리턴
//                                    } else if (isFixedFee(operator)) {
//                                        return ChargeResult.success(operator.getExternalCode(), channelName1, "요금제1", "정액요금제", 2000); // 예시 리턴
//                                    }
//                                    foundEligibleOperator = true;
//                                    break;
//                                case "통신사2": // 통신사2에 대한 처리
//                                    // 특정 작업 수행
//                                    // ...
//                                    // 리턴 코드 설정
//                                    String channelName2 = operator.getOperatorName(); // 통신사명으로 변경
//                                    operator.setIsRunFlag(false); // isRunFlag를 false로 변경
//                                    // externalCode(요금제코드)가 일반요금제인지 정액요금제인지 확인
//                                    if (isGeneralFee(operator)) {
//                                        return ChargeResult.success(operator.getExternalCode(), channelName2, "요금제2", "일반요금제", 1500); // 예시 리턴
//                                    } else if (isFixedFee(operator)) {
//                                        return ChargeResult.success(operator.getExternalCode(), channelName2, "요금제2", "정액요금제", 2500); // 예시 리턴
//                                    }
//                                    foundEligibleOperator = true;
//                                    break;
//                                // 다른 통신사들에 대한 처리도 추가
//                                default:
//                                    // 처리할 통신사가 없는 경우
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
////       2.  switch ( switch문 사용하여 for문으로 돌린 통신사들의 isExternalApiFlag값이 true이면서 channelName이 true인 값을 조회)
////        하나라도 조건에 만족하면 조건에 만족하는 통신사 조회 시작 이후 리턴코드가 일치하면 channelName을 통신사명으로 변경하고 WellOperatorEntity의 isRunFlag를 false로 변경(하나라도 조건 맞을 경우 나머지 api종료)
////        WellOperatorEntity의 externalCode(요금제코드)가 일반요금제인지 정액요금제인지 확인
////        3. 이후 요금제코드, 통신사명, 요금제명, 요금제타입, 충전금액값 리턴
////        4. 만약 하나도 조건에 만족하지 않으면 외부 api 조회 실패 리턴
//
//        // 2 기능
//
//
//        // 3 기능
//        // 4 기능
//    }
//
//    private boolean isChannelNameTrue(WellOperatorEntity operator) {
//        // channel은 일단 true로 고정
//        return true;
//    }
//
//    private boolean isConditionSatisfied(WellOperatorEntity operator) {
//        // 여기에 조건을 추가하여 조건에 맞는지 확인
//        // 예를 들어, operator의 특정 속성을 비교하거나 조건을 검사할 수 있습니다.
//        // 조건에 맞으면 true를 반환하고, 그렇지 않으면 false를 반환합니다.
//        return true; // 임시로 true로 설정
//    }
//
//
////    public void chargeCheck() {
////        // 고유값 생성 (랜덤 사용)
////        int random = 1234;
////        // 채널 변수 생성
////        String cahnnel = "1234";
////
////        if (내부 API 접속 가능이 하지않으면?){
////            에러 발생 후 리턴
////        }
////
//////        리턴 변수 저장 (직접연동여부 등등)
////
////        if (외부 API1 접속 가능 ? ||외부 API2 접속 가능? ||외부 API3 접속 가능?등등등){
////
////            cahnnel = "선택된 통신사";
////
////            if (우리 DB 안에있는 개통 내역 체크){
////                channel = "우리가 보관하는 통신사 이름";
////                우리 DB 안에 저장 (개통 내역이 O 인 채로)
////            }
////
//////      우리 DB 내부 체크 (충전이 가능한지 해서)
////            충전 할 수 있는지 결과 리턴
////        } else{
////            // 외부 API 가 하나라도 동작하지 않았을 경우
////
////            if (PDS API 체크){// 통합된 API 호출 (위처럼 외부 API 호출 라이브러리 사용) (정액요금제인지만 확인)
////
////
////                우리 DB 안에 저장
////                충전 할 수 있는지 결과 리턴
////
////            } else{ // 수동 체크
////                if (수동 충전 내역 O){
////                    수동 충전 로직
////                }
////
////                실패 결과 리턴
////            }
////            실패 결과 리턴
////            //
////
////        }
////
////    }
//
////    public void chargeOnce() {
////        충전 여부 Check 여부
////        if (수동충전인경우) {
////            if (시간 연동 사용하여 업무 시간인경우 ){
////                충전 가능한 무언가 리턴
////            } else {
////                충전 안됨 리턴
////            }
////        }
////        충전 내역 DB 에 저장 (컬럼 - 충전중)
////        가능 여부 리턴
////    }
//
////    public void charge(충전 어디에 하는지에 대한 변수) {
////        실제 충전 로직(예치금 라이브러리 이용)
////
////        switch (위 변수) {
////            충전1:
////                충전1 로직
////            충전2:
////                충전2 로직
////        }
////
////        여기까지 왔따면 위에서 걸리지 않았따는거기 떄문에
////
////        if 일반 충전 API (외부 API 호출하여 실제 결제) {
////           성공하면
////        } else {
////            담보금액 복구
////            실패하면 실패 코드 리턴
////        }
////        예치금 차감 후
////        성공 코드 리턴
////    }
//
////    public void handCharge() {
////        // 이 API 가 호출되면 수동충전이 되었다는 의미니깐
////        // 충전 로직 실 행후 (예치금 차감, 충전 상태 변경)
////        // 성공 리턴
////
////    }
//
//}
