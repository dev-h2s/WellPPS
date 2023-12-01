package com.wellnetworks.wellwebapi.java.controller.account;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.dto.VirtualAccount.WellVirtualAccountCreateDTO;
import com.wellnetworks.wellcore.java.dto.VirtualAccount.WellVirtualAccountInfoDTO;
import com.wellnetworks.wellcore.java.repository.account.WellVirtualAccountRepository;
import com.wellnetworks.wellcore.java.service.account.ExcelUtil;
import com.wellnetworks.wellcore.java.service.account.WellVirtualAccountService;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    //개별 조회 & 상세 조회
    @GetMapping("info/{virtualAccountIdx}")
    public ResponseEntity<WellVirtualAccountInfoDTO> getVirtualAccountById(@PathVariable String virtualAccountIdx) {
        return virtualAccountService.getVirtualAccountById(virtualAccountIdx)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //리스트 조회
    @GetMapping("info")
    public ResponseEntity<Map<String, Object>> getPartnerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "issueDate"));
        Page<WellVirtualAccountInfoDTO> partnersPage = virtualAccountService.getAllAccounts(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", partnersPage.getNumber());
        response.put("items", partnersPage.getContent());
        response.put("message", "");
        response.put("status", "OK");
        response.put("totalItems", partnersPage.getTotalElements());
        response.put("totalPages", partnersPage.getTotalPages());

        return ResponseEntity.ok(response);
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
            response.put("message", "엑셀 파일 처리 중 오류 발생");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    private WellVirtualAccountCreateDTO mapToAccount(Map<String, Object> userMap) {
        String bankName = (String) userMap.get("1");
        String accountNumber = (String) userMap.get("2");

        WellVirtualAccountCreateDTO dto = new WellVirtualAccountCreateDTO();
        dto.setVirtualBankName(bankName);
        dto.setVirtualAccount(accountNumber);

        return dto;
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



    //수정
    //다중검색
    //삭제
}
