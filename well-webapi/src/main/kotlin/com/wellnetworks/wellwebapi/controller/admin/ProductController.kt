package com.wellnetworks.wellwebapi.controller.admin

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.wellnetworks.wellcore.domain.dto.WellProductDTOs
import com.wellnetworks.wellcore.service.WellProductService
import com.wellnetworks.wellwebapi.response.BaseRes
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

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
}