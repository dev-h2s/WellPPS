package com.wellnetworks.wellwebapi.java.controller.apiKey;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyIssueEntity;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.*;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import com.wellnetworks.wellcore.java.service.apiKey.WellApiKeyService;
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

import java.util.*;

@RestController
@RequestMapping(("/apikey/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class ApiKeyController {

    @Autowired private WellApiKeyService apiKeyService;

    //1개 조회
    @GetMapping("info/{apiKeyInIdx}")
    public Optional<WellApiKeyInfoDTO> getApikey(@PathVariable String apiKeyInIdx) throws ClassNotFoundException {
        Optional<WellApiKeyInfoDTO> apikey = apiKeyService.getApikeyByApikeyIdx(apiKeyInIdx);
        if (apikey == null) {
            throw new ClassNotFoundException(String.format("IDX[%s] not found", apiKeyInIdx));
        }
        return apikey;
    }

    //리스트 조회
    @GetMapping("info")
    public ResponseEntity<Map<String, Object>> getAllApikeys(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "apiKeyInRegisterDate"));
        Page<WellApiKeyInfoDTO> apikeysPage = apiKeyService.getAllApikeys(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", apikeysPage.getNumber());
        response.put("items", apikeysPage.getContent());
        response.put("message", "");
        response.put("status", "OK");
        response.put("totalItems", apikeysPage.getTotalElements());
        response.put("totalPages", apikeysPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    //상세 조회
    @GetMapping("info/detail/{apiKeyInIdx}")
    public Optional<WellApiKeyDetailDTO> getDetailApiKeyByApiKeyInIdx(@PathVariable String apiKeyInIdx) throws ClassNotFoundException {
        Optional<WellApiKeyDetailDTO> apiKeyDetailDTO = apiKeyService.getDetailApiKeyByApiKeyInIdx(apiKeyInIdx);
        if (apiKeyDetailDTO == null) {
            throw new ClassNotFoundException(String.format("IDX[%s] not found", apiKeyInIdx));
        }
        return apiKeyDetailDTO;
    }

    //발급
    @PostMapping("issue")
    public ResponseEntity<String> apiKeyIssue(@Valid WellApikeyIssueEntity issueEntity) {
        try {
            String generatedApiKey = apiKeyService.issue(issueEntity);
            return ResponseEntity.ok(generatedApiKey);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("API 키 생성 중 오류 발생: " + e.getMessage());
        }
    }


    // 생성
    @PostMapping("generate")
    public ResponseEntity<String> generateApiKey(@Valid WellApikeyInCreateDTO createDTO) {
        try {
            apiKeyService.join(createDTO);
            return ResponseEntity.ok("API 키 생성 및 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("API 키 생성 및 저장 중 오류 발생: " + e.getMessage());
        }
    }

    //수정
    @PatchMapping("update/{apiKeyInIdx}")
    public ResponseEntity<String> patchApiKey(@Valid WellApiKeyUpdateDTO updateDTO,
                                              @PathVariable String apiKeyInIdx) throws Exception {
        apiKeyService.update(apiKeyInIdx, updateDTO);
        if (apiKeyInIdx == null) {
            throw new ClassNotFoundException(String.format("IDX[%s] not found", apiKeyInIdx));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("ApiKey가 성공적으로 수정되었습니다.");
    }


    //만료 처리
    @PatchMapping("expire/{apiKeyInIdx}")
    public ResponseEntity<String> expireApiKey(@Valid WellApikeyExpireDTO expireDTO) {
        try {
            apiKeyService.expireApikey(expireDTO);
            return ResponseEntity.ok("API 키 만료 처리 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("API 키 만료 중 오류 발생: " + e.getMessage());
        }
    }

    //삭제
    @DeleteMapping("delete/{apiKeyInIdx}")
    public ResponseEntity<String> deleteApiKey(@Valid WellApiKeyDeleteDTO deleteDTO) throws Exception {
        try {
        apiKeyService.deleteApiKey(deleteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("ApiKey가 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("API 키 삭제 중 오류 발생: " + e.getMessage());
        }
    }

    //검색
    @GetMapping("search")
    public ResponseEntity<Map<String, Object>> searchApiKey(
            @RequestParam(value = "issuer", required = false) String issuer,
            @RequestParam(value = "apiKeyIn", required = false) String apiKeyIn,
            @RequestParam(value = "serverUrl", required = false) String serverUrl,
            @RequestParam(value = "apiServerIp", required = false) String apiServerIp,
            @RequestParam(value = "partnerNames", required = false) List<String> partnerNames
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int size
    ) {

            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "apiKeyInRegisterDate"));
            Page<WellApiKeyInfoDTO> result = apiKeyService.searchApiKeyList(issuer, apiKeyIn, serverUrl, apiServerIp, partnerNames, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("currentPage", result.getNumber());
            response.put("items", result.getContent());
            response.put("message", "");
            response.put("status", "OK");
            response.put("totalItems", result.getTotalElements());
            response.put("totalPages", result.getTotalPages());

            return ResponseEntity.ok(response);

    }
}
