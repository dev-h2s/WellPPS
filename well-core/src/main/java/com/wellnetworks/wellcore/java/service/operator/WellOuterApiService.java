package com.wellnetworks.wellcore.java.service.operator;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.dto.Operator.WellOuterApiListDTO;
import com.wellnetworks.wellcore.java.dto.Operator.WellOuterApiUpdateDTO;
import com.wellnetworks.wellcore.java.repository.operator.WellOuterApiRepository;
import com.wellnetworks.wellcore.java.service.diposit.WellDipositService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    //수정
    @Transactional(rollbackOn = Exception.class)
    public WellOuterApiUpdateDTO updateOperatorFlags(String operatorIdx, WellOuterApiUpdateDTO updateDTO) {
        try {
            WellOperatorEntity operatorEntity = outerApiRepository.findByOperatorIdx(operatorIdx);
            if (operatorEntity == null) {
                throw new EntityNotFoundException("통신사를 찾을 수 없습니다.");
            }
            // 나머지 4개의 수정 가능한 필드만 업데이트
            operatorEntity.setExternalApiFlag(updateDTO.getIsExternalApiFlag());
            operatorEntity.setVisibleFlag(updateDTO.getIsVisibleFlag());
            operatorEntity.setPdsFlag(updateDTO.getIsPdsFlag());
            operatorEntity.setRunFlag(updateDTO.getIsRunFlag());

            // 레포지토리를 통해 업데이트
            operatorEntity = outerApiRepository.save(operatorEntity);

            // 엔터티를 기반으로 DTO 업데이트
            BeanUtils.copyProperties(operatorEntity, updateDTO);

            return updateDTO;
        } catch (EntityNotFoundException e) {
            // 해당 엔터티를 찾을 수 없는 경우
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "엔터티를 찾을 수 없습니다.", e);
        } catch (Exception e) {
            // 기타 예외 처리
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "외부API 수정 중 오류가 발생했습니다.", e);
        }
    }
}
