package com.wellnetworks.wellcore.java.service.product;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorUpdateDTO;
import com.wellnetworks.wellcore.java.dto.Product.WellProductCreateDTO;
import com.wellnetworks.wellcore.java.dto.Product.WellProductDetailDTO;
import com.wellnetworks.wellcore.java.dto.Product.WellProductUpdateDTO;
import com.wellnetworks.wellcore.java.repository.operator.WellOperatorRepository;
import com.wellnetworks.wellcore.java.repository.product.WellProductRepository;
import com.wellnetworks.wellcore.java.service.diposit.WellDipositService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WellProductService {
    private static final Logger log = LoggerFactory.getLogger(WellDipositService.class);
    private final WellProductRepository productRepository;
    private final WellOperatorRepository operatorRepository;

    //요금제 상세 조회
    public Optional<WellProductDetailDTO> getDetailProduct(String productIdx) {
        return productRepository.findById(productIdx)
                .map(product -> {
                    String operatorName = product.getOperator() != null ? product.getOperator().getOperatorName() : null;
                    return new WellProductDetailDTO(
                            product.getVisibleFlag(),
                            product.getOpeningHistorySearchFlag(),
                            operatorName,
                            product.getNetwork(),
                            product.getBaseFee(),
                            product.getProductType(),
                            product.getProductName(),
                            product.getMvnoProductName(),
                            product.getData(),
                            product.getVoice(),
                            product.getEtc(),
                            product.getSms(),
                            product.getInternalCode(),
                            product.getExternalCode(),
                            product.getMemo()
                    );
                });
    }

    //입력
    public WellProductEntity createProduct(WellProductCreateDTO createDTO) {
        WellOperatorEntity operator = operatorRepository.findByOperatorName(createDTO.getOperatorName())
                .orElseThrow(() -> new EntityNotFoundException("통신사를 찾을 수 없습니다."));

        WellProductEntity product = WellProductEntity.builder()
                .visibleFlag(createDTO.getVisibleFlag())
                .openingHistorySearchFlag(createDTO.getOpeningHistorySearchFlag())
                .operator(operator)
                .network(createDTO.getNetwork())
                .baseFee(createDTO.getBaseFee())
                .productType(createDTO.getProductType())
                .productName(createDTO.getProductName())
                .data(createDTO.getData())
                .voice(createDTO.getVoice())
                .sms(createDTO.getSms())
                .etc(createDTO.getEtc())
                .internalCode(createDTO.getInternalCode())
                .externalCode(createDTO.getExternalCode())
                .mvnoProductName(createDTO.getMvnoProductName())
                .memo(createDTO.getMemo())
                .build();

        return productRepository.save(product);
    }

    //수정
    public WellProductEntity updateProduct(String productIdx, WellProductUpdateDTO updateDTO) {
        WellProductEntity product = productRepository.findById(productIdx)
                .orElseThrow(() -> new EntityNotFoundException("요금제가 존재하지 않습니다."));

        product.updateFromDTO(updateDTO);

        return productRepository.save(product);
    }

    //삭제
    public void deleteProduct(String productIdx) {
        WellProductEntity product = productRepository.findById(productIdx)
                .orElseThrow(() -> new EntityNotFoundException("요금제가 존재하지 않습니다."));
        productRepository.delete(product);
    }
}
