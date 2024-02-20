package com.wellnetworks.wellwebapi.java.controller.code;

import com.wellnetworks.wellcore.java.dto.code.WellCodeCreateDTO;
import com.wellnetworks.wellcore.java.dto.code.WellCodeDetailDTO;
import com.wellnetworks.wellcore.java.dto.code.WellCodeListDTO;
import com.wellnetworks.wellcore.java.dto.code.WellCodeUpdateDTO;
import com.wellnetworks.wellcore.java.service.code.WellCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "코드 관리", description = "코드 관리 API")
@RestController
@RequestMapping(("/code"))
@RequiredArgsConstructor
public class CodeController {
    private final WellCodeService codeService;

    @Operation(summary = "코드 관리 리스트 조회")
    @GetMapping
    public List<WellCodeListDTO> getAllCType() {
        return codeService.getAllCType();
    }

    @Operation(summary = "코드 관리 상세 조회")
    @GetMapping("/{codeType}")
    public List<WellCodeDetailDTO> getCodesByCType(@PathVariable String codeType) {
        return codeService.getCodesByCType(codeType);
    }

    @Operation(summary = "코드 관리 생성")
    @PostMapping
    public WellCodeCreateDTO createCode(WellCodeCreateDTO codeDetail) {
        return codeService.createCode(codeDetail);
    }

    @Operation(summary = "코드 관리 수정")
    @PutMapping("/{id}")
    public WellCodeUpdateDTO updateCode(@PathVariable Long id, WellCodeUpdateDTO codeDetail) {
        return codeService.updateCode(id, codeDetail);
    }

    @Operation(summary = "코드 관리 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCode(@PathVariable Long id) {
        codeService.deleteCode(id);
        return ResponseEntity.ok("코드가 삭제되었습니다.");
    }
}
