package com.wellnetworks.wellwebapi.java.controller.account;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.dto.VirtualAccount.*;
import com.wellnetworks.wellcore.java.repository.account.WellVirtualAccountRepository;
import com.wellnetworks.wellcore.java.service.account.ExcelUtil;
import com.wellnetworks.wellcore.java.service.account.WellVirtualAccountService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(("/account/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class VirtualAccountController {
    @Autowired private ExcelUtil excelUtil;
    @Autowired private WellVirtualAccountService virtualAccountService;
    @Autowired private WellVirtualAccountRepository virtualAccountRepository;

    //개별 조회
    @GetMapping("/info/{virtualAccountIdx}")
    public ResponseEntity<?> getVirtualAccountById(@PathVariable String virtualAccountIdx) {
        try {
            Optional<WellVirtualAccountInfoDTO> accountInfoOpt = virtualAccountService.getVirtualAccountById(virtualAccountIdx);

            if (accountInfoOpt.isPresent()) {
                return ResponseEntity.ok(accountInfoOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("가상계좌를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }



    //상세 조회
    @GetMapping("/info/detail/{virtualAccountIdx}")
    public ResponseEntity<?> getDetailVirtualAccountById(@PathVariable String virtualAccountIdx) {
        try {
            Optional<WellVirtualAccountDetailDTO> accountDetailOpt = virtualAccountService.getDetailVirtualAccountById(virtualAccountIdx);

            if (accountDetailOpt.isPresent()) {
                return ResponseEntity.ok(accountDetailOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("상세 가상계좌 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }


    //리스트 조회
    @GetMapping("/info")
    public ResponseEntity<?> getPartnerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "issueDate"));
            Page<WellVirtualAccountInfoDTO> partnersPage = virtualAccountService.getAllAccounts(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("currentPage", partnersPage.getNumber());
            response.put("items", partnersPage.getContent());
            response.put("message", "조회 성공");
            response.put("status", "OK");
            response.put("totalItems", partnersPage.getTotalElements());
            response.put("totalPages", partnersPage.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "서버 오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


//생성
    @PostMapping("upload")
    public ResponseEntity<Map<String, Object>> addExcel(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        if (file.isEmpty()) {
            response.put("message", "빈 파일입니다.");
            return ResponseEntity.badRequest().body(response);
        }

        String contentType = file.getContentType();
        System.out.println("Uploaded file MIME type: " + contentType); // MIME 타입 로깅

        if (!"application/haansoftxlsx".equals(contentType) && !"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(contentType)
         && !"application/vnd.ms-excel".equals(contentType)) {
            response.put("message", "지원되지 않는 파일 형식입니다: " + contentType);
            return ResponseEntity.badRequest().body(response);
        }


        try {
            List<Map<String, Object>> listMap = excelUtil.getListData(file, 1, 3);
            List<WellVirtualAccountCreateDTO> dtos = listMap.stream()
                    .map(this::mapToAccount)
                    .collect(Collectors.toList());

            List<WellVirtualAccountEntity> entities = dtos.stream()
                    .map(virtualAccountService::convertToEntity)
                    .collect(Collectors.toList());

            virtualAccountRepository.saveAll(entities);
            response.put("message", "엑셀 파일 내 사용자 입력 성공");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "엑셀 파일 처리 중 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private WellVirtualAccountCreateDTO mapToAccount(Map<String, Object> userMap) {
        String bankName = (String) userMap.get("1");
        String accountNumber = (String) userMap.get("2");

        validateBankName(bankName);
        validateAccountNumber(accountNumber);

        WellVirtualAccountCreateDTO dto = new WellVirtualAccountCreateDTO();
        dto.setVirtualBankName(bankName);
        dto.setVirtualAccount(accountNumber);

        return dto;
    }
    private void validateBankName(String bankName) {
        if (bankName == null || bankName.trim().isEmpty()) {
            throw new IllegalArgumentException("은행명은 필수 입력 항목입니다.");
        }
        if (bankName.length() > 10) {
            throw new IllegalArgumentException("은행명은 10자 이하여야 합니다.");
        }
    }

    private void validateAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("계좌번호는 필수 입력 항목입니다.");
        }
    }


    // 엑셀 다운로드
    @GetMapping("/download/excel")
    public ResponseEntity<Resource> downloadExcelFile() throws UnsupportedEncodingException {
        String filePath = "C:\\study\\file\\가상계좌 관리 임시 입력폼.xlsx";
        Resource file = new FileSystemResource(Paths.get(filePath).toAbsolutePath().normalize().toString());

        if (!((FileSystemResource) file).exists()) {
            return ResponseEntity.notFound().build();
        }

        String downloadFilename = "가상계좌 양식.xlsx";
        String encodedFilename = URLEncoder.encode(downloadFilename, StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename)
                .body(file);
    }

    //가상계좌 발급(거래처)
    @PatchMapping("/issue/{partnerIdx}")
    public ResponseEntity<?> issueVirtualAccount(
            @PathVariable String partnerIdx,
            @RequestParam String virtualBankName) {
        try {
            virtualAccountService.issueVirtualAccount(partnerIdx, virtualBankName);
            return ResponseEntity.ok("가상계좌 발급 완료");
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    //가상계좌 변경(거래처)
    @PatchMapping("/changeAccount/{partnerIdx}")
    public ResponseEntity<?> changeVirtualAccount(
            @PathVariable String partnerIdx,
            @RequestParam String virtualBankName) {
        try {
            virtualAccountService.changeVirtualAccount(partnerIdx, virtualBankName);
            return ResponseEntity.ok("가상계좌 변경 완료");
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    //수정(발급)
    @PatchMapping("/update/notIssue/{virtualAccountIdx}")
    public ResponseEntity<?> updateVirtualAccount(@PathVariable String virtualAccountIdx,
                                                  @RequestParam String partnerIdx) {
        try {
            if (partnerIdx == null) {
                throw new IllegalArgumentException("거래처를 찾을 수 없습니다.");
            }
            virtualAccountService.updateVirtualAccount(virtualAccountIdx, partnerIdx);
            return ResponseEntity.status(HttpStatus.CREATED).body("가상계좌가 성공적으로 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("오류: " + e.getMessage());
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }



    //수정(미발급, 회수)
    @PatchMapping("/update/issue/{virtualAccountIdx}")
    public ResponseEntity<?> updateCollectVirtualAccount(@PathVariable String virtualAccountIdx,
                                                         @RequestParam String issuance) {
        try {
            virtualAccountService.updateCollectVirtualAccount(virtualAccountIdx, issuance);
            return ResponseEntity.status(HttpStatus.CREATED).body("가상계좌가 성공적으로 수정되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("오류: " + e.getMessage());
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    //다중검색
    @GetMapping("search")
    public ResponseEntity<?> searchAccount(
            @RequestParam(value = "partnerNames", required = false) List<String> partnerNames,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate,
            @RequestParam(value = "virtualBankName", required = false) String virtualBankName,
            @RequestParam(value = "issuance", required = false) String issuance,
            @RequestParam(value = "virtualAccount", required = false) String virtualAccount,
            @RequestParam(value = "writer", required = false) String writer,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "issueDate"));
            Page<WellVirtualAccountInfoDTO> result = virtualAccountService.searchAccountList(partnerNames, startDate, endDate, virtualBankName, issuance, virtualAccount, writer, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("currentPage", result.getNumber());
            response.put("items", result.getContent());
            response.put("message", "");
            response.put("status", "OK");
            response.put("totalItems", result.getTotalElements());
            response.put("totalPages", result.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "검색 중 오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    //삭제
    @DeleteMapping("delete/{virtualAccountIdx}")
    public ResponseEntity<?> deleteVirtualAccount(@PathVariable String virtualAccountIdx) {
        try {
            virtualAccountService.deleteAccount(virtualAccountIdx);
            return ResponseEntity.ok("가상계좌가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "가상계좌 삭제 중 오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
