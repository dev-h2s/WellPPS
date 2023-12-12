package com.wellnetworks.wellcore.java.service.operator;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.dto.Operator.WellOuterApiListDTO;
import com.wellnetworks.wellcore.java.repository.operator.WellOuterApiRepository;
import com.wellnetworks.wellcore.java.service.diposit.WellDipositService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WellOuterApiService {

    private static final Logger log = LoggerFactory.getLogger(WellDipositService.class);
    private final WellOuterApiRepository outerApiRepository;

    //리스트 조회
    public Page<WellOuterApiListDTO> getAllOuterApis(Pageable pageable) {
        try {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());

            Page<WellOperatorEntity> operators = outerApiRepository.findAll(pageable);
            List<WellOuterApiListDTO> outerApiListDTOList = new ArrayList<>();

            for (WellOperatorEntity operator : operators) {

                WellOuterApiListDTO outerApiListDTO = new WellOuterApiListDTO(
                        operator
                );
                outerApiListDTOList.add(outerApiListDTO);
            }
            return new PageImpl<>(outerApiListDTOList);
        } catch (Exception e) {
            log.error("외부API 리스트를 가져오는 도중 오류가 발생했습니다: {}", e);
            return Page.empty();
        }
    }
}
