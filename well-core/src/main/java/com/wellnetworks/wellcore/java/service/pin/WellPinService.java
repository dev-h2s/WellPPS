package com.wellnetworks.wellcore.java.service.pin;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.pin.WellPinEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
import com.wellnetworks.wellcore.java.dto.PIN.*;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.operator.WellOperatorRepository;
import com.wellnetworks.wellcore.java.repository.pin.WellPinRepository;
import com.wellnetworks.wellcore.java.repository.product.WellProductRepository;
import com.wellnetworks.wellcore.java.service.account.ExcelUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WellPinService {

    private static final Logger log = LoggerFactory.getLogger(WellPinService.class);
    private final WellPinRepository pinRepository;
    private final WellPartnerRepository partnerRepository;
    private final WellProductRepository productRepository;
    private final WellOperatorRepository operatorRepository;
    private final ExcelUtil excelUtil;

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

        if (pinRepository.findByPinNum(updateDTO.getPinNum()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 PIN 입니다.");
        }

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

    // 핀 엑셀 저장
    @Transactional
    public void importPinsFromExcel(MultipartFile file) throws IOException, InvalidFormatException {
        List<Map<String, Object>> excelData = excelUtil.getListData(file, 1, 6); // 엑셀 파일의 컬럼 수에 맞게 조정

        for (Map<String, Object> row : excelData) {
            WellPinExcelCreateDTO dto = mapRowToDto(row);

            // 중복 PIN 체크
            String pinNum = dto.getPinNum();
            if (pinRepository.findByPinNum(pinNum).isPresent()) {
                throw new IllegalArgumentException("중복된 PIN 값입니다: " + pinNum);
            }

            log.info("PIN 번호가 중복되지 않았습니다.");

            WellPinEntity pin = convertDtoToEntity(dto);
            pinRepository.save(pin);
        }
    }

    // Map을 DTO로 변환
    private WellPinExcelCreateDTO mapRowToDto(Map<String, Object> row) {
        WellPinExcelCreateDTO dto = new WellPinExcelCreateDTO();
        dto.setStore((String) row.get("1"));
        dto.setOperatorName((String) row.get("2"));
        dto.setProductName((String) row.get("3"));
        dto.setPinNum((String) row.get("4"));
        dto.setManagementNum((String) row.get("5"));
        String isSaleFlagString = (String) row.get("6");
        Boolean isSaleFlag = "1".equals(isSaleFlagString); // "1"이면 true, 그 외에는 false
        dto.setIsSaleFlag(isSaleFlag);

        return dto;
    }

    //엑셀 값들 등록
    private WellPinEntity convertDtoToEntity(WellPinExcelCreateDTO dto) {
         WellPinEntity pinEntity = new WellPinEntity();

        pinEntity.setStore(dto.getStore());
        pinEntity.setOperatorName(dto.getOperatorName());
        pinEntity.setProductName(dto.getProductName());
        pinEntity.setPinNum(dto.getPinNum());
        pinEntity.setManagementNum(dto.getManagementNum());
        pinEntity.setIsSaleFlag(dto.getIsSaleFlag());

        return pinEntity;
    }

    //개별 상세 조회
    public Optional<WellPinInfoDTO> infoPin(Long pinIdx) {
        return pinRepository.findById(pinIdx)
                .map(pin -> new WellPinInfoDTO(
                        pin.getStore(),
                        pin.getOperatorName(),
                        pin.getProductName(),
                        pin.getPinNum(),
                        pin.getManagementNum(),
                        pin.getIsSaleFlag()
                ));
    }
}
