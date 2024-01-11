package com.wellnetworks.wellcore.java.service.pin;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.pin.WellPinEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinCreateDTO;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinListDTO;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinUpdateDTO;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.operator.WellOperatorRepository;
import com.wellnetworks.wellcore.java.repository.pin.WellPinRepository;
import com.wellnetworks.wellcore.java.repository.product.WellProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.wellnetworks.wellcore", "com.wellnetworks.wellwebapi.java.controller"})
public class WellPinService {

    private static final Logger log = LoggerFactory.getLogger(WellPinService.class);
    private final WellPinRepository pinRepository;
    private final WellPartnerRepository partnerRepository;
    private final WellProductRepository productRepository;
    private final WellOperatorRepository operatorRepository;

    private final EntityManager em;

    //리스트 조회
    public Page<WellPinListDTO> getAllPins(Pageable pageable) {
        try {
            Page<WellPinEntity> pins = pinRepository.findAll(pageable);

            List<WellPinListDTO> pinListDTOList = pins.stream().map(pin -> {
                String operatorName = operatorRepository.findByOperatorName(pin.getOperatorName())
                        .map(WellOperatorEntity::getOperatorName)
                        .orElse("");
                String productName = productRepository.findByProductName(pin.getProductName())
                        .map(WellProductEntity::getProductName)
                        .orElse("");
                String storeName = partnerRepository.findByPartnerName(pin.getStore())
                        .map(WellPartnerEntity::getPartnerName)
                        .orElse("");
                String releaseName = partnerRepository.findByPartnerName(pin.getRelease())
                        .map(WellPartnerEntity::getPartnerName)
                        .orElse("");

                return new WellPinListDTO(pin, operatorName, productName, storeName, releaseName);
            }).collect(Collectors.toList());

            return new PageImpl<>(pinListDTOList, pageable, pins.getTotalElements());
        }  catch (Exception e) {
            log.error("PIN 리스트를 가져오는 도중 오류가 발생했습니다: {}", e.getMessage());
            throw new RuntimeException("PIN 리스트 조회 실패", e);
        }
    }

    //생성
    public WellPinEntity createPin(WellPinCreateDTO createDTO) {
        if (pinRepository.findByPinNum(createDTO.getPinNum()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 PIN 입니다.");
        }
        return pinRepository.save(createDTO.toEntity());
    }

    //수정
    @Transactional
    public WellPinEntity updatePin(WellPinUpdateDTO updateDTO) {
        WellPinEntity pinEntity = em.find(WellPinEntity.class, updateDTO.getPinIdx());

        if (updateDTO.getStore() != null) pinEntity.setStore(updateDTO.getStore());
        if (updateDTO.getRelease() != null) pinEntity.setRelease(updateDTO.getRelease());
        if (updateDTO.getOperatorName() != null) pinEntity.setOperatorName(updateDTO.getOperatorName());
        if (updateDTO.getProductName() != null) pinEntity.setProductName(updateDTO.getProductName());
        if (updateDTO.getPinNum() != null) pinEntity.setPinNum(updateDTO.getPinNum());
        if (updateDTO.getManagementNum() != null) pinEntity.setManagementNum(updateDTO.getManagementNum());
        if (updateDTO.getIsUseFlag() != null) pinEntity.setIsUseFlag(updateDTO.getIsUseFlag());
        if (updateDTO.getUser() != null) pinEntity.setUserName(updateDTO.getUser());
        if (updateDTO.getIsSaleFlag() != null) pinEntity.setIsSaleFlag(updateDTO.getIsSaleFlag());

        return pinEntity;
    }

    //삭제
    public void deletePin(Long pinIdx) {
        WellPinEntity pinEntity = pinRepository.findById(pinIdx)
                .orElseThrow(() -> new EntityNotFoundException("pin이 존재하지 않습니다."));
        pinRepository.delete(pinEntity);
    }
}
