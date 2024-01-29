package com.wellnetworks.wellcore.java.service.apiKey;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyIssueEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.*;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.apikeyIn.WellApikeyInRepository;
import com.wellnetworks.wellcore.java.repository.apikeyIn.WellApikeyIssueRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class WellApiKeyService {
    private final WellApikeyInRepository apikeyInRepository;
    private final WellPartnerRepository partnerRepository;
    private final WellApikeyIssueRepository apikeyIssueRepository;

    @PersistenceContext
    private EntityManager entityManager;

    // API 키 1개 조회
    public Optional<WellApiKeyInfoDTO> getApikeyByApikeyIdx(String apiKeyInIdx) {
        WellApikeyInEntity apiKeyEntity = apikeyInRepository.findByApiKeyInIdx(apiKeyInIdx);

        if (apiKeyEntity != null) {
            String partnerName = getPartnerName(apiKeyEntity.getPartnerIdx());
            return Optional.of(new WellApiKeyInfoDTO(apiKeyEntity, partnerName));
        } else {
            return Optional.empty();
        }
    }


    // API 키 리스트 조회
    public Page<WellApiKeyInfoDTO> getAllApikeys(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "apiKeyInRegisterDate"));

        Page<WellApikeyInEntity> apikeysPage = apikeyInRepository.findAll(pageable);
        List<WellApiKeyInfoDTO> apiKeyInfoList = new ArrayList<>();

        for (WellApikeyInEntity apiKeyEntity : apikeysPage.getContent()) {
            String partnerName = getPartnerName(apiKeyEntity.getPartnerIdx());
            WellApiKeyInfoDTO apiKeyInfo = new WellApiKeyInfoDTO(apiKeyEntity, partnerName);
            apiKeyInfoList.add(apiKeyInfo);
        }
        return new PageImpl<>(apiKeyInfoList, pageable, apikeysPage.getTotalElements());
    }

    // API 키 상세 조회
    public Optional<WellApiKeyDetailDTO> getDetailApiKeyByApiKeyInIdx(String apiKeyInIdx) {
        return Optional.ofNullable(apikeyInRepository.findByApiKeyInIdx(apiKeyInIdx))
                .map(entity -> {
                    String partnerName = getPartnerName(entity.getPartnerIdx());
                    return new WellApiKeyDetailDTO(entity, partnerName);
                });
    }


    // API 키 발급
    private String lastGeneratedApiKey;

    @Transactional(rollbackOn = Exception.class)
    public String issue(WellApikeyIssueEntity issueEntity) {
        if (lastGeneratedApiKey != null) {
            issueEntity.setApiKeyIn(lastGeneratedApiKey);
        } else {
            // 새로운 API 키 생성
            String apiKeyIn = UUID.randomUUID().toString();
            issueEntity.setApiKeyIn(apiKeyIn);
            lastGeneratedApiKey = apiKeyIn;
            apikeyIssueRepository.save(issueEntity);
        }
        return issueEntity.getApiKeyIn();
    }

    // API 키 생성
    @Transactional(rollbackOn = Exception.class)
    public void join(WellApikeyInCreateDTO createDTO) {
        String apiKeyInIdx = UUID.randomUUID().toString();
        WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(createDTO.getPartnerIdx());
        WellApikeyInEntity existingApiKey = partnerEntity.getApiKey();

        if (existingApiKey != null) {
            existingApiKey.setPartnerIdx(null);
            apikeyInRepository.save(existingApiKey);
        }

        String generatedApiKey = issue(new WellApikeyIssueEntity());

        // 발급 시 저장된 API 키와 생성 시 입력된 API 키가 같은지 확인
        if (Objects.equals(generatedApiKey, createDTO.getApiKeyIn())) {
            WellApikeyInEntity apikeyInEntity = WellApikeyInEntity.builder()
                    .apiKeyInIdx(apiKeyInIdx)
                    .partnerIdx(partnerEntity.getPartnerIdx())
                    .apiKeyIn(generatedApiKey)
                    .apiKeyInRegisterDate(createDTO.getApiKeyInRegisterDate())
                    .apiKeyInEndFlag(createDTO.isApiKeyInEndFlag())
                    .issuer(createDTO.getIssuer())
                    .serverUrl(createDTO.getServerUrl())
                    .apiServerIp(createDTO.getApiServerIp())
                    .memo(createDTO.getMemo())
                    .build();

            apikeyInRepository.save(apikeyInEntity);

            partnerEntity.setInApiFlag(true);
            partnerEntity.setApiKey(apikeyInEntity);
            partnerRepository.save(partnerEntity);

            System.out.println("API 키 생성 및 저장 완료");
        } else {
            System.out.println("생성된 API 키와 입력된 API 키가 일치하지 않습니다.");
            throw new RuntimeException("생성된 API 키와 입력된 API 키가 일치하지 않습니다.");
        }
    }

    // API 키 수정
    @Transactional(rollbackOn = Exception.class)
    public void update(String apiKeyInIdx, WellApiKeyUpdateDTO updateDTO) throws Exception {
        // 현재 API 키 엔티티 조회
        WellApikeyInEntity apikey = apikeyInRepository.findByApiKeyInIdx(apiKeyInIdx);
        WellPartnerEntity currentPartnerEntity = partnerRepository.findByPartnerIdx(apikey.getPartnerIdx());

        currentPartnerEntity.setApiKey(null);

        if (updateDTO != null) {
            WellPartnerEntity newPartnerEntity = partnerRepository.findByPartnerIdx(updateDTO.getPartnerIdx());
            newPartnerEntity.setApiKey(apikey);

// 기존에 연결되어 있던 다른 API 키의 partnerIdx를 null로 설정
            WellApikeyInEntity existingApikey = apikeyInRepository.findByPartnerIdx(newPartnerEntity.getPartnerIdx());
            if (existingApikey != null) {
                existingApikey.setPartnerIdx(null);
                apikeyInRepository.save(existingApikey);
            }

            BeanUtils.copyProperties(updateDTO, apikey);
            apikey.updateFormDTO(updateDTO);

            // expire 값에 따라 처리(만료 버튼 여부)
            if (updateDTO.isExpire()) {
                WellApikeyExpireDTO expireDTO = new WellApikeyExpireDTO();
                expireDTO.setApiKeyInIdx(apiKeyInIdx);
                expireApikey(expireDTO);
            }
        }
    }


    // API 키 체크항목 만료
    @Transactional(rollbackOn = Exception.class)
    public void expireApikey(WellApikeyExpireDTO expireDTO) {
        WellApikeyInEntity apikeyInEntity = apikeyInRepository.findByApiKeyInIdx(expireDTO.getApiKeyInIdx());

        if (apikeyInEntity != null) {
            apikeyInEntity.setApiKeyInEndFlag(true);
            WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(apikeyInEntity.getPartnerIdx());

            apikeyInEntity.setPartnerIdx(null);
            apikeyInRepository.save(apikeyInEntity);

            if (partnerEntity != null) {
                partnerEntity.setApiKey(null);
                partnerRepository.save(partnerEntity);
                System.out.println("거래처 연결 해제 완료");
            } else {
                System.out.println("해당 거래처를 찾을 수 없습니다.");
            }

            System.out.println("API 키 만료 처리 완료");
        } else {
            System.out.println("해당 API 키를 찾을 수 없습니다.");
        }
    }

    // API 키 삭제
    @Transactional(rollbackOn = Exception.class)
    public void deleteApiKey(WellApiKeyDeleteDTO deleteDTO) {
        WellApikeyInEntity apikeyInEntity = apikeyInRepository.findByApiKeyInIdx(deleteDTO.getApiKeyInIdx());

        WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(apikeyInEntity.getPartnerIdx());
        if (partnerEntity != null) {
            partnerEntity.setApiKey(null);
        }

        entityManager.flush();
        entityManager.clear();

        apikeyInRepository.deleteByApiKeyInIdx(apikeyInEntity.getApiKeyInIdx());
    }

    // API 키 검색
    public Page<WellApiKeyInfoDTO> searchApiKeyList(String issuer, String apiKeyIn, String serverUrl, String apiServerIp,
                                                    List<String> partnerNames, Pageable pageable) {
        Specification<WellApikeyInEntity> spec = Specification.where(ApiKeySpecification.apikeyIssuerContains(issuer))
                .and(ApiKeySpecification.apikeyInContains(apiKeyIn))
                .and(ApiKeySpecification.apikeyUrlContains(serverUrl))
                .and(ApiKeySpecification.apikeyIpContains(apiServerIp))
                .and(ApiKeySpecification.partnerNamesIn(partnerNames));

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "apiKeyInRegisterDate"));

        Page<WellApikeyInEntity> apikeys = apikeyInRepository.findAll(spec, pageable);

        List<WellApiKeyInfoDTO> apiKeyInfoList = new ArrayList<>();

        for (WellApikeyInEntity apikey : apikeys) {
            String partnerName = getPartnerName(apikey.getPartnerIdx());
            WellApiKeyInfoDTO apiKeyInfo = new WellApiKeyInfoDTO(apikey, partnerName);
            apiKeyInfoList.add(apiKeyInfo);
        }
        return new PageImpl<>(apiKeyInfoList, pageable, apikeys.getTotalElements());
    }

    // 중복 코드 제거
    private String getPartnerName(String partnerIdx) {
        if (partnerIdx != null) {
            WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(partnerIdx);
            return partnerEntity != null ? partnerEntity.getPartnerName() : null;
        }
        return null;
    }
}
