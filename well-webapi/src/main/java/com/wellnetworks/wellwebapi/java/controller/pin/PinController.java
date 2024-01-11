package com.wellnetworks.wellwebapi.java.controller.pin;

import com.wellnetworks.wellcore.java.dto.Operator.WellOperatorUpdateDTO;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinCreateDTO;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinListDTO;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinUpdateDTO;
import com.wellnetworks.wellcore.java.service.pin.WellPinService;
import com.wellnetworks.wellwebapi.java.controller.ResponseUtil;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/pin/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class PinController {
    @Autowired private WellPinService pinService;

    //리스트 조회
    @GetMapping("info")
    public ResponseEntity<?> getPinList(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
            Page<WellPinListDTO> pinPage = pinService.getAllPins(pageable);
            return ResponseUtil.createOkResponse(pinPage.getContent(), "조회 성공", pinPage);
        } catch (Exception e) {
            return ResponseUtil.createErrorResponse(e);
        }
    }

    //생성
    @PostMapping("generate")
    public ResponseEntity<?> generatePin(@Valid WellPinCreateDTO createDTO) {
        try {
            pinService.createPin(createDTO);
            return ResponseEntity.ok("PIN 생성 및 저장 완료");
        } catch (Exception e) {
            return ResponseUtil.createErrorResponse(e);
        }
    }

    //수정
    @PatchMapping("update/{pinIdx}")
    public ResponseEntity<?> updatePin(@Valid WellPinUpdateDTO updateDTO) {
        try {
            pinService.updatePin(updateDTO);
            return ResponseEntity.ok("pin이 수정되었습니다.");
        } catch (Exception e) {
            return ResponseUtil.createErrorResponse(e);
        }
    }

    //삭제
    @DeleteMapping("delete/{pinIdx}")
    public ResponseEntity<?> deletePin(@PathVariable Long pinIdx) {
        try {
            pinService.deletePin(pinIdx);
            return ResponseEntity.ok("pin이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseUtil.createErrorResponse(e);
        }
    }
}