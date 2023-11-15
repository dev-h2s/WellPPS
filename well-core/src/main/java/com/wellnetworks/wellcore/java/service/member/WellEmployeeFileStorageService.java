package com.wellnetworks.wellcore.java.service.member;

import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellEmployeeFileCreateDTO;
import com.wellnetworks.wellcore.java.dto.FIle.WellFIleCreateDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeJoinDTO;
import com.wellnetworks.wellcore.java.repository.File.WellEmployeeFileRepository;
import com.wellnetworks.wellcore.java.repository.File.WellFileStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WellEmployeeFileStorageService {

    @Value("${upload.path}")
    private String uploadDir;

    private final WellFileStorageRepository fileStorageRepository;
    private final WellEmployeeFileRepository employeeFileRepository;


    //파일 저장 서비스 메서드
    @Transactional
    public Map<String, Object> saveFiles(WellEmployeeJoinDTO joinDTO, String employeeIdx) throws Exception {
        Map<String, Object> result = new HashMap<>(); // 파일 저장 결과를 저장할 Map 객체 초기화
        List<Long> fileIds = new ArrayList<>(); // 업로드된 파일의 고유 식별자(ID)를 저장할 리스트 초기화

        List<MultipartFile> files = joinDTO.getFiles();  // WellEmployeeJoinDTO에서 업로드된 파일 목록 가져오기


        // 각 파일 업로드 필드를 처리하는 processFiles 메서드 호출
        // "첨부파일"은 파일 종류 또는 카테고리를 나타내며, employeeIdx는 사원을 식별하는 인덱스를 전달
        processFiles(files, "첨부파일", employeeIdx, fileIds, result);

        // 파일 저장 결과 Map 반환
        return result;
    }

    // processFiles에서 사용 , 파일 저장
    @Transactional
    public Long insertFile(WellFileStorageEntity file) {
        return fileStorageRepository.save(file).getId();
    }


    // processFiles에서 사용
    @Transactional
    public Long insertEmployeeFile(WellEmployeeFileStorageEntity employeeFile) {
        return employeeFileRepository.save(employeeFile).getId();
    }

    // 각 파일 업로드 필드를 처리하는 메서드 saveFiles에서 사용
    private void processFiles(List<MultipartFile> files, String fileKind, String employeeIdx, List<Long> fileIds, Map<String, Object> result) {
        if (files != null) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    String originalFileName = file.getOriginalFilename();
                    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String savedFileName = UUID.randomUUID() + extension;

                    File targetFile = new File(uploadDir + savedFileName);

                    WellFIleCreateDTO fileDto = WellFIleCreateDTO.builder()
                            .originFileName(originalFileName)
                            .savedFileName(savedFileName)
                            .uploadDir(uploadDir)
                            .extension(extension)
                            .size(file.getSize())
                            .contentType(file.getContentType())
                            .fileKind(fileKind)
                            .build();

                    WellFileStorageEntity fileEntity = fileDto.toEntity();
                    Long fileId = insertFile(fileEntity);

                    try {
                        InputStream fileStream = file.getInputStream();
                        FileUtils.copyInputStreamToFile(fileStream, targetFile);
                        fileIds.add(fileId);
                        result.put("fileIdxs", fileIds.toString());
                        result.put("result", "OK");
                    } catch (Exception e) {
                        FileUtils.deleteQuietly(targetFile);
                        e.printStackTrace();
                        result.put("result", "FAIL");
                        break;
                    }

                    WellEmployeeFileCreateDTO employeefileDto = WellEmployeeFileCreateDTO.builder()
                            .emIdx(employeeIdx)
                            .build();

                    WellEmployeeFileStorageEntity employeeFileEntity = employeefileDto.toEntity(fileEntity);
                    insertEmployeeFile(employeeFileEntity);
                }
            }
        }
    }

}
