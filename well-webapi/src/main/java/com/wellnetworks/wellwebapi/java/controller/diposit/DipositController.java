package com.wellnetworks.wellwebapi.java.controller.diposit;

import com.wellnetworks.wellcore.java.dto.Diposit.WellDipositInfoDTO;
import com.wellnetworks.wellcore.java.service.diposit.WellDipositService;
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
@RequestMapping(("/diposit/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class DipositController {
    @Autowired private WellDipositService dipositService;

    @GetMapping("info/{dipositIdx}")
    public ResponseEntity<?> getVirtualAccountById(@PathVariable String dipositIdx) {
        try {
            Optional<WellDipositInfoDTO> DipositInfoOpt = dipositService.getDipositById(dipositIdx);

            if (DipositInfoOpt.isPresent()) {
                return ResponseEntity.ok(DipositInfoOpt.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("예치금을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생: " + e.getMessage());
        }
    }
}
