package com.wellnetworks.wellwebapi.java.controller.pin;

import com.wellnetworks.wellcore.java.dto.PIN.*;
import com.wellnetworks.wellcore.java.service.pin.WellPinService;
import com.wellnetworks.wellwebapi.java.controller.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Tag(name = "PIN 관리", description = "PIN 관리 API")
@RestController
@RequestMapping(("/pin"))
@RequiredArgsConstructor
public class PinController {

    private final WellPinService pinService;

    @Operation(summary = "PIN 관리 리스트 조회")
    @GetMapping
    public ResponseEntity<?> getPinList(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<WellPinListDTO> pinPage = pinService.getAllPins(pageable);
        return ResponseUtil.createOkResponse(pinPage.getContent(), "조회 성공", pinPage);

    }

    @Operation(summary = "PIN 관리 수동 생성")
    @PostMapping
    public ResponseEntity<?> generatePin(@Valid WellPinCreateDTO createDTO) {
        pinService.createPin(createDTO);
        return ResponseEntity.ok("PIN 생성 및 저장 완료");

    }

    @Operation(summary = "PIN 관리 수정")
    @PutMapping("/{pinIdx}")
    public ResponseEntity<?> updatePin(@Valid WellPinUpdateDTO updateDTO) {
        pinService.updatePin(updateDTO);
        return ResponseEntity.ok("pin이 수정되었습니다.");
    }

    @Operation(summary = "PIN 관리 삭제")
    @DeleteMapping("/{pinIdx}")
    public ResponseEntity<?> deletePin(@PathVariable Long pinIdx) {
        pinService.deletePin(pinIdx);
        return ResponseEntity.ok("pin이 삭제되었습니다.");
    }

    @Operation(summary = "PIN 관리 엑셀 등록")
    @PostMapping("/excel")
    public ResponseEntity<?> importPins(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        try {
            Workbook workbook = pinService.importPinsFromExcel(file);

            if (workbook != null) {
                // 중복이 있을 경우: 수정된 엑셀 파일을 응답으로 제공
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setHeader("Content-Disposition", "attachment; filename=duplicatePins.xlsx");

                ServletOutputStream outputStream = response.getOutputStream();
                workbook.write(outputStream);
                workbook.close();
                outputStream.close();
                return ResponseEntity.status(HttpStatus.CONFLICT).body("중복된 핀이 있습니다. 수정된 파일을 다운로드하세요.");
            } else {
                return ResponseEntity.ok("핀 데이터가 성공적으로 업로드되었으며 중복된 핀이 없습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 처리 중 오류 발생: " + e.getMessage());
        }
    }

    @Operation(summary = "PIN 관리 엑셀 양식 다운로드")
    @GetMapping("/excel/download")
    public ResponseEntity<Resource> downloadExcelFile() {
        String filePath = "C:\\study\\file\\pin 관리 입력폼.xlsx";
        FileSystemResource file = new FileSystemResource(Paths.get(filePath).toAbsolutePath().normalize().toString());

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        String downloadFilename = "pin 관리 양식.xlsx";
        String encodedFilename = URLEncoder.encode(downloadFilename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header("Content-Disposition", "attachment; filename*=UTF-8''" + encodedFilename)
                .body(file);
    }

    @Operation(summary = "PIN 관리 개별 상세 조회")
    @GetMapping("/{pinIdx}")
    public ResponseEntity<?> getPinDetail(@PathVariable Long pinIdx) {

        Optional<WellPinInfoDTO> infoDTO = pinService.infoPin(pinIdx);
        if (infoDTO.isPresent()) {
            return ResponseEntity.ok(infoDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("pin을 찾을 수 없습니다.");
        }
    }

    @Operation(summary = "PIN 관리 일괄 출고 리스트 조회")
    @GetMapping("/release")
    public List<WellReleaseListDTO> getReleaseList() {
        return pinService.getReleaseList();
    }

    @Operation(summary = "PIN 관리 출고된 값 엑셀 다운로드")
    @GetMapping("/release/excel")
    public void excelDownload(HttpServletResponse response) throws IOException {
        List<WellReleaseDTO> releaseList = pinService.getReleaseExcel();

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet("첫번째 시트");
        Row row;
        Cell cell;
        int rowNum = 0;
        int num = 1;

        // Header
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellValue("번호");
        cell = row.createCell(1);
        cell.setCellValue("통신사");
        cell = row.createCell(2);
        cell.setCellValue("요금제");
        cell = row.createCell(3);
        cell.setCellValue("PIN번호");

        for (WellReleaseDTO releaseDTO : releaseList) {
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue(num++);
            cell = row.createCell(1);
            cell.setCellValue(releaseDTO.getOperatorName());
            cell = row.createCell(2);
            cell.setCellValue(releaseDTO.getProductName());
            cell = row.createCell(3);
            cell.setCellValue(releaseDTO.getPinNum());
        }

        // 컨텐츠 타입과 파일명 지정
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment;filename=release.xlsx");

        wb.write(response.getOutputStream());
        wb.close();
    }

    @Operation(summary = "PIN 관리 출고")
    @PostMapping("/release")
    public ResponseEntity<String> releasePins(@RequestParam("release") String release) {
        pinService.releasePinsByRelease(release);
        return ResponseEntity.ok("출고 처리 완료");
    }

    @Operation(summary = "PIN 관리 다중 검색")
    @GetMapping("/search")
    public ResponseEntity<?> searchAccount(
            @RequestParam(value = "isSaleFlag", required = false) Boolean isSaleFlag,
            @RequestParam(value = "isUseFlag", required = false) Boolean isUseFlag,
            @RequestParam(value = "network", required = false) String network,
            @RequestParam(value = "operatorName", required = false) String operatorName,
            @RequestParam(value = "productName", required = false) String productName,
            @RequestParam(value = "pinNum", required = false) String pinNum,
            @RequestParam(value = "managementNum", required = false) String managementNum,
            @RequestParam(value = "writer", required = false) String writer,
            @RequestParam(value = "user", required = false) String user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<WellPinSearchDTO> result = pinService.searchPinList(isSaleFlag, isUseFlag, network, operatorName, productName, pinNum, managementNum, writer, user, pageable);

        return ResponseUtil.createOkResponse(result.getContent(), "조회 성공", result);
    }

}