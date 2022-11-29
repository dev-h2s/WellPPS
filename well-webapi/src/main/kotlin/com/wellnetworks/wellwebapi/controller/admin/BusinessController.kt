package com.wellnetworks.wellwebapi.controller.admin

import com.wellnetworks.wellcore.service.WellPartnerService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/hr/")
class BusinessController(private var wellPartnerService: WellPartnerService) {

    @GetMapping("business")
    fun getPartner() {

    }

    @GetMapping("business")
    fun getPartner(@RequestParam user_idx: String, @RequestParam p_code: String) {

    }
}