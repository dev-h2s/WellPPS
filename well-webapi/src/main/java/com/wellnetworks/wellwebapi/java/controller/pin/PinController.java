package com.wellnetworks.wellwebapi.java.controller.pin;

import com.wellnetworks.wellcore.java.dto.PIN.WellPinCreateDTO;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinInfoDTO;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinListDTO;
import com.wellnetworks.wellcore.java.dto.PIN.WellPinUpdateDTO;
import com.wellnetworks.wellcore.java.service.pin.WellPinService;
import com.wellnetworks.wellwebapi.java.controller.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Optional;

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

    // 엑셀 파일 업로드
    @PostMapping("/excel")
    public ResponseEntity<?> importPins(@RequestParam("file") MultipartFile file) {
        try {
            pinService.importPinsFromExcel(file);
            return ResponseEntity.ok("핀 데이터가 성공적으로 업로드되었습니다.");
        } catch (Exception e) {
            return ResponseUtil.createErrorResponse(e);
        }
    }

    // 엑셀 다운로드
    @GetMapping("/excel/download")
    public ResponseEntity<Resource> downloadExcelFile() throws UnsupportedEncodingException {
        String filePath = "C:\\study\\file\\pin 관리 입력폼.xlsx";
        Resource file = new FileSystemResource(Paths.get(filePath).toAbsolutePath().normalize().toString());

        if (!((FileSystemResource) file).exists()) {
            return ResponseEntity.notFound().build();
        }

        String downloadFilename = "pin 관리 양식.xlsx";
        String encodedFilename = URLEncoder.encode(downloadFilename, StandardCharsets.UTF_8.name()).replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename)
                .body(file);
    }

    //개별 상세 조회
    @GetMapping("/{pinIdx}")
    public ResponseEntity<?> getPinDetail(@PathVariable Long pinIdx) {
        try {
            Optional<WellPinInfoDTO> infoDTO = pinService.infoPin(pinIdx);
            if (infoDTO.isPresent()) {
                return ResponseEntity.ok(infoDTO.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("pin을 찾을 수 없습니다.");
            }

        } catch (Exception e) {
            return ResponseUtil.createErrorResponse(e);
        }
    }
}