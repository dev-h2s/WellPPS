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
// RestController는 Controller에서 @ResponseBody 붙은 효과(JSON/XML형태로 객체 데이터 반환)
@RequestMapping("/admin/hr/")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// EnableGlobalMethodSecurity : Spring Security를 사용하여 메소드 수준의 보안을 활성화, 보안 규칙을 정의할 수 있도록 구성
class BusinessController(private var wellPartnerService: WellPartnerService) {

    // PostAuthorize : 메소드 실행 후에 보안 검사를 수행하는 데 사용
    // isAuthenticated : 현재 사용자가 인증되었는지 확인
    // hasRole : 권한 확인
    @GetMapping("business/{id}")
    @PostAuthorize("isAuthenticated() and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN) and" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER)")
    // PathVariable : 웹 요청 URL에서 경로 변수를 추출하여 메소드 파라미터로 전달
    // 메소드 내에서 id 변수를 사용할 수 있음 (id 변수는 요청 URL에서 추출된 동적인 값)
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