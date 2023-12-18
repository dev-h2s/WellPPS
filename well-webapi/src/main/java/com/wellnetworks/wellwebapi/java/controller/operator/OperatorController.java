package com.wellnetworks.wellwebapi.java.controller.operator;

import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorCreateDTO;
import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorDetailDTO;
import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorListDTO;
import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorUpdateDTO;
import com.wellnetworks.wellcore.java.service.operator.WellOPeratorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(("/operator/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class OperatorController {

    @Autowired private WellOPeratorService wellOPeratorService;

    //리스트 조회
    @GetMapping("info")
    public ResponseEntity<?> getOuterApiList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<WellOperatorListDTO> listDTOS = wellOPeratorService.getAllOperatorsAndProducts(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("currentPage", listDTOS.getNumber());
            response.put("items", listDTOS.getContent());
            response.put("message", "조회 성공");
            response.put("status", "OK");
            response.put("totalItems", listDTOS.getTotalElements());
            response.put("totalPages", listDTOS.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "서버 오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    //상세 조회
    @GetMapping("info/detail/{operatorIdx}")
    public ResponseEntity<?> getOperatorDetail(@PathVariable String operatorIdx) {
        try {
            Optional<WellOperatorDetailDTO> operatorDetailOpt = wellOPeratorService.getDetailOperator(operatorIdx);

            if (operatorDetailOpt.isPresent()) {
                return ResponseEntity.ok(operatorDetailOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("통신사 정보가 존재하지 않습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }

    //생성
    @PostMapping("create")
    public ResponseEntity<?> createOperator(@Valid WellOperatorCreateDTO createDTO) {
        try {
            // 중복 체크
            if (wellOPeratorService.isOperatorCodeExists(createDTO.getOperatorCode())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("통신사 코드가 이미 존재합니다.");
            }

            wellOPeratorService.createOperator(createDTO);
            return ResponseEntity.ok(" 통신사 생성 및 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }

    //중복체크
    @GetMapping("checkCode")
    public ResponseEntity<?> checkOperatorCode(@RequestParam String operatorCode) {
        boolean exists = wellOPeratorService.isOperatorCodeExists(operatorCode);
        if (exists == true) {
            return ResponseEntity.ok("이미사용중인 코드명입니다.");
        }
        return ResponseEntity.ok("사용가능한 코드명입니다.");
    }

    //수정
    @PatchMapping("update/{operatorIdx}")
    public ResponseEntity<?> updateOperator(@PathVariable String operatorIdx,
                                            @Valid WellOperatorUpdateDTO updateDTO) {
        try {
            wellOPeratorService.updateOperator(operatorIdx, updateDTO);
            return ResponseEntity.ok("통신사가 수정되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }

    //삭제
    @DeleteMapping("delete/{operatorIdx}")
    public ResponseEntity<?> deleteOperator(@PathVariable String operatorIdx) {
        try {
            wellOPeratorService.deleteOperator(operatorIdx);
            return ResponseEntity.ok("통신사가 삭제되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }
}
