package com.wellnetworks.wellcore.java.service.operator;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorCreateDTO;
import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorDetailDTO;
import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorListDTO;
import com.wellnetworks.wellcore.java.dto.Product.WellProductListDTO;
import com.wellnetworks.wellcore.java.repository.operator.WellOperatorRepository;
import com.wellnetworks.wellcore.java.repository.product.WellProductRepository;
import com.wellnetworks.wellcore.java.service.diposit.WellDipositService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WellOPeratorService {

    private static final Logger log = LoggerFactory.getLogger(WellDipositService.class);
    private final WellOperatorRepository operatorRepository;
    private final WellProductRepository productRepository;

    // 통신사 & 요금제 리스트
    public Page<WellOperatorListDTO> getAllOperatorsAndProducts(Pageable pageable) {

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

        Page<WellOperatorEntity> operators = operatorRepository.findAll(pageable);
        List<WellOperatorListDTO> result = new ArrayList<>();

        for (WellOperatorEntity operator : operators.getContent()) {
            List<WellProductEntity> products = productRepository.findByOperator(operator);
            List<WellProductListDTO> productDTOs = products.stream()
                    .map(product -> new WellProductListDTO(
                            product.getProductName(),
                            product.getNetwork(),
                            product.getProductType(),
                            product.getBaseFee(),
                            product.getData(),
                            product.getVoice(),
                            product.getSms(),
                            product.getEtc(),
                            product.getVisibleFlag()
                    ))
                    .collect(Collectors.toList());

            WellOperatorListDTO dto = new WellOperatorListDTO();
            dto.setOperatorName(operator.getOperatorName());
            dto.setProducts(productDTOs);

            result.add(dto);
        }

        return new PageImpl<>(result, pageable, operators.getTotalElements());
    }

    //통신사 상세 조회
    public Optional<WellOperatorDetailDTO> getDetailOperator(String operatorIdx) {
        return operatorRepository.findById(operatorIdx)
                .map(operator -> new WellOperatorDetailDTO(
                        operator.getOperatorName(),
                        operator.getOperatorCode(),
                        operator.getIsOpeningSearchFlag()
                ));
    }

    //생성
    public WellOperatorEntity createOperator(WellOperatorCreateDTO createDTO) {
        // 중복 체크
        if (operatorRepository.findByOperatorCode(createDTO.getOperatorCode()).isPresent()) {
            throw new IllegalArgumentException("이미사용중인 코드명입니다.");
        }

        WellOperatorEntity newOperator = WellOperatorEntity.builder()
                .operatorName(createDTO.getOperatorName())
                .operatorCode(createDTO.getOperatorCode().toUpperCase()) // 대문자로 저장
                .isOpeningSearchFlag(createDTO.getIsOpeningSearchFlag())
                .isExternalApiFlag(false)
                .isVisibleFlag(false)
                .isPdsFlag(false)
                .isRunFlag(false)
                .build();

        return operatorRepository.save(newOperator);
    }

    //중복체크
    public boolean isOperatorCodeExists(String operatorCode) {
        return operatorRepository.findByOperatorCode(operatorCode).isPresent();
    }
    //수정
    //삭제
}
