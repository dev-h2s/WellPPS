package com.wellnetworks.wellwebapi.java.controller.product;

import com.wellnetworks.wellcore.java.dto.Product.WellProductDetailDTO;
import com.wellnetworks.wellcore.java.service.product.WellProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(("/product/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class ProductController {

    @Autowired private WellProductService productService;

    //상세 조회
    @GetMapping("/info/detail/{productIdx}")
    public ResponseEntity<?> getDetailProductById(@PathVariable String productIdx) {
        try {
            Optional<WellProductDetailDTO> productDetail = productService.getDetailProduct(productIdx);

            if (productDetail.isPresent()) {
                return ResponseEntity.ok(productDetail.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("요금제 정보를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }
}
