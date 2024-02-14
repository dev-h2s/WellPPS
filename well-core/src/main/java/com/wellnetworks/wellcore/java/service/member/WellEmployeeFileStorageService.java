package com.wellnetworks.wellcore.java.service.member;

import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellEmployeeFileCreateDTO;
import com.wellnetworks.wellcore.java.dto.FIle.WellFIleCreateDTO;
import com.wellnetworks.wellcore.java.repository.File.WellEmployeeFileRepository;
import com.wellnetworks.wellcore.java.repository.File.WellFileStorageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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
    public void saveFiles(MultipartHttpServletRequest files, String employeeIdx) {
        Map<String, Object> result = new HashMap<>(); // 파일 저장 결과를 저장할 Map 객체 초기화
        List<Long> fileIds = new ArrayList<>(); // 업로드된 파일의 고유 식별자(ID)를 저장할 리스트 초기화

        // 각 파일 업로드 필드를 처리 하는 processFiles 메서드 호출
        // "첨부 파일"은 파일 종류 또는 카테고리를 나타 내며, employeeIdx 는 사원을 식별 하는 IDX 를 전달
        processFiles(files.getFiles("uploadFile1"), "첨부파일1", employeeIdx, fileIds, result);
        processFiles(files.getFiles("uploadFile2"), "첨부파일2", employeeIdx, fileIds, result);
        processFiles(files.getFiles("uploadFile3"), "첨부파일3", employeeIdx, fileIds, result);
        processFiles(files.getFiles("uploadFile4"), "첨부파일4", employeeIdx, fileIds, result);
        processFiles(files.getFiles("uploadFile5"), "첨부파일5", employeeIdx, fileIds, result);
    }

    // processFiles 에서 사용 , 파일 저장
    public Long insertFile(WellFileStorageEntity file) {
        return fileStorageRepository.save(file).getId();
    }

    // processFiles 에서 사용
    public void insertEmployeeFile(WellEmployeeFileStorageEntity employeeFile) {
        employeeFileRepository.save(employeeFile);
    }

    // 각 파일 업로드 필드를 처리하는 메서드 saveFiles에서 사용
    private void processFiles(List<MultipartFile> files, String fileKind, String employeeIdx, List<Long> fileIds, Map<String, Object> result) {
        // 파일 리스트를 체크하여 빈 파일이 아닌 경우에만 처리한다.
        if (files != null) {
            for (MultipartFile file : files) {
                // 파일 존재 여부와 비어 있지 않은지 확인한다.
                if (file != null && !file.isEmpty()) {
                    // 원본 파일 이름을 가져온다
                    String originalFileName = file.getOriginalFilename();
                    assert originalFileName != null;
                    // 파일 확장자를 추출합니다. 예를 들어 "image.jpg"의 경우 ".jpg"가 된다.
                    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    // 저장할 파일 이름을 설정합니다. UUID를 사용하여 고유한 문자열을 생성하고,
                    // 파일 이름 앞에 employeeIdx와 구분자를 추가하여 경로를 만든다
                    String savedFileName = UUID.randomUUID().toString().substring(0, 5) + "-" + originalFileName;
                    // 저장될 전체 경로를 포함한 디렉토리 경로를 설정합니다.
                    String employeeDir = uploadDir + File.separator + employeeIdx;

                    // 파일을 저장할 디렉토리 객체를 생성합니다.
                    File targetFolder = new File(employeeDir);
                    if (!targetFolder.exists()) {
                        targetFolder.mkdirs();
                    }
                    // 파일을 저장할 전체 경로를 포함한 파일 객체를 생성합니다.
                    File targetFile = new File(targetFolder, savedFileName);



                    WellFIleCreateDTO fileDto = WellFIleCreateDTO.builder()
                            .originFileName(originalFileName)
                            .savedFileName(savedFileName)
                            .uploadDir(uploadDir)
                            .extension(extension)
                            .size(file.getSize())
                            .contentType(file.getContentType())
                            .fileKind(fileKind)
                            .build();

                    // DTO를 이용하여 파일 엔티티 객체를 생성합니다.
                    WellFileStorageEntity fileEntity = fileDto.toEntity();
                    // 파일 엔티티를 저장하고, 생성된 ID를 반환받습니다.
                    Long fileId = insertFile(fileEntity);

                    try {
                        // 파일의 내용을 읽어서 스트림을 가져옵니다.
                        InputStream fileStream = file.getInputStream();
                        // 스트림을 이용하여 파일을 실제로 저장합니다.
                        FileUtils.copyInputStreamToFile(fileStream, targetFile);
                        // 파일 ID를 리스트에 추가합니다.
                        fileIds.add(fileId);
                        // 결과 Map에 파일 인덱스와 결과 상태를 추가합니다.
                        result.put("fileIdxs", fileIds.toString());
                        result.put("result", "OK");
                    } catch (Exception e) {
                        // 오류 발생 시 저장된 파일을 삭제합니다.
                        FileUtils.deleteQuietly(targetFile);
                        e.printStackTrace();
                        // 결과 Map에 실패 상태를 추가합니다.
                        result.put("result", "FAIL");
                        break;
                    }

                    // 직원 파일 정보를 담을 DTO 객체를 생성합니다.
                    WellEmployeeFileCreateDTO employeefileDto = WellEmployeeFileCreateDTO.builder()
                            .emIdx(employeeIdx)
                            .build();

                    // DTO를 이용하여 직원 파일 엔티티 객체를 생성합니다.
                    WellEmployeeFileStorageEntity employeeFileEntity = employeefileDto.toEntity(fileEntity);
                    // 직원 파일 엔티티를 저장합니다.
                    insertEmployeeFile(employeeFileEntity);
                }
            }
        }
    }


    // 파일삭제
    @Transactional
    public void deleteBoardFile(Long fileId) {
        // 원하는 파일id삭제
        employeeFileRepository.deleteByFileId(fileId);
        fileStorageRepository.deleteById(fileId);

    }

    @Transactional
    public void deleteFileByEmployeeIdx(String employeeIdx) {
        List<WellEmployeeFileStorageEntity> employeeFileList = employeeFileRepository.findByEmployeeIdx(employeeIdx);

        for (WellEmployeeFileStorageEntity entity : employeeFileList) {
            employeeFileRepository.deleteById(entity.getId());
            fileStorageRepository.deleteById(entity.getFile().getId());
        }

        File targetFile = new File(uploadDir + File.separator + employeeIdx);
        File[] removeList = targetFile.listFiles();

        if (removeList != null) {
            for (File file : removeList) {
                file.delete(); //파일 삭제
            }
        }

    }

}

