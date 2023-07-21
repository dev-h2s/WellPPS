package com.wellnetworks.wellwebapi.controller.admin

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wellnetworks.wellcore.domain.dto.WellOpeningDTO
import com.wellnetworks.wellcore.domain.dto.WellOpeningDTOUpdate
import com.wellnetworks.wellcore.domain.dto.WellProductDTOUpdate
import com.wellnetworks.wellcore.domain.dto.WellProductDTOs
import com.wellnetworks.wellcore.service.WellOpeningService
import com.wellnetworks.wellcore.service.utils.SearchCriteria
import com.wellnetworks.wellwebapi.response.BaseItemRes
import com.wellnetworks.wellwebapi.response.BaseListRes
import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/admin/open/")
class OpeningController(private var openingService: WellOpeningService) {

    @PostMapping("opening",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    fun createOpening(@RequestPart("opening") openingJsonString: String): ResponseEntity<BaseRes> {
        val mapper = jacksonObjectMapper()

        try{
            val openingObj = mapper.readValue(openingJsonString, WellOpeningDTO::class.java)

            if(!openingService.createOpening(openingObj))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "개통내역 추가 실패"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "개통내역 추가 성공"))
    }

    @GetMapping("opening/{id}")
    fun getOpening(@PathVariable id: String): ResponseEntity<BaseItemRes<WellOpeningDTO>> {
        val idx: String
        try {
            idx = UUID.fromString(id).toString().uppercase()
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseItemRes(HttpStatus.BAD_REQUEST, "문서 번호가 잘못되었습니다."))
        }

        val opening = openingService.getOpeningByIdx(idx)

        if (opening.isEmpty)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseItemRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))

        return ResponseEntity.ok(BaseItemRes(HttpStatus.OK, "", opening.get()))
    }

    @GetMapping("opening")
    fun getOpeningList(
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("page", defaultValue = "0") page: Int,
    ): ResponseEntity<BaseListRes<WellOpeningDTO>>{
        val searchKeywords: MutableList<SearchCriteria> = mutableListOf()

        val pageable: Pageable = PageRequest.of(page, size)

        // Todo : 검색조건 추가

        val openingList = openingService.searchOpening(pageable, searchKeywords)

        return ResponseEntity.ok(
            BaseListRes(HttpStatus.OK, "",
                openingList.content, openingList.number, openingList.totalElements, openingList.totalPages)
        )
    }

    @PutMapping("opening")
    fun updateOpening(@RequestPart("opening") opening: String) : ResponseEntity<BaseRes>{

        val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())

        try {
            val openingObj = mapper.readValue(opening, WellOpeningDTOUpdate::class.java)

            if (!openingService.updateOpening(openingObj))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트 실패"))

        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "업데이트 성공"))
    }

    @DeleteMapping("opening/{id}")
    fun deleteOpening(@PathVariable id: String) : ResponseEntity<BaseRes> {
        val uuidIdx: String

        try{
            uuidIdx = UUID.fromString(id).toString()
            openingService.deleteOpeningById(uuidIdx)
        }catch (e: Exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))
        }
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "삭제 성공"))
    }
}