package com.wellnetworks.wellwebapi.java.controller.operator;

import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorListDTO;
import com.wellnetworks.wellcore.java.service.operator.WellOPeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
}
