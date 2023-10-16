package com.wellnetworks.wellwebapi.java.controller;
// 거래처 리스트 컨트롤러

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import com.wellnetworks.wellcore.java.service.WellPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(("/admin/hr/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class PartnerListController {

    @Autowired private WellPartnerService wellPartnerService;

    //거래처 idx
    @GetMapping("business/{partnerIdx}")
    public void getPartner(@RequestParam String partnerIdx) {
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

    @PostMapping("business")
    public void postPartner() {
    }

    @PatchMapping("business")
    public void patchPartner() {
    }

    @DeleteMapping("business")
    public void deletePartner() {
    }

}
