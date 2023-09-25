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
//RestController는 @ResponseBody 어노테이션이 붙은 효과를 지니게 됩니다.
//즉 주용도는 JSON/XML형태로 객체 데이터 반환을 목적으로 합니다.
@RequestMapping("/admin/pr/")
// 이 컨트롤러가 처리하는 엔드포인트의 기본 URL 경로를 지정합니다. 모든 메서드의 URL은 "/admin/pr/"로 시작합니다.
class ProductController(private var productService: WellProductService) {
//    "productService"라는 변수가 있으며, 이 변수의 타입은 "WellProductService"입니다.
    @PostMapping("product",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    fun createProduct(@RequestPart("product") productJsonString: String):ResponseEntity<BaseRes>{
        val mapper = jacksonObjectMapper()
                
        try{
            val productObj = mapper.readValue(productJsonString, WellProductDTOs::class.java)
            
            if(!productService.createProduct(productObj))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "제품 추가 실패"))
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }

        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "제품 추가 성공"))
    }

    @GetMapping("product/{id}")
    fun getProduct(@PathVariable id: String): ResponseEntity<BaseItemRes<WellProductDTOs>> {
        val idx: String
        try {
            idx = UUID.fromString(id).toString().uppercase()
        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseItemRes(HttpStatus.BAD_REQUEST, "문서 번호가 잘못되었습니다."))
        }

        val product = productService.getProductByIdx(idx)

        if (product.isEmpty)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BaseItemRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))

        return ResponseEntity.ok(BaseItemRes(HttpStatus.OK, "", product.get()))
    }

    @GetMapping("product")
    fun getProductList(
        @RequestParam("size", defaultValue = "10") size: Int,
        @RequestParam("page", defaultValue = "0") page: Int,
    ): ResponseEntity<BaseListRes<WellProductDTOs>>{
        val searchKeywords: MutableList<SearchCriteria> = mutableListOf()

        val pageable: Pageable = PageRequest.of(page, size)

        // Todo : 검색조건 추가

        val productList = productService.searchProduct(pageable, searchKeywords)

        return ResponseEntity.ok(BaseListRes(HttpStatus.OK, "",
            productList.content, productList.number, productList.totalElements, productList.totalPages))
    }

    @PutMapping("product")
    fun updateProduct(@RequestPart("product") product: String) : ResponseEntity<BaseRes>{

        val mapper = jacksonObjectMapper().registerModule(JavaTimeModule())

        try {
            val productObj = mapper.readValue(product, WellProductDTOUpdate::class.java)

            if (!productService.updateProduct(productObj))
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BaseRes(HttpStatus.INTERNAL_SERVER_ERROR, "업데이트 실패"))

        } catch (e: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseRes(HttpStatus.BAD_REQUEST, e.message ?: "잘못된 요청입니다."))
        }
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "업데이트 성공"))
    }

    @DeleteMapping("product/{id}")
    fun deleteProduct(@PathVariable id: String) : ResponseEntity<BaseRes> {
        val uuidIdx: String

        try{
            uuidIdx = UUID.fromString(id).toString()
            productService.deleteProductById(uuidIdx)
        }catch (e: Exception){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(BaseRes(HttpStatus.NOT_FOUND, "$id 데이터를 찾을 수 없습니다."))
        }
        return ResponseEntity.ok(BaseRes(HttpStatus.OK, "삭제 성공"))
    }

    @GetMapping("product/count")
    fun countProduct(): ResponseEntity<ProductCountRes> {

        val count = productService.productCount()
        val totalTelecom = count.totalTelecom
        val runTelecom = count.runTelecom
        val totalProduct = count.totalProduct
        val runProduct = count.runProduct

        return ResponseEntity.ok(
            ProductCountRes(
                totalTelecom, runTelecom, totalProduct, runProduct
            )
        )
    }

}