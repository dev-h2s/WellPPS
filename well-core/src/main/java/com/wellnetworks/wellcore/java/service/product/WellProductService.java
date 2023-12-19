package com.wellnetworks.wellcore.java.service.product;

import com.wellnetworks.wellcore.java.dto.Product.WellProductDetailDTO;
import com.wellnetworks.wellcore.java.repository.product.WellProductRepository;
import com.wellnetworks.wellcore.java.service.diposit.WellDipositService;
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
}
