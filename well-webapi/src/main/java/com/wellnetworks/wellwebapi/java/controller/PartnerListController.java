package com.wellnetworks.wellwebapi.java.controller;
// 거래처 리스트 컨트롤러

import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerDetailDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerCreateDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerUpdateDTO;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.service.partner.WellPartnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@RestController
@RequestMapping(("/admin/hr/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore","com.wellnetworks.wellsecure"})
public class PartnerListController {

    @Autowired private WellPartnerService wellPartnerService;


    //거래처 idx
    @GetMapping("business/{partnerIdx}")
    public Optional<WellPartnerInfoDTO> getPartner(@PathVariable String partnerIdx) throws ClassNotFoundException {
        Optional<WellPartnerInfoDTO> partnerInfoDTO = wellPartnerService.getPartnerByPartnerIdx(partnerIdx);
        if (partnerInfoDTO == null) {
            throw new ClassNotFoundException(String.format("IDX[%s] not found", partnerIdx));
        }
        return partnerInfoDTO;
    }

    //상세 거래처 idx
    @GetMapping("business/detail/{partnerIdx}")
    public Optional<WellPartnerDetailDTO> getDetailPartner(@PathVariable String partnerIdx) throws ClassNotFoundException {
        Optional<WellPartnerDetailDTO> partnerDetailDTO = wellPartnerService.getDetailPartnerByPartnerIdx(partnerIdx);
        if (partnerDetailDTO == null) {
            throw new ClassNotFoundException(String.format("IDX[%s] not found", partnerIdx));
        }
        return partnerDetailDTO;
    }

    //거래처 리스트
    @GetMapping("business")
    public ResponseEntity<Map<String, Object>> getPartnerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "productRegisterDate"));
        Page<WellPartnerInfoDTO> partnersPage = wellPartnerService.getAllPartners(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", partnersPage.getNumber());
        response.put("items", partnersPage.getContent());
        response.put("message", "");
        response.put("status", "OK");
        response.put("totalItems", partnersPage.getTotalElements());
        response.put("totalPages", partnersPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    //거래처 생성
    @PostMapping(value = "business/create")
    public ResponseEntity<String> createPartner(@Valid WellPartnerCreateDTO createDTO) throws Exception {
        try {
            String tempPassword = wellPartnerService.join(createDTO);

            // 콘솔에 임시 비밀번호 출력
            return ResponseEntity.status(HttpStatus.CREATED).body("거래처가 성공적으로 생성되었습니다. 생성된 아이디"+" 임시 비밀번호: " + tempPassword);
        } catch (Exception e) {
//            // 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원가입 중 오류가 발생하였습니다.");
        }
    }

    //거래처 수정
    @PatchMapping("business/update/{partnerIdx}")
    public ResponseEntity<String> patchPartner(@Valid WellPartnerUpdateDTO updateDTO,
                                               @PathVariable String partnerIdx) throws Exception {
        wellPartnerService.update(partnerIdx, updateDTO);
        if (partnerIdx == null) {
            throw new ClassNotFoundException(String.format("IDX[%s] not found", partnerIdx));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("거래처가 성공적으로 수정되었습니다.");
    }

    // 거래처 검색
    @GetMapping("business/search")
    public ResponseEntity<Map<String, Object>> searchPartner(
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
            , @RequestParam(value = "partnerUpperIdx", required = false) String partnerUpperIdx
            , @RequestParam(value = "hasBusinessLicense", required = false) Boolean hasBusinessLicense
            , @RequestParam(value = "hasContractDocument", required = false) Boolean hasContractDocument
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "productRegisterDate"));
        Page<WellPartnerInfoDTO> partnersPage = wellPartnerService.searchPartnerList(partnerName, ceoName, ceoTelephone, partnerCode, address, writer, partnerTelephone, startDate, endDate
                , discountCategory, partnerType, salesManager, transactionStatus, regionAddress, partnerUpperIdx, hasBusinessLicense, hasContractDocument, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("currentPage", partnersPage.getNumber());
        response.put("items", partnersPage.getContent());
        response.put("message", "");
        response.put("status", "OK");
        response.put("totalItems", partnersPage.getTotalElements());
        response.put("totalPages", partnersPage.getTotalPages());

        return ResponseEntity.ok(response);
    }



    // 거래처 체크항목 삭제
    @DeleteMapping("business/delete/{partnerIdx}")
    public ResponseEntity<String> deletePartner(@PathVariable String partnerIdx) throws Exception {
        wellPartnerService.deletePartnerIdx(partnerIdx);

        return ResponseEntity.status(HttpStatus.CREATED).body("거래처가 성공적으로 삭제되었습니다.");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return errors;
    }
}
