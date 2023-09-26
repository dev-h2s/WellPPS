package com.wellnetworks.wellwebapi.controller.admin

import com.fasterxml.jackson.databind.ser.Serializers.Base
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO
import com.wellnetworks.wellcore.domain.dto.WellProductDTOUpdate
import com.wellnetworks.wellcore.domain.dto.WellProductDTOs
import com.wellnetworks.wellcore.service.WellProductService
import com.wellnetworks.wellcore.service.utils.SearchCriteria
import com.wellnetworks.wellwebapi.response.BaseItemRes
import com.wellnetworks.wellwebapi.response.BaseListRes
import com.wellnetworks.wellwebapi.response.BaseRes
import com.wellnetworks.wellwebapi.response.ProductCountRes
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
//RestController는 @ResponseBody 어노테이션
//즉 주용도는 JSON/XML형태로 객체 데이터 반환을 목적
@RequestMapping("/admin/pr/")
// 이 컨트롤러가 처리하는 엔드포인트의 기본 URL 경로를 지정합니다. 모든 메서드의 URL은 "/admin/pr/"로 시작
class ProductController(private var productService: WellProductService) {
//    "productService"라는 변수가 있으며, 이 변수의 타입은 "WellProductService"
    @PostMapping("product",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
// HTTP POST 메서드로 "product" 엔드포인트를 처리하는 메서드
// @RequestPart 어노테이션을 사용하여 "product" 파라미터를 받음
// 이 엔드포인트는 JSON 또는 멀티파트(form) 데이터를 받아서 제품을 생성
    fun createProduct(@RequestPart("product") productJsonString: String):ResponseEntity<BaseRes>{
    // JSON 데이터를 객체로 변환하기 위해 Jackson ObjectMapper사용
        val mapper = jacksonObjectMapper()
                
        try{
            val productObj = mapper.readValue(productJsonString, WellProductDTOs::class.java)
            // 제품을 생성하고 결과를 ResponseEntity로 반환
            if(!productService.createProduct(productObj))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "제품 추가 실패"))
        } catch (e: Exception) {
            // 예외 발생 시 적절한 ResponseEntity를 반환하여 클라이언트에게 에러를 알림
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "제품 추가 성공"))
    }

    @GetMapping("product/{id}")
    // HTTP GET 메서드로 "product/{id}" 엔드포인트를 처리하는 메서드
    fun getProduct(@PathVariable id: String): ResponseEntity<BaseItemRes<WellProductDTOs>> {
        val idx: String
        try {
            // 입력된 id를 UUID로 변환하고 대문자로 변경하여 idx에 저장
            idx = UUID.fromString(id).toString().uppercase()
        } catch (e: Exception) {
            // 예외 처리: 문서 번호가 잘못되었을 때 클라이언트에게 에러를 알림
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseItemRes(HttpStatus.BAD_REQUEST, "문서 번호가 잘못되었습니다."))
        }
        // productService에서 idx에 해당하는 제품을 조회
        val product = productService.getProductByIdx(idx)

        if (product.isEmpty)
        // 제품이 존재하지 않을 때 클라이언트에게 에러를 알림
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseItemRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))
        // 조회한 제품 정보를 클라이언트에게 응답
        return ResponseEntity.ok(BaseItemRes(HttpStatus.OK, "", product.get()))
    }

    @GetMapping("product")
    // HTTP GET 메서드로 "product" 엔드포인트를 처리하는 메서드
    fun getProductList(
//        @RequestParam은 스프링 프레임워크에서 사용되는 어노테이션으로,
//        HTTP 요청의 파라미터를 메서드의 인자로 받아올 때 사용됨
//        파라미터를 HTTP 요청의 쿼리 문자열에서 추출하거나, 요청의 form 데이터에서 추출할 수 있다.
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("page", defaultValue = "0") page: Int,
        //페이지 size : 한페이지당 나오는 열 수 page : 페이지 갯수
        )
    //        HTTP 응답을 나타내는 객체입니다.
    : ResponseEntity<BaseListRes<WellProductDTOs>>{

        // 검색 키워드를 저장할 MutableList를 초기화. (검색 기능을 추가할 때 사용될 수 있다.)
        val searchKeywords: MutableList<SearchCriteria> = mutableListOf()

        // Pageable 객체 생성, size와 page를 사용하여 페이지네이션을 설정
        val pageable: Pageable = PageRequest.of(page, size)

        // Todo : 검색조건 추가
        // productService에서  목록을 조회하고 페이지 정보와 함께 클라이언트에게 응답
        val productList = productService.searchProduct(pageable, searchKeywords)

            //BaseListRes 객체 생성, 성공시 비어있는 메시지
        return ResponseEntity.ok(BaseListRes(HttpStatus.OK, "",
            productList.content, productList.number, productList.totalElements, productList.totalPages))
    }
    //HTTP 요청 중에서 PUT 메서드를 처리하는 용도
    @PutMapping("product")

    fun updateProduct(@RequestPart("product") product: String) : ResponseEntity<BaseRes>{
        // ObjectMapper 생성
        val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())

        try {
            // JSON 형식의 제품 데이터를 객체로 변환
            val productObj = mapper.readValue(product, WellProductDTOUpdate::class.java)
            // 제품 정보를 업데이트하고 업데이트 결과에 따라 응답 생성
            if (!productService.updateProduct(productObj))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트 실패"))

        } catch (e: Exception) {
            // 예외 발생 시 적절한 응답 생성
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }
        // 업데이트 성공 시 성공 응답 생성
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "업데이트 성공"))
    }

    @DeleteMapping("product/{id}")
    fun deleteProduct(@PathVariable id: String) : ResponseEntity<BaseRes> {
        val uuidIdx: String

        try{
            // 입력된 ID를 UUID 형식으로 변환
            uuidIdx = UUID.fromString(id).toString()

            // 해당 ID에 해당하는 제품 삭제
            productService.deleteProductById(uuidIdx)
        }catch (e: Exception){
            // 예외 발생 시 적절한 응답 생성
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))
        }
        // 삭제 성공 시 성공 응답 생성
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "삭제 성공"))
    }

    @GetMapping("product/count")
    fun countProduct(): ResponseEntity<ProductCountRes> {
        // 제품 카운트 정보를 받아옴
        val count = productService.productCount()
        val totalTelecom = count.totalTelecom
        val runTelecom = count.runTelecom
        val totalProduct = count.totalProduct
        val runProduct = count.runProduct
        // 계산 조회
        return ResponseEntity.ok(
            ProductCountRes(
                totalTelecom, runTelecom, totalProduct, runProduct
            )
        )
    }

}