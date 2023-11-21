package com.wellnetworks.wellwebapi.java.controller.apiKey;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyIssueEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApiKeyDetailDTO;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApiKeyInfoDTO;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApikeyExpireDTO;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApikeyInCreateDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerDetailDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import com.wellnetworks.wellcore.java.repository.apikeyIn.WellApikeyInRepository;
import com.wellnetworks.wellcore.java.service.apiKey.WellApiKeyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
    public List<WellApiKeyInfoDTO> getAllApikeys() {
        List<WellApiKeyInfoDTO> apiKeyList = apiKeyService.getAllApikeys();

        return apiKeyList;
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API 키 생성 중 오류 발생: " + e.getMessage());
        }
    }


    // 생성
    @PostMapping("generate")
    public ResponseEntity<String> generateApiKey(@Valid WellApikeyInCreateDTO createDTO) {
        try {
            apiKeyService.join(createDTO);
            return ResponseEntity.ok("API 키 생성 및 저장 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("API 키 생성 및 저장 중 오류 발생: " + e.getMessage());
        }
    }


    //만료 처리
    @PatchMapping("/expire/{apiKeyInIdx}")
    public ResponseEntity<String> expireApiKey(@Valid WellApikeyExpireDTO expireDTO
                                                , @PathVariable String apiKeyInIdx) {
        try {
            apiKeyService.expireApikey(apiKeyInIdx, expireDTO);
            return ResponseEntity.ok("API 키 만료 처리 완료");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("API 키 만료 중 오류 발생: " + e.getMessage());
        }
    }
}
