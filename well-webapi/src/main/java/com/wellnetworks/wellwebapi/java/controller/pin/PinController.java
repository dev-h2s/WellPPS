package com.wellnetworks.wellwebapi.java.controller.pin;

import com.wellnetworks.wellcore.java.dto.PIN.WellPinCreateDTO;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinListDTO;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinUpdateDTO;
import com.wellnetworks.wellcore.java.service.pin.WellPinService;
import com.wellnetworks.wellwebapi.java.controller.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("/pin"))
@RequiredArgsConstructor
public class PinController {

    private final WellPinService pinService;

    @GetMapping
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

    @PostMapping
    public ResponseEntity<?> generatePin(@Valid WellPinCreateDTO createDTO) {
        try {
            pinService.createPin(createDTO);
            return ResponseEntity.ok("PIN 생성 및 저장 완료");
        } catch (Exception e) {
            return ResponseUtil.createErrorResponse(e);
        }
    }

    @PutMapping("/{pinIdx}")
    public ResponseEntity<?> updatePin(@Valid WellPinUpdateDTO updateDTO) {
        try {
            pinService.updatePin(updateDTO);
            return ResponseEntity.ok("pin이 수정되었습니다.");
        } catch (Exception e) {
            return ResponseUtil.createErrorResponse(e);
        }
    }

    @DeleteMapping("/{pinIdx}")
    public ResponseEntity<?> deletePin(@PathVariable Long pinIdx) {
        try {
            pinService.deletePin(pinIdx);
            return ResponseEntity.ok("pin이 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseUtil.createErrorResponse(e);
        }
    }
}