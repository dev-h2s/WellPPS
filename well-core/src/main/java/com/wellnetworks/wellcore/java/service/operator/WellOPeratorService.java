package com.wellnetworks.wellcore.java.service.operator;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
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
}
