package com.wellnetworks.wellwebapi.java.controller.pin;

import com.wellnetworks.wellcore.java.dto.PIN.WellPinListDTO;
import com.wellnetworks.wellcore.java.service.pin.WellPinService;
import com.wellnetworks.wellwebapi.java.controller.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
