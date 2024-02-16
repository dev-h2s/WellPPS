package com.wellnetworks.wellwebapi.java.controller.code;

import com.wellnetworks.wellcore.java.dto.code.WellCodeListDTO;
import com.wellnetworks.wellcore.java.service.code.WellCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
