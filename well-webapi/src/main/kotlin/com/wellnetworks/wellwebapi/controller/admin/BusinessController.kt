package com.wellnetworks.wellwebapi.controller.admin

import com.wellnetworks.wellcore.service.WellPartnerService
import com.wellnetworks.wellcore.domain.enums.PermissionList
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.Date

@RestController
@RequestMapping("/admin/hr/")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class BusinessController(private var wellPartnerService: WellPartnerService) {

    @GetMapping("business/{id}")
    @PostAuthorize("isAuthenticated() and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN) and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER)")
    fun getPartner(@PathVariable id: String) {

    }

    @GetMapping("business")
    @PostAuthorize("isAuthenticated() and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN) and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER)")
    fun getPartnerList(
        @RequestParam(required = false) p: Int,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") sdt: Date,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyyMMdd") edt: Date,
        @RequestParam(required = false) p_code: String,
        @RequestParam(required = false) u_name: String,
        @RequestParam(required = false) u_type: String,
        @RequestParam(required = false) d_type: String,
        @RequestParam(required = false) man: String,
        @RequestParam(required = false) ceo: String,
        @RequestParam(required = false) tell: String,
        @RequestParam(required = false) state: Int,
        @RequestParam(required = false) upper: String,
        @RequestParam(required = false) lic: Boolean,
        @RequestParam(required = false) con: Boolean,
        @RequestParam(required = false) dist: String,
        @RequestParam(required = false) addr: String,
    ) {

    }

    @PostMapping("business")
    @PostAuthorize("isAuthenticated() and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN) and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER)")
    fun createPartner() {

    }

    @DeleteMapping("business")
    @PostAuthorize("isAuthenticated() and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN) and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER)")
    fun deletePartner() {

    }

    @PutMapping("business")
    @PostAuthorize("isAuthenticated() and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN) and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER)")
    fun updatePartner() {

    }
}