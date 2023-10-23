package com.wellnetworks.wellwebapi.java.controller;
// 거래처 리스트 컨트롤러

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerCreateDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import com.wellnetworks.wellcore.java.dto.PartnerGroup.WellPartnerGroupCreateDTO;
import com.wellnetworks.wellcore.java.service.WellPartnerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/admin/hr/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class PartnerListController {

    @Autowired
    private WellPartnerService wellPartnerService;

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
    @PostMapping("partners/create")
    public ResponseEntity<String> createPartner(@RequestBody WellPartnerCreateDTO createDTO) {
        wellPartnerService.join(createDTO); // 거래처 그룹 ID를 함께 전달
        return ResponseEntity.status(HttpStatus.CREATED).body("거래처가 성공적으로 생성되었습니다.");
    }




    //거래처 수정
    @PatchMapping("business")
    public void patchPartner() {
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


    //거래처 그룹
    @GetMapping("group")
    public ResponseEntity<List<WellPartnerGroupCreateDTO>> getAllGroups() {
        List<WellPartnerGroupEntity> groupEntities = wellPartnerService.getAllGroups();

        // 엔티티를 DTO로 변환
        List<WellPartnerGroupCreateDTO> groupDTOs = groupEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(groupDTOs);
    }

    // 엔티티를 DTO로 변환하는 메서드
    private WellPartnerGroupCreateDTO convertToDTO(WellPartnerGroupEntity entity) {
        WellPartnerGroupCreateDTO dto = new WellPartnerGroupCreateDTO();
        dto.setPartnerGroupId(entity.getPartnerGroupId());
        // 나머지 필드에 대한 매핑

        return dto;
    }

}
