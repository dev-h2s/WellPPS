package com.wellnetworks.wellcore.java.service.apiKey;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyIssueEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApiKeyDetailDTO;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApiKeyInfoDTO;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApikeyExpireDTO;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApikeyInCreateDTO;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.apikeyIn.WellApikeyInRepository;
import com.wellnetworks.wellcore.java.repository.apikeyIn.WellApikeyIssueRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WellApiKeyService {
    private final WellApikeyInRepository apikeyInRepository;
    private final WellPartnerRepository partnerRepository;
    private final WellApikeyIssueRepository apikeyIssueRepository;

    //apikey 1개 조회
    public Optional<WellApiKeyInfoDTO> getApikeyByApikeyIdx(String apiKeyInIdx) {
        return Optional.ofNullable(apikeyInRepository.findByApiKeyInIdx(apiKeyInIdx))
                .map(entity -> {
                    String partnerIdx = entity.getPartnerIdx();
                    String partnerName = null;

                    // partnerIdx가 null이 아닌 경우에만 거래처명 조회
                    if (partnerIdx != null) {
                        WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(partnerIdx);
                        if (partnerEntity != null) {
                            partnerName = partnerEntity.getPartnerName();
                        }
                    }
                    return new WellApiKeyInfoDTO(entity, partnerName);
                });
    }




    //apikey 리스트 조회
    public List<WellApiKeyInfoDTO> getAllApikeys() {
        List<WellApikeyInEntity> apikeys = apikeyInRepository.findAll();
        List<WellApiKeyInfoDTO> apiKeyInfoList = new ArrayList<>();

        for (WellApikeyInEntity apiKeyEntity : apikeys) {
            String partnerIdx = apiKeyEntity.getPartnerIdx();
            String partnerName = null;

            // partnerIdx가 null이 아닌 경우에만 거래처명 조회
            if (partnerIdx != null) {
                WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(partnerIdx);
                if (partnerEntity != null) {
                    partnerName = partnerEntity.getPartnerName();
                }
            }

            WellApiKeyInfoDTO apiKeyInfo = new WellApiKeyInfoDTO(apiKeyEntity, partnerName);
            apiKeyInfoList.add(apiKeyInfo);
        }

        return apiKeyInfoList;
    }


    //apikey 상세 조회
    public Optional<WellApiKeyDetailDTO> getDetailApiKeyByApiKeyInIdx(String apiKeyInIdx) {
        return Optional.ofNullable(apikeyInRepository.findByApiKeyInIdx(apiKeyInIdx))
                .map(entity -> {
                    String partnerIdx = entity.getPartnerIdx();
                    String partnerName = null;

                    // partnerIdx가 null이 아닌 경우에만 거래처명 조회
                    if (partnerIdx != null) {
                        WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(partnerIdx);
                        if (partnerEntity != null) {
                            partnerName = partnerEntity.getPartnerName();
                        }
                    }
                    return new WellApiKeyDetailDTO(entity, partnerName);
                });
    }

    //apikey  검색


    //apikey 발급
    private String lastGeneratedApiKey;
    @Transactional(rollbackOn = Exception.class)
    public String issue(WellApikeyIssueEntity issueEntity) throws Exception {
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

    //apikey 생성
    @Transactional(rollbackOn = Exception.class)
    public void join(WellApikeyInCreateDTO createDTO) throws Exception {
        try {
            String apiKeyInIdx = UUID.randomUUID().toString();

            WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(createDTO.getPartnerIdx());

            WellApikeyInEntity existingApiKey = partnerEntity.getApiKey();
            if (existingApiKey != null) {
                existingApiKey.setPartnerIdx(null);
                apikeyInRepository.save(existingApiKey);
            }

            // API 키 생성
            String generatedApiKey = issue(new WellApikeyIssueEntity());

            System.out.println(generatedApiKey);
            System.out.println(createDTO.getApiKeyIn());

            // 발급 시 저장된 API 키와 생성 시 입력된 API 키가 같은지 확인
            if (generatedApiKey.equals(createDTO.getApiKeyIn())) {
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
                        .home(createDTO.isHome())
                        .dream(createDTO.isDream())
                        .valueCom(createDTO.isValueCom())
                        .iz(createDTO.isIz())
                        .asia(createDTO.isAsia())
                        .PDS(createDTO.isPDS())
                        .build();

                apikeyInRepository.save(apikeyInEntity);

                partnerEntity.setInApiFlag(true);
                partnerEntity.setApiKey(apikeyInEntity);
                partnerRepository.save(partnerEntity);

                System.out.println("API 키 생성 및 저장 완료");
            } else {
                System.out.println("생성된 API 키와 입력된 API 키가 일치하지 않습니다.");
                // 일치하지 않을 경우 예외 발생
                throw new RuntimeException("생성된 API 키와 입력된 API 키가 일치하지 않습니다.");
            }
        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("API 키 생성 및 저장 중 오류 발생", e);
        }
    }



    //apikey 수정
//apikey 체크항목만료
    @Transactional(rollbackOn = Exception.class)
    public void expireApikey(String apiKeyInIdx, WellApikeyExpireDTO expireDTO) {
        try {
            // 입력받은 API Key로 해당 API 키를 조회
            WellApikeyInEntity apikeyInEntity = apikeyInRepository.findByApiKeyInIdx(expireDTO.getApiKeyInIdx());

            // API 키가 존재하면 만료 처리
            if (apikeyInEntity != null) {
                apikeyInEntity.setApiKeyInEndFlag(true);
                WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(apikeyInEntity.getPartnerIdx());

                apikeyInEntity.setPartnerIdx(null);
                apikeyInRepository.save(apikeyInEntity);

                if (partnerEntity != null) {
                    partnerEntity.removeApikeyInIdx();
                    partnerRepository.save(partnerEntity);
                    System.out.println("거래처 연결 해제 완료");
                } else {
                    System.out.println("해당 거래처를 찾을 수 없습니다.");
                    // 예외 발생 또는 메시지 처리 등 필요한 작업 수행
                }

                System.out.println("API 키 만료 처리 완료");
            } else {
                System.out.println("해당 API 키를 찾을 수 없습니다.");
                // 예외 발생 또는 메시지 처리 등 필요한 작업 수행
            }
        } catch (Exception e) {
            System.out.println("API 키 만료 중 오류 발생: " + e.getMessage());
            // 롤백을 위해 예외 발생
            throw new RuntimeException("API 키 만료 중 오류 발생", e);
        }
    }



    //apikey 삭제
}
