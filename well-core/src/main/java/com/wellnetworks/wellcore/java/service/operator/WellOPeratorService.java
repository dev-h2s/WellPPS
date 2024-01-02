package com.wellnetworks.wellcore.java.service.operator;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorCreateDTO;
import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorDetailDTO;
import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorListDTO;
import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorUpdateDTO;
import com.wellnetworks.wellcore.java.dto.Product.WellProductListDTO;
import com.wellnetworks.wellcore.java.repository.operator.WellOperatorRepository;
import com.wellnetworks.wellcore.java.repository.product.WellProductRepository;
import com.wellnetworks.wellcore.java.service.diposit.WellDipositService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
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
        try {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

            Page<WellOperatorEntity> operators = operatorRepository.findAll(pageable);
            List<WellOperatorListDTO> result = new ArrayList<>();

            for (WellOperatorEntity operator : operators.getContent()) {
                List<WellProductEntity> products = productRepository.findByOperator(operator);
                List<WellProductListDTO> productDTOs = products.stream()
                        .map(product -> new WellProductListDTO(
                                product.getProductIdx(),
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
                dto.setOperatorIdx(operator.getOperatorIdx());
                dto.setOperatorName(operator.getOperatorName());
                dto.setProducts(productDTOs);

                result.add(dto);
            }

            return new PageImpl<>(result, pageable, operators.getTotalElements());
        } catch (DataAccessException e) {
            log.error("데이터베이스 접근 중 오류 발생", e);
            throw e;
        } catch (Exception e) {
            log.error("통신사 및 요금제 조회 중 오류 발생", e);
            throw e;
        }
    }

    //통신사 상세 조회
    public Optional<WellOperatorDetailDTO> getDetailOperator(String operatorIdx) {
        try {
            return operatorRepository.findById(operatorIdx)
                    .map(operator -> new WellOperatorDetailDTO(
                            operator.getOperatorName(),
                            operator.getOperatorCode(),
                            operator.getIsOpeningSearchFlag()
                    ));
        } catch (DataAccessException e) {
            log.error("데이터베이스 접근 중 오류 발생", e);
            throw e;
        } catch (Exception e) {
            log.error("통신사 상세 조회 중 오류 발생", e);
            throw e;
        }
    }

    // 생성
    public List<WellOperatorEntity> createOperator(WellOperatorCreateDTO createDTO) {
        try {
            // 중복 체크
            if (operatorRepository.findByOperatorCode(createDTO.getOperatorCode()).isPresent()) {
                throw new IllegalArgumentException("이미 사용 중인 코드명입니다.");
            }

            List<Object[]> existingVersions = operatorRepository.findDistinctVersions();
            List<WellOperatorEntity> createdOperators = new ArrayList<>();

            for (Object[] versionInfo : existingVersions) {
                Float versionId = (Float) versionInfo[0];
                String versionName = (String) versionInfo[1];

                WellOperatorEntity newOperator = WellOperatorEntity.builder()
                        .operatorName(createDTO.getOperatorName())
                        .operatorCode(createDTO.getOperatorCode().toUpperCase())
                        .isOpeningSearchFlag(createDTO.getIsOpeningSearchFlag())
                        .isExternalApiFlag(false)
                        .isVisibleFlag(false)
                        .isPdsFlag(false)
                        .isRunFlag(false)
                        .versionId(versionId)
                        .versionName(versionName)
                        .build();

                createdOperators.add(operatorRepository.save(newOperator));
            }

            return createdOperators;
        } catch (DataAccessException e) {
            log.error("데이터베이스 접근 중 오류 발생", e);
            throw e;
        } catch (IllegalArgumentException e) {
            log.error("중복된 코드명 오류 발생", e);
            throw e;
        } catch (Exception e) {
            log.error("통신사 생성 중 오류 발생", e);
            throw e;
        }
    }


    //중복체크
    public boolean isOperatorCodeExists(String operatorCode) {
        try {
            return operatorRepository.findByOperatorCode(operatorCode).isPresent();
        } catch (DataAccessException e) {
            log.error("데이터베이스 접근 중 오류 발생", e);
            throw e;
        } catch (Exception e) {
            log.error("중복 체크 중 오류 발생", e);
            throw e;
        }
    }

    //수정
    public WellOperatorEntity updateOperator(String operatorIdx, WellOperatorUpdateDTO updateDTO) {
        try {
            WellOperatorEntity operator = operatorRepository.findById(operatorIdx)
                    .orElseThrow(() -> new EntityNotFoundException("통신사가 존재하지 않습니다."));

            operator.setOperatorName(updateDTO.getOperatorName());
            operator.setIsOpeningSearchFlag(updateDTO.getIsOpeningSearchFlag());

            return operatorRepository.save(operator);
        } catch (DataAccessException e) {
            log.error("데이터베이스 접근 중 오류 발생", e);
            throw e;
        } catch (Exception e) {
            log.error("통신사 수정 중 오류 발생", e);
            throw e;
        }
    }

    //삭제
    public void deleteOperator(String operatorIdx) {
        try {
            WellOperatorEntity operator = operatorRepository.findById(operatorIdx)
                    .orElseThrow(() -> new EntityNotFoundException("통신사가 존재하지 않습니다."));
            operatorRepository.delete(operator);
    } catch (DataAccessException e) {
        log.error("데이터베이스 접근 중 오류 발생", e);
        throw e;
    } catch (Exception e) {
        log.error("통신사 삭제 중 오류 발생", e);
        throw e;
    }
    }
}
