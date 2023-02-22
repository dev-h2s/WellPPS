package com.wellnetworks.wellwebapi.controller.admin

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wellnetworks.wellcore.domain.dto.*
import com.wellnetworks.wellcore.domain.enums.*
import com.wellnetworks.wellcore.service.utils.SearchCriteria
import com.wellnetworks.wellcore.service.WellPartnerService
import com.wellnetworks.wellwebapi.response.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

@RestController
@RequestMapping("/admin/hr/")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class BusinessController(private var partnerService: WellPartnerService) {

    @GetMapping("business/{id}")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER)) or" +
            " hasRole(MenuPermissionUtil.Companion.BuildPermissionString(MENU_PERMISSION_PARTNER, MENU_PERMISSION_TYPE_READ))" )
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
    //@WellPermissionChecker([PermissionKey.PERMISSION_LOGIN])
/*    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.MenuPermissionUtil).Companion.BuildPermissionString(MENU_PERMISSION_PARTNER, MENU_PERMISSION_TYPE_READ)) )")*/
    fun getPartnerList(
        @RequestParam("from_date", required = false) startDate: String?,
        @RequestParam("to_date", required = false) endDate: String?,
        @RequestParam("pcode", required = false) pCode: String?,
        @RequestParam("c_name", required = false) partnerName: String?,
        @RequestParam("c_type", required = false) partnerType: Int?,
//        @RequestParam("dtyp", required = false) discountType: String?,
        @RequestParam("con_person", required = false) manager: String?,
        @RequestParam("ceo_name", required = false) ceoName: String?,
        @RequestParam("ceo_tel", required = false) ceoTelephone: String?,
        @RequestParam("c_status", required = false) status: Int?,
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
        val dtStartFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val dtEndFormatter = DateTimeFormatter.ofPattern("yyyyMMdd HH:mm:ss.SSSSSSS")

        val PermissionKeyList = PermissionKey.asMap()
        print(PermissionKeyList)

        if (!startDate.isNullOrEmpty()) {
            val startDateParse = LocalDate.parse(startDate, dtStartFormatter).atStartOfDay(ZoneId.systemDefault())
            searchKeywords.add(
                SearchCriteria(
                    WellPartnerColumnsName.Register_Datetime.columnsName,
                    ">",
                    ZonedDateTime.ofInstant(startDateParse.toInstant(), ZoneOffset.UTC)
                )
            )
        }
        if (!endDate.isNullOrEmpty()) {
            val endDateParse =
                LocalDateTime.parse("$endDate 23:59:59.9999999", dtEndFormatter).atZone(ZoneId.systemDefault());
            searchKeywords.add(
                SearchCriteria(
                    WellPartnerColumnsName.Register_Datetime.columnsName,
                    "<",
                    ZonedDateTime.ofInstant(endDateParse.toInstant(), ZoneOffset.UTC)
                )
            )
        }
        if (!pCode.isNullOrEmpty()) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.P_Code.columnsName, "=", pCode))
        if (!partnerName.isNullOrEmpty()) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.Company_Name.columnsName, "%%", partnerName))
        if (partnerType != null) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.Company_Type.columnsName, "=", CompanyType.from(partnerType!!)!!))
        if (!manager.isNullOrEmpty()) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.Contact_Person.columnsName, "=", manager))
        if (!ceoName.isNullOrEmpty()) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.CEO_Name.columnsName, "=", ceoName))
        if (!ceoTelephone.isNullOrEmpty()) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.CEO_Telephone.columnsName, "=", ceoTelephone))
        if (status != null) searchKeywords.add(SearchCriteria(WellPartnerColumnsName.Company_State.columnsName, "=", CompanyStateType.from(status!!)!!))

        val partnerList = partnerService.searchPartner(pageable, searchKeywords)


        return ResponseEntity.ok(BaseListRes(HttpStatus.OK, "",
            partnerList.content, partnerList.number, partnerList.totalElements, partnerList.totalPages))
    }

    @PostMapping("business",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")
    fun createPartner(@RequestPart("user") userJsonString: String,
                      @RequestPart("partner") partnerJsonString: String,
                      @RequestPart("file") files: List<MultipartFile>
    ): ResponseEntity<BaseRes> {
        val mapper = jacksonObjectMapper()

        try {
            val userObj = mapper.readValue(userJsonString, WellUserDTOCreate::class.java)
            val partnerObj = mapper.readValue(partnerJsonString, WellPartnerDTOCreate::class.java)

            if (!partnerService.creaetPartner(userObj, partnerObj, files))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "파트너 추가 실패"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "파트너 추가 성공"))
    }

    @DeleteMapping("business/{id}")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")
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

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "삭제 성공"))
    }


    @PutMapping("business")
    @PreAuthorize("isAuthenticated() and" +
        " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
        " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")

    //(@PathVariable id: String): ResponseEntity<BaseItemRes<WellPartnerDTO>> {
    //fun updatePartner(@RequestPart("partner") partner: String, @RequestPart("file") files: List<MultipartFile>): ResponseEntity<BaseRes> {
    fun updatePartner(@RequestPart("partner") partner: String, @RequestPart("file", required = false) files: List<MultipartFile>?): ResponseEntity<BaseRes> {

        val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())

        try {
            val partnerObj = mapper.readValue(partner, WellPartnerDTOUpdate::class.java)

            if (!partnerService.updatePartner(partnerObj, files))
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
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")*/
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
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER))")*/
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

    @GetMapping("partner/param_companytype")
    @PreAuthorize("isAuthenticated() and" +
            " (hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).SUPER_ADMIN.permitssionKey) or" +
            " hasRole(T(com.wellnetworks.wellcore.domain.enums.PermissionKey).MEMBER.permitssionKey))")
    fun paramCompanyType(): ResponseEntity<BaseListRes<ParamEnumItemRes>> {
        var paramCompanyTypeList : MutableList<ParamEnumItemRes> = mutableListOf()
        for (item in CompanyType.values()) {
            paramCompanyTypeList.add(ParamEnumItemRes(
                item.index(),
                item.key,
                item.toString()
            ))
        }

        return ResponseEntity.ok(
            BaseListRes<ParamEnumItemRes>(
                HttpStatus.OK, "",
                paramCompanyTypeList,
            )
        )
    }

}