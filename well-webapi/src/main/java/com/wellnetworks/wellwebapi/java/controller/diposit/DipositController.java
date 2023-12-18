package com.wellnetworks.wellwebapi.java.controller.diposit;

import com.wellnetworks.wellcore.java.dto.Diposit.WellDipositCreateDTO;
import com.wellnetworks.wellcore.java.dto.Diposit.WellDipositInfoDTO;
import com.wellnetworks.wellcore.java.dto.Diposit.WellDipositListDTO;
import com.wellnetworks.wellcore.java.repository.account.WellDipositRepository;
import com.wellnetworks.wellcore.java.service.diposit.WellDipositService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(("/diposit/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class DipositController {
    @Autowired private WellDipositService dipositService;
    @Autowired private WellDipositRepository dipositRepository;

    // 개별 조회
    @GetMapping("info/{dipositIdx}")
    public ResponseEntity<?> getVirtualAccountById(@PathVariable String dipositIdx) {
        try {
            Optional<WellDipositInfoDTO> DipositInfoOpt = dipositService.getDipositById(dipositIdx);

            if (DipositInfoOpt.isPresent()) {
                return ResponseEntity.ok(DipositInfoOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("예치금을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }

    //리스트 조회
    @GetMapping("info")
    public ResponseEntity<?> getPartnerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "issueDate"));
            Page<WellDipositListDTO> dipositPage = dipositService.getAllDiposits(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("currentPage", dipositPage.getNumber());
            response.put("items", dipositPage.getContent());
            response.put("message", "조회 성공");
            response.put("status", "OK");
            response.put("totalItems", dipositPage.getTotalElements());
            response.put("totalPages", dipositPage.getTotalPages());

            response.put("dipositSum", dipositRepository.calculateDipositSum());
            response.put("chargeSum", dipositRepository.calculateChargeSum());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "서버 오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    //생성
    @PostMapping("generate")
    public ResponseEntity<String> generateDiposit(@Valid WellDipositCreateDTO createDTO) {
        try {
            dipositService.adjustDiposit(createDTO);
            return ResponseEntity.ok("예치금 생성 및 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("예치금 생성 및 저장 중 오류 발생: " + e.getMessage());
        }
    }
    //검색
    @GetMapping("search")
    public ResponseEntity<?> searchAccount(
            @RequestParam(value = "partnerNames", required = false) List<String> partnerNames,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "dipositAdjustment", required = false) Boolean dipositAdjustment,
            @RequestParam(value = "dipositStatus", required = false) String dipositStatus,
            @RequestParam(value = "memo", required = false) String memo,
            @RequestParam(value = "writer", required = false) String writer,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "registerDate"));
            Page<WellDipositListDTO> result = dipositService.searchDipositList(partnerNames, startDate, endDate, dipositAdjustment, dipositStatus, memo, writer, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("currentPage", result.getNumber());
            response.put("items", result.getContent());
            response.put("message", "");
            response.put("status", "OK");
            response.put("totalItems", result.getTotalElements());
            response.put("totalPages", result.getTotalPages());

            response.put("dipositSum", dipositRepository.calculateDipositSum());
            response.put("chargeSum", dipositRepository.calculateChargeSum());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "검색 중 오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
