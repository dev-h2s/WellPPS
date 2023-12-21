package com.wellnetworks.wellwebapi.java.controller.product;

import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerUpdateDTO;
import com.wellnetworks.wellcore.java.dto.Product.WellProductCreateDTO;
import com.wellnetworks.wellcore.java.dto.Product.WellProductDetailDTO;
import com.wellnetworks.wellcore.java.dto.Product.WellProductUpdateDTO;
import com.wellnetworks.wellcore.java.service.product.WellProductService;
import jakarta.persistence.EntityNotFoundException;
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

    //수정
    @PatchMapping("update/{productIdx}")
    public ResponseEntity<String> patchProduct(@Valid WellProductUpdateDTO updateDTO,
                                               @PathVariable String productIdx) {
        try {
            if (productIdx == null) {
                throw new ClassNotFoundException(String.format("요금제IDX가 일치하지 않습니다. : ", productIdx));
            }
            productService.updateProduct(productIdx, updateDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("요금제를 성공적으로 수정되었습니다.");
        } catch (ClassNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("요금제를 찾을 수 없습니다. IDX: %s", productIdx));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("요금제 수정 중 오류가 발생하였습니다.");
        }
    }

    //삭제
    @DeleteMapping("delete/{productIdx}")
    public ResponseEntity<?> deleteProduct(@PathVariable String productIdx) {
        try {
            productService.deleteProduct(productIdx);
            return ResponseEntity.ok("요금제가 삭제되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }
}
