package com.wellnetworks.wellwebapi.java.controller;
// 거래처 리스트 컨트롤러

import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerCreateDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerUpdateDTO;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.service.partner.WellPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(("/admin/hr/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class PartnerListController {

    @Autowired
    private WellPartnerService wellPartnerService;
    @Autowired
    private WellPartnerRepository wellPartnerRepository;


    //거래처 idx
    @GetMapping("business/{partnerIdx}")
    public Optional<WellPartnerInfoDTO> getPartner(@PathVariable String partnerIdx) throws ClassNotFoundException {
        Optional<WellPartnerInfoDTO> partnerInfoDTO = wellPartnerService.getPartnerByPartnerIdx(partnerIdx);
        if (partnerInfoDTO == null) {
            throw new ClassNotFoundException(String.format("IDX[%s] not found", partnerIdx));
        }
        return partnerInfoDTO;
    }

    //거래처 리스트
    @GetMapping("business")
    public List<WellPartnerInfoDTO> getPartnerList(
            @RequestParam(required = false) String partnerIdx,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date productRegisterDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") Date productModifyDate,
            @RequestParam(required = false) String partnerCode,
            @RequestParam(required = false) String partnerName,
            @RequestParam(required = false) String partnerType,
            @RequestParam(required = false) String discountCategory,
            @RequestParam(required = false) Integer dipositBalance,
            @RequestParam(required = false) String salesManager,
            @RequestParam(required = false) String ceoName,
            @RequestParam(required = false) String partnerTelephone,
            @RequestParam(required = false) String writer,
            @RequestParam(required = false) Integer transactionStatus,
            @RequestParam(required = false) String partnerUpperId
    ) {
        List<WellPartnerInfoDTO> partnerList = wellPartnerService.getAllPartners();

        return partnerList;
    }

    //거래처 입력
    @PostMapping(value = "business/create")
    public ResponseEntity<String> createPartner(WellPartnerCreateDTO createDTO) throws Exception {
         wellPartnerService.join(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("거래처가 성공적으로 생성되었습니다.");
    }

    //거래처 수정
    @PatchMapping("business/update")
    public ResponseEntity<String> patchPartner(WellPartnerUpdateDTO updateDTO) throws Exception {
        wellPartnerService.update(updateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("수정 완료.");
    }

    //거래처 검색
    @GetMapping("business/search")
    public List<WellPartnerInfoDTO> searchPartner(
            @RequestParam(value = "partnerName", required = false) String partnerName
            , @RequestParam(value = "ceoName", required = false) String ceoName
            , @RequestParam(value = "ceoTelephone", required = false) String ceoTelephone
            , @RequestParam(value = "partnerCode", required = false) String partnerCode
            , @RequestParam(value = "address", required = false) String address
            , @RequestParam(value = "writer", required = false) String writer
            , @RequestParam(value = "partnerTelephone", required = false) String partnerTelephone
            , @RequestParam(value = "startDate", required = false) LocalDate startDate
            , @RequestParam(value = "endDate", required = false) LocalDate endDate
            , @RequestParam(value = "discountCategory", required = false) String discountCategory
            , @RequestParam(value = "partnerType", required = false) String partnerType
            , @RequestParam(value = "salesManager", required = false) String salesManager
            , @RequestParam(value = "transactionStatus", required = false) String transactionStatus
            , @RequestParam(value = "regionAddress", required = false) String regionAddress
    ) {

        return wellPartnerService.searchPartnerList(partnerName, ceoName, ceoTelephone, partnerCode, address, writer, partnerTelephone, startDate, endDate
                , discountCategory, partnerType, salesManager, transactionStatus, regionAddress);
    }



    // 거래처 체크항목 삭제
    @DeleteMapping("business/{partnerIdx}")
    public ResponseEntity<String> deletePartner(@PathVariable String partnerIdx) throws ClassNotFoundException {
        Optional<WellPartnerInfoDTO> partnerInfoDTO = wellPartnerService.deletePartnerIdx(partnerIdx);

        if (!partnerInfoDTO.isPresent()) {
            // 삭제 대상을 찾지 못한 경우 404 Not Found를 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("IDX[%s] not found", partnerIdx));
        } else {
            // 삭제 성공한 경우 204 No Content를 반환
            return ResponseEntity.noContent().build();
        }
    }




}
