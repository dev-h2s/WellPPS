package com.wellnetworks.wellcore.java.service.pin;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.pin.WellPinEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinListDTO;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.operator.WellOperatorRepository;
import com.wellnetworks.wellcore.java.repository.pin.WellPinRepository;
import com.wellnetworks.wellcore.java.repository.product.WellProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
}
