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
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
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
        Page<WellPinEntity> pins = pinRepository.findAll(pageable);

        List<WellPinListDTO> pinListDTOList = pins.stream().map(pin -> {
            String operatorName = operatorRepository.findByOperatorName(pin.getOperatorName()).map(WellOperatorEntity::getOperatorName).orElse("");
            String productName = productRepository.findByProductName(pin.getProductName()).map(WellProductEntity::getProductName).orElse("");
            String storeName = partnerRepository.findByPartnerName(pin.getStore()).map(WellPartnerEntity::getPartnerName).orElse("");
            String releaseName = partnerRepository.findByPartnerName(pin.getRelease()).map(WellPartnerEntity::getPartnerName).orElse("");

            return new WellPinListDTO(pin, operatorName, productName, storeName, releaseName);
        }).collect(Collectors.toList());

        return new PageImpl<>(pinListDTOList, pageable, pins.getTotalElements());
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
    public List<WellPinExcelCreateDTO> importPinsFromExcel(MultipartFile file) throws IOException, InvalidFormatException {
        List<Map<String, Object>> excelData = excelUtil.getListData(file, 1, 6);
        List<WellPinExcelCreateDTO> duplicatePins = new ArrayList<>();
        List<WellPinExcelCreateDTO> allPins = new ArrayList<>();

        for (Map<String, Object> row : excelData) {
            WellPinExcelCreateDTO dto = mapRowToDto(row);
            allPins.add(dto);

            if (pinRepository.findByPinNum(dto.getPinNum()).isPresent()) {
                duplicatePins.add(dto);
            }
        }

        // 중복된 핀이 없으면 모든 핀 저장
        if (duplicatePins.isEmpty()) {
            for (WellPinExcelCreateDTO dto : allPins) {
                WellPinEntity pin = convertDtoToEntity(dto);
                pinRepository.save(pin);
            }
        }

        return duplicatePins;
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

    //일괄 출고 리스트
    public List<WellReleaseListDTO> getReleaseList() {
        List<WellReleaseListDTO> releaseList = new ArrayList<>();
        List<Object[]> groupedData = pinRepository.countPinsByOperatorNameAndProductName();

        for (Object[] group : groupedData) {
            String operatorName = (String) group[0];
            String productName = (String) group[1];
            Long count = (Long) group[2];

            WellReleaseListDTO releaseDTO = new WellReleaseListDTO(operatorName, productName);
            releaseDTO.setProductCount(count); // PIN 개수 설정
            releaseList.add(releaseDTO);
        }
        return releaseList;
    }

    // 출고 개별 값들 조회
    public List<WellReleaseDTO> getReleaseExcel() {
        List<WellReleaseDTO> releaseList = new ArrayList<>();
        List<WellPinEntity> pins = pinRepository.findReleasedAndNotUsedPins(); // 모든 PIN 항목 조회

        for (WellPinEntity pin : pins) {
            String operatorName = pin.getOperatorName();
            String productName = pin.getProductName();
            String pinNum = pin.getPinNum();

            WellReleaseDTO releaseDTO = new WellReleaseDTO(operatorName, productName, pinNum);
            releaseList.add(releaseDTO);
        }
        return releaseList;
    }

    //일괄출고
    @Transactional
    public List<WellReleaseListDTO> releasePinsByRelease(String release) {
        List<WellReleaseListDTO> releaseList = getReleaseList();

        for (WellReleaseListDTO releaseDTO : releaseList) {
            String operatorName = releaseDTO.getOperatorName();
            String productName = releaseDTO.getProductName();

            pinRepository.updateReleaseAndIsUseFlag(release, true, operatorName, productName);
        }
        return releaseList;
    }

    //검색
    public Page<WellPinSearchDTO> searchPinList(Boolean isSaleFlag, Boolean isUseFlag, String network, String operatorName, String productName
            , String pinNum, String managementNum, String writer, String user, Pageable pageable) {
        Specification<WellPinEntity> spec = Specification.where(PinSpecification.isSaleFlagEquals(isSaleFlag))
                .and(PinSpecification.isUseFlagEquals(isUseFlag))
                .and(PinSpecification.networkContains(network))
                .and(PinSpecification.operatorNameContains(operatorName))
                .and(PinSpecification.productNameContains(productName))
                .and(PinSpecification.pinNumContains(pinNum))
                .and(PinSpecification.managementNumContains(managementNum))
                .and(PinSpecification.writerContains(writer))
                .and(PinSpecification.userNameContains(user));

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<WellPinEntity> pins = pinRepository.findAll(spec, pageable);

        List<WellPinSearchDTO> pinInfoList = new ArrayList<>();

        for (WellPinEntity pin : pins) {
            WellPinSearchDTO pinSearchDTO = new WellPinSearchDTO();
            pinSearchDTO.setIsSaleFlag(pin.getIsSaleFlag());
            pinSearchDTO.setIsUseFlag(pin.getIsUseFlag());
            pinSearchDTO.setNetwork(pin.getNetwork());
            pinSearchDTO.setOperatorName(pin.getOperatorName());
            pinSearchDTO.setProductName(pin.getProductName());
            pinSearchDTO.setPinNum(pin.getPinNum());
            pinSearchDTO.setManagementNum(pin.getManagementNum());
            pinSearchDTO.setWriter(pin.getWriter());
            pinSearchDTO.setUser(pin.getUserName());
            pinInfoList.add(pinSearchDTO);
        }
        return new PageImpl<>(pinInfoList, pageable, pins.getTotalElements());
    }

    //엑셀 중복값 다운로드
}
