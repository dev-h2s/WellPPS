package com.wellnetworks.wellwebapi.java.controller.product;

import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
import com.wellnetworks.wellcore.java.dto.Product.WellProductCreateDTO;
import com.wellnetworks.wellcore.java.dto.Product.WellProductDetailDTO;
import com.wellnetworks.wellcore.java.service.product.WellProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    //입력
    @PostMapping("create")
    public ResponseEntity<?> createProduct(@Valid WellProductCreateDTO createDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        productService.createProduct(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("요금제가 성공적으로 생성되었습니다.");
    }
}
