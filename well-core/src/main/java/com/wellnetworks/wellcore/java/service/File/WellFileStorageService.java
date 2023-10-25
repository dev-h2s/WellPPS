package com.wellnetworks.wellcore.java.service.File;

import com.wellnetworks.wellcore.java.dto.FIle.WellFIleCreateDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WellFileStorageService {
//
//    @Value("${upload.path}")
//    private String uploadDir;
//
//    public WellFIleCreateDTO storeFile(MultipartFile file) {
//        // 업로드 디렉토리 생성
//        Path uploadPath = Paths.get(uploadDir);
//        if (!Files.exists(uploadPath)) {
//            try {
//                Files.createDirectories(uploadPath);
//            } catch (IOException e) {
//                log.error("업로드 디렉토리를 생성하는 데 실패했습니다.", e);
//            }
//        }
//
//        // 파일 이름 중복 방지를 위한 고유한 파일명 생성
//        String uniqueFileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
//
//        // 파일을 업로드 디렉토리로 저장
//        Path filePath = uploadPath.resolve(uniqueFileName);
//        try {
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            log.error("파일을 저장하는 데 실패했습니다.", e);
//        }
//
//        // 엔티티를 생성하고 반환
//        return WellFIleCreateDTO.builder()
//                .originFileName(file.getOriginalFilename())
//                .savedFileName(uniqueFileName)
//                .size(file.getSize())
//                .extension(getFileExtension(file.getOriginalFilename()))
//                .uploadDir(uploadDir)
//                .fileKind("partnerFile") // 파일 종류 설정 (거래처 파일 등)
//                .build();
//    }
//
//    private String getFileExtension(String fileName) {
//        int lastDotIndex = fileName.lastIndexOf(".");
//        if (lastDotIndex != -1) {
//            return fileName.substring(lastDotIndex + 1);
//        }
//        return "";
//    }
}
