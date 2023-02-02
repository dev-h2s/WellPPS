package com.wellnetworks.wellwebapi.controller.admin

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.service.utils.SearchCriteria
import com.wellnetworks.wellcore.service.WellPartnerService
import com.wellnetworks.wellwebapi.response.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PostAuthorize
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RestController
@RequestMapping("/admin/hr/")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class BusinessController(private var partnerService: WellPartnerService) {

    @GetMapping("business/{id}")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")
    fun getPartner(@PathVariable id: String): ResponseEntity<BaseItemRes<WellPartnerDTO>> {
        val uuidIdx: String
        try {
            uuidIdx = UUID.fromString(id).toString()
        }catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseItemRes(HttpStatus.BAD_REQUEST, "문서 번호가 잘못되었습니다."))
        }

        val partner = partnerService.getPartnerByIdx(uuidIdx)

        if (partner.isEmpty)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseItemRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))

        return ResponseEntity.ok(BaseItemRes(HttpStatus.OK, "", partner.get()))
    }

    @GetMapping("business")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")
    fun getPartnerList(
        @RequestParam("std", required = false) startDate: String?,
        @RequestParam("edt", required = false) endDate: String?,
        @RequestParam("p", required = false) pCode: String?,
        @RequestParam("pnam", required = false) partnerName: String?,
        @RequestParam("ptyp", required = false) partnerType: String?,
//        @RequestParam("dtyp", required = false) discountType: String?,
        @RequestParam("man", required = false) manager: String?,
        @RequestParam("ceo", required = false) ceoName: String?,
        @RequestParam("tel", required = false) ceoTelephone: String?,
        @RequestParam("sts", required = false) status: Int?,
//        @RequestParam("upp", required = false) upper: String?,
//        @RequestParam("lic", required = false) licenseAttached: Boolean?,
//        @RequestParam("con", required = false) contractAttached: Boolean?,
//        @RequestParam("dist", required = false) districts: String?,
//        @RequestParam("addr", required = false) address: String?,
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("page", defaultValue = "0") page: Int,
    ): ResponseEntity<BaseListRes<WellPartnerDTO>> {
        val searchKeywords: MutableList<SearchCriteria> = mutableListOf()

        val pageable: Pageable = PageRequest.of(page, size)
        val dtFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

        if (!startDate.isNullOrEmpty()) {
            val startDateParse = LocalDate.parse(startDate, dtFormatter).atStartOfDay(ZoneId.systemDefault());
            searchKeywords.add(SearchCriteria(WellPartnerColumnsName.Register_Datetime.columnsName, ">", startDateParse.toInstant()))
        }
        if (!endDate.isNullOrEmpty()) {
            val endDateParse = LocalDate.parse(endDate, dtFormatter).atStartOfDay(ZoneId.systemDefault());
            searchKeywords.add(SearchCriteria(WellPartnerColumnsName.Register_Datetime.columnsName, "<", endDateParse.toInstant()))
        }
        if (!pCode.isNullOrEmpty()) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.P_Code.columnsName, "=", pCode))
        if (!partnerName.isNullOrEmpty()) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.Company_Name.columnsName, "%", partnerName))
        if (!partnerType.isNullOrEmpty()) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.Company_Type.columnsName, "=", partnerType))
        if (!manager.isNullOrEmpty()) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.Contact_Person.columnsName, "=", manager))
        if (!ceoName.isNullOrEmpty()) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.CEO_Name.columnsName, "=", ceoName))
        if (!ceoTelephone.isNullOrEmpty()) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.CEO_Telephone.columnsName, "=", ceoTelephone))
        if (status != null) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.Company_State.columnsName, "=", status))

        val partnerList = partnerService.searchPartner(pageable, searchKeywords)


        return ResponseEntity.ok(BaseListRes(HttpStatus.OK, "",
            partnerList.content, partnerList.number, partnerList.totalElements, partnerList.totalPages))
    }

    @PostMapping("business",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")
    fun createPartner(@RequestPart("user") userJsonString: String,
                      @RequestPart("partner") partnerJsonString: String,
                      @RequestPart("file") files: List<MultipartFile>
    ): ResponseEntity<BaseRes> {
        val mapper = jacksonObjectMapper()

        try {
            val user = mapper.readValue(userJsonString, WellUserDTOCreate::class.java)
            val partner = mapper.readValue(partnerJsonString, WellPartnerDTOCreate::class.java)

            if (!partnerService.creaetPartner(user, partner, files))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "파트너 추가 실패"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "파트너 추가 성공"))
    }

    @DeleteMapping("business/{id}")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")
    fun deletePartner(@PathVariable id: String) : ResponseEntity<BaseRes> {
        val uuidIdx: String

        try{
            uuidIdx = UUID.fromString(id).toString()
        }catch (e: java.lang.Exception){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, "문서 번호가 잘못되었습니다."))
        }

        try {
            partnerService.deletePartnerById(uuidIdx)
        } catch (e: Exception) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(BaseRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "delete ok"))
    }


    @PutMapping("business")
    @PreAuthorize("isAuthenticated() and" +
        " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
        " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")

    //(@PathVariable id: String): ResponseEntity<BaseItemRes<WellPartnerDTO>> {
    //fun updatePartner(@RequestPart("partner") partner: String, @RequestPart("file") files: List<MultipartFile>): ResponseEntity<BaseRes> {
    fun updatePartner(@RequestPart("partner") partner: String, @RequestPart("file", required = false) files: List<MultipartFile>?): ResponseEntity<BaseRes> {

        val mapper = jacksonObjectMapper()

        try {
            val partner = mapper.readValue(partner, WellPartnerDTOUpdate::class.java)

            if (!partnerService.updatePartner(partner, files))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트 실패"))

        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "업데이트 성공"))
        /*
        if (!partnerService.updatePartner(partner, files))
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트 실패"))

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "업데이트 성공"))
         */

    }

    @GetMapping("partner/count")
/*    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")*/
    fun cntPartner(): ResponseEntity<PartnerCompanyTypeCountRes> {

        val cnt = partnerService.companyTypeCount()
        val regCnt = cnt.regCount
        val tempCnt = cnt.tempCount
        val watchCnt = cnt.watchCount
        val susCnt = cnt.susCount

        return ResponseEntity.ok(
            PartnerCompanyTypeCountRes(
                regCnt, tempCnt, watchCnt, susCnt
            )
        )
    }

    @GetMapping("partner/unattached_count")
/*    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_SUPERADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionList).PERMISSION_MEMBER.permitssionKey))")*/
    fun taxUnattachedCount(): ResponseEntity<PartnerUnattachedCountRes> {

        val count = partnerService.partnerUnattachedTaxCount()
        val taxCount = count.taxIdxCount
        val contractCount = count.contractIdxCount


        return ResponseEntity.ok(
            PartnerUnattachedCountRes(
                taxCount, contractCount
            )
        )
    }

}