package com.wellnetworks.wellcore.java.service.apiKey;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApiKeyInfoDTO;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApikeyInCreateDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.apikeyIn.WellApikeyInRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WellApiKeyService {
    private final WellApikeyInRepository apikeyInRepository;
    private final WellPartnerRepository partnerRepository;


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
    //apikey 상세 조회
    //apikey  검색
    //apikey 생성
    @Transactional(rollbackOn = Exception.class)
    public void join(WellApikeyInCreateDTO createDTO) throws Exception {
        try {
            String apiKeyInIdx = UUID.randomUUID().toString();
            //apikeyin 랜덤값
            String generatedApiKey = UUID.randomUUID().toString();

            WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(createDTO.getPartnerIdx());

            WellApikeyInEntity existingApiKey = partnerEntity.getApiKey();
            if (existingApiKey != null) {
                existingApiKey.setPartnerIdx(null);
                apikeyInRepository.save(existingApiKey);
            }

            // API 키 생성
            WellApikeyInEntity apikeyInEntity = WellApikeyInEntity.builder()
                    .apiKeyInIdx(apiKeyInIdx)
                    .partnerIdx(partnerEntity.getPartnerIdx())
                    .apiKeyIn(generatedApiKey)
                    .apiKeyInRegisterDate(createDTO.getApiKeyInRegisterDate())
                    .apiKeyInEndFlag(createDTO.isApiKeyInEndFlag())
                    .partnerAgreeFlag(createDTO.isPartnerAgreeFlag())
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

            // API 키 저장
            apikeyInRepository.save(apikeyInEntity);

            partnerEntity.setInApiFlag(true);
            partnerEntity.setApiKey(apikeyInEntity);
            partnerRepository.save(partnerEntity);

            System.out.println("API 키 생성 및 저장 완료");
        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("API 키 생성 및 저장 중 오류 발생", e);
        }
    }



    //apikey 수정
    //apikey 체크항목만료
    //apikey 삭제
}
