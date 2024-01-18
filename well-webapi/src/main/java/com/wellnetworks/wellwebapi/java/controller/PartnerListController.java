package com.wellnetworks.wellwebapi.java.controller;
// 거래처 리스트 컨트롤러

import com.wellnetworks.wellcore.java.dto.Partner.*;
import com.wellnetworks.wellcore.java.dto.Partner.sign.WellPartnerSignCreateDTO;
import com.wellnetworks.wellcore.java.dto.Partner.sign.WellPartnerSignInfoDTO;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.service.partner.WellPartnerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping(("/admin/hr/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore","com.wellnetworks.wellsecure"})
public class PartnerListController {

    @Autowired private WellPartnerService wellPartnerService;
    @Autowired private WellPartnerRepository partnerRepository;

    //상세 거래처 idx 조회
    @GetMapping("business/detail/{partnerIdx}")
    public ResponseEntity<?> getDetailPartner(@PathVariable String partnerIdx) {
        try {
            Optional<WellPartnerDetailDTO> partnerDetailDTO = wellPartnerService.getDetailPartnerByPartnerIdx(partnerIdx);

            if (partnerDetailDTO.isPresent()) {
                return ResponseEntity.ok(partnerDetailDTO.get());
            } else {
                // 데이터가 없는 경우 404 Not Found 반환
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("거래처 상세 정보를 찾을 수 없습니다. IDX: %s", partnerIdx));
            }
        } catch (EntityNotFoundException e) {
            // EntityNotFoundException이 발생한 경우 404 Not Found 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("거래처 상세 정보를 찾을 수 없습니다. IDX: %s", partnerIdx));
        } catch (Exception e) {
            // 다른 예외가 발생한 경우 500 Internal Server Error 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }

    //상세 거래처 idx 조회
    @GetMapping("businessSign/detail/{partnerIdx}")
    public ResponseEntity<?> getDetailPartnerSign(@PathVariable String partnerIdx) {
//        try {
            Optional<WellPartnerDetailDTO> partnerDetailDTO = wellPartnerService.getDetailPartnerByPartnerIdx(partnerIdx);

            if (partnerDetailDTO.isPresent()) {
                return ResponseEntity.ok(partnerDetailDTO.get());
            } else {
                // 데이터가 없는 경우 404 Not Found 반환
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("거래처 상세 정보를 찾을 수 없습니다. IDX: %s", partnerIdx));
            }
//        } catch (EntityNotFoundException e) {
            // EntityNotFoundException이 발생한 경우 404 Not Found 반환
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("거래처 상세 정보를 찾을 수 없습니다. IDX: %s", partnerIdx));
//        } catch (Exception e) {
            // 다른 예외가 발생한 경우 500 Internal Server Error 반환
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
//        }
    }




    //거래처 리스트
    @GetMapping("business")
    public ResponseEntity<?> getPartnerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "productRegisterDate"));
            Page<WellPartnerInfoDTO> partnersPage = wellPartnerService.getAllPartners(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("currentPage", partnersPage.getNumber());
            response.put("items", partnersPage.getContent());
            response.put("message", "");
            response.put("status", "OK");
            response.put("totalItems", partnersPage.getTotalElements());
            response.put("totalPages", partnersPage.getTotalPages());

            response.put("registeredCount", partnerRepository.registeredCount());
            response.put("preRegisteredCount", partnerRepository.preRegisteredCount());
            response.put("managementCount", partnerRepository.managementCount());
            response.put("suspendedCount", partnerRepository.suspendedCount());
            response.put("businessLicenseCount", partnerRepository.countBusinessLicenseMissing());
            response.put("contractDocumentCount", partnerRepository.countContractDocumentMissing());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 예외가 발생하면 500 Internal Server Error 응답을 반환
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "서버 오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }


    //거래처 회원가입 리스트
    @GetMapping("businessSign")
    public ResponseEntity<?> getPartnerSignList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "productRegisterDate"));
            Page<WellPartnerSignInfoDTO> partnersPage = wellPartnerService.getAllPartnerSign(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("currentPage", partnersPage.getNumber());
            response.put("items", partnersPage.getContent());
            response.put("message", "");
            response.put("status", "OK");
            response.put("totalItems", partnersPage.getTotalElements());
            response.put("totalPages", partnersPage.getTotalPages());

            response.put("registeredCount", partnerRepository.registeredCount());
            response.put("preRegisteredCount", partnerRepository.preRegisteredCount());
            response.put("managementCount", partnerRepository.managementCount());
            response.put("suspendedCount", partnerRepository.suspendedCount());
            response.put("businessLicenseCount", partnerRepository.countBusinessLicenseMissing());
            response.put("contractDocumentCount", partnerRepository.countContractDocumentMissing());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 예외가 발생하면 500 Internal Server Error 응답을 반환
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "서버 오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }





    //거래처 생성
    @PostMapping(value = "business/create")
    public ResponseEntity<String> createPartner(@Valid WellPartnerCreateDTO createDTO) throws Exception {

            String tempPassword = wellPartnerService.join(createDTO);

            // 콘솔에 임시 비밀번호 출력
            return ResponseEntity.status(HttpStatus.CREATED).body("거래처가 성공적으로 생성되었습니다. 생성된 아이디"+" 임시 비밀번호: " + tempPassword);
    }

    //거래처 회원가입 신청 입력
    @PostMapping(value = "businessSign/create")
    public ResponseEntity<String> createPartnerSign(@Valid WellPartnerSignCreateDTO signCreateDTO) throws Exception {

        wellPartnerService.signJoin(signCreateDTO);

        // 콘솔에 임시 비밀번호 출력
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 성공적으로 신청되었습니다.");

    }

    //거래처 수정
    @PatchMapping("business/update/{partnerIdx}")
    public ResponseEntity<String> patchPartner(@Valid WellPartnerUpdateDTO updateDTO,
                                               @PathVariable String partnerIdx) {
        try {
            if (partnerIdx == null) {
                throw new ClassNotFoundException(String.format("IDX[%s] not found", partnerIdx));
            }
            wellPartnerService.update(partnerIdx, updateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("거래처가 성공적으로 수정되었습니다.");
        } catch (ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("거래처를 찾을 수 없습니다. IDX: %s", partnerIdx));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("거래처 수정 중 오류가 발생하였습니다.");
        }
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
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "productRegisterDate"));
            Page<WellPartnerSearchDTO> partnersPage = wellPartnerService.searchPartnerList(partnerName, ceoName, ceoTelephone, partnerCode, address, writer, partnerTelephone, startDate, endDate
                    , discountCategory, partnerType, salesManager, transactionStatus, regionAddress, partnerUpperIdx, hasBusinessLicense, hasContractDocument, pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("currentPage", partnersPage.getNumber());
            response.put("items", partnersPage.getContent());
            response.put("message", "");
            response.put("status", "OK");
            response.put("totalItems", partnersPage.getTotalElements());
            response.put("totalPages", partnersPage.getTotalPages());

            response.put("registeredCount", partnerRepository.registeredCount());
            response.put("preRegisteredCount", partnerRepository.preRegisteredCount());
            response.put("managementCount", partnerRepository.managementCount());
            response.put("suspendedCount", partnerRepository.suspendedCount());
            response.put("businessLicenseCount", partnerRepository.countBusinessLicenseMissing());
            response.put("contractDocumentCount", partnerRepository.countContractDocumentMissing());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 검색 중 예외 발생 시 500 Internal Server Error 응답 반환
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "거래처 검색 중 오류 발생: " + e.getMessage());
            errorResponse.put("status", "ERROR");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }




    // 거래처 체크항목 삭제
    @DeleteMapping("business/delete/{partnerIdx}")
    public ResponseEntity<String> deletePartner(@PathVariable String partnerIdx) {
        try {
            wellPartnerService.deletePartnerIdx(partnerIdx);
            return ResponseEntity.status(HttpStatus.CREATED).body("거래처가 성공적으로 삭제되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("삭제할 거래처를 찾을 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("거래처 삭제 중 오류 발생: " + e.getMessage());
        }
    }


}
