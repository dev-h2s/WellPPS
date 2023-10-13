package com.wellnetworks.wellwebapi.java.controller;
// 거래처 리스트 컨트롤러

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.service.WellPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartnerListController {

//    @Autowired
//    private WellPartnerService wellPartnerService;
//
//    // 페이지네이션 거래처
//    @GetMapping("/partners")
//    public Page<WellPartnerEntity> getPaginatedPartners(@RequestParam int page, @RequestParam int size) {
//        return wellPartnerService.getPaginatedPartners(page, size);
//    }
}
