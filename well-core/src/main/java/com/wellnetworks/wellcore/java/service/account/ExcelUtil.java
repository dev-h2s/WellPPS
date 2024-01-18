package com.wellnetworks.wellcore.java.service.account;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ExcelUtil {
    public String getCellValue(XSSFCell cell) {
        if (cell == null) {
            return "";
        }

        return switch (cell.getCellTypeEnum()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            default -> "";
        };
    }

    public List<Map<String, Object>> getListData(MultipartFile file, int startRowNum, int columnLength) throws IOException, InvalidFormatException {
        List<Map<String, Object>> excelList = new ArrayList<>();
        try (OPCPackage opcPackage = OPCPackage.open(file.getInputStream());
             XSSFWorkbook workbook = new XSSFWorkbook(opcPackage)) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int rowIndex = startRowNum; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                XSSFRow row = sheet.getRow(rowIndex);
                if (row != null && row.getCell(0) != null && !row.getCell(0).toString().isBlank()) {
                    Map<String, Object> map = new HashMap<>();
                    for (int columnIndex = 0; columnIndex < columnLength; columnIndex++) {
                        XSSFCell cell = row.getCell(columnIndex);
                        map.put(String.valueOf(columnIndex), getCellValue(cell));
                    }
                    excelList.add(map);
                }
            }
        }
        return excelList;
    }
}
