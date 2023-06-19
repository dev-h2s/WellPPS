package com.wellnetworks.wellwebapi.controller.admin

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO
import com.wellnetworks.wellcore.domain.dto.WellProductDTOs
import com.wellnetworks.wellcore.service.WellProductService
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
@RequestMapping("/admin/pr/")
class ProductController(private var productService: WellProductService) {
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

}