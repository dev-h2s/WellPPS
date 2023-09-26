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
// RestController는 Controller에서 @ResponseBody 붙은 효과(JSON/XML형태로 객체 데이터 반환)
@RequestMapping("/admin/open/")
class OpeningController(private var openingService: WellOpeningService) {

    @PostMapping("opening",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
        // 파일 업로드와 같은 경우 사용 / json 형식 데이터 받는데 사용
    // 개통 내역 생성
    fun createOpening(@RequestPart("opening") openingJsonString: String): ResponseEntity<BaseRes> {
        val mapper = jacksonObjectMapper()

        try{
            val openingObj = mapper.readValue(openingJsonString, WellOpeningDTO::class.java)
        // 개통 내역을 생성하고 생성이 실패한 경우 500 Internal Server Error 응답을 반환
            if(!openingService.createOpening(openingObj))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "개통내역 추가 실패"))
        } catch (e: Exception) {
            // 잘못된 요청 또는 예외 발생 시 400(Bad Request)과 함께 오류 메시지를 반환
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }
        // 성공적으로 개통 내역을 생성했을 때 200(OK)과 성공 메시지를 반환.
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "개통내역 추가 성공"))
    }

    @GetMapping("opening/{id}")
    // 개통 내역의 정보를 조회
    fun getOpening(@PathVariable id: String): ResponseEntity<BaseItemRes<WellOpeningDTO>> {
        val idx: String

        try {
    // 입력받은 id를 uuid로 바꾸고 대문자 표시
            idx = UUID.fromString(id).toString().uppercase()
        } catch (e: Exception) {
            //잘못 요청시 오류 메세지
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseItemRes(HttpStatus.BAD_REQUEST, "문서 번호가 잘못되었습니다."))
        }
    // 입력된 ID에 해당하는 개통 내역을 조회하고 실패시 오류문구 출력
        val opening = openingService.getOpeningByIdx(idx)

        if (opening.isEmpty)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseItemRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))
    // 성공시 조회된 데이터 반환
        return ResponseEntity.ok(BaseItemRes(HttpStatus.OK, "", opening.get()))
    }


    @GetMapping("opening")
    // getOpeningList 메서드는 개통 내역 목록을 조회
    fun getOpeningList(
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("page", defaultValue = "0") page: Int,
    ): ResponseEntity<BaseListRes<WellOpeningDTO>>{
        val searchKeywords: MutableList<SearchCriteria> = mutableListOf()
        val pageable: Pageable = PageRequest.of(page, size)
        // Todo : 검색조건 추가 (검색 기능에 대한 주석이 없으므로 추가 작업이 필요

        // 개통 내역 목록을 조회하고, 조회된 목록과 페이징 정보를 반환
        val openingList = openingService.searchOpening(pageable, searchKeywords)
        return ResponseEntity.ok(
            BaseListRes(HttpStatus.OK, "",
                openingList.content, openingList.number, openingList.totalElements, openingList.totalPages)
        )
    }

    @PutMapping("opening")
    // updateOpening 메서드는 개통 내역을 업데이트합니다. 업데이트할 데이터는 RequestPart로 받음
    fun updateOpening(@RequestPart("opening") opening: String) : ResponseEntity<BaseRes>{

        val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())

        try {
            // 입력된 opening 데이터를 WellOpeningDTOUpdate 객체로 변환
            val openingObj = mapper.readValue(opening, WellOpeningDTOUpdate::class.java)
            // 업데이트에 실패하면 500(Internal Server Error)을 반환
            if (!openingService.updateOpening(openingObj))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트 실패"))

        } catch (e: Exception) {
            //실패시 오류문구 출력
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }
        //성공시 메시지 반환
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "업데이트 성공"))
    }
    // deleteOpening 메서드는 개통 내역을 삭제 {id}는 삭제할 개통 내역의 고유 ID를 나타냄
    @DeleteMapping("opening/{id}")
    fun deleteOpening(@PathVariable id: String) : ResponseEntity<BaseRes> {
        val uuidIdx: String
        try{
            // 입력된 ID를 UUID 형식으로 변환합니다.
            uuidIdx = UUID.fromString(id).toString()
            // 개통 내역을 삭제
            openingService.deleteOpeningById(uuidIdx)
        }catch (e: Exception){
            // 잘못된 요청 또는 예외 발생 시 오류 메시지를 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))
        }
        //성공시 메세지 반환
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "삭제 성공"))
    }
}