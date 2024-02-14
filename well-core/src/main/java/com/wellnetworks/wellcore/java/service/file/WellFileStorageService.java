package com.wellnetworks.wellcore.java.service.file;

import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellFIleCreateDTO;
import com.wellnetworks.wellcore.java.dto.FIle.WellPartnerFileCreateDTO;
import com.wellnetworks.wellcore.java.repository.File.WellFileStorageRepository;
import com.wellnetworks.wellcore.java.repository.File.WellPartnerFileRepository;
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
public class WellFileStorageService {

    @Value("${upload.path}")
    private String uploadDir;

    private final WellFileStorageRepository fileStorageRepository;
    private final WellPartnerFileRepository partnerFileRepository;

    @Transactional
    public void saveFiles(MultipartHttpServletRequest files, String partnerIdx) {
        Map<String, Object> result = new HashMap<>();
        List<Long> fileIds = new ArrayList<>();
        // 각 파일 업로드 필드를 처리
        processFiles(files.getFiles("businessLicenseFile"), "사업자등록증", partnerIdx, fileIds, result);
        processFiles(files.getFiles("contractDocumentFile"), "계약서", partnerIdx, fileIds, result);
        processFiles(files.getFiles("idCardFile"), "대표자신분증", partnerIdx, fileIds, result);
        processFiles(files.getFiles("storePhotoFile"), "매장사진", partnerIdx, fileIds, result);
        processFiles(files.getFiles("businessCardFile"), "대표자명함", partnerIdx, fileIds, result);

     }

    @Transactional
    public void saveSignFiles(MultipartHttpServletRequest files, String partnerIdx) {
        Map<String, Object> result = new HashMap<>();
        List<Long> fileIds = new ArrayList<>();
        // 각 파일 업로드 필드를 처리
        processFiles(files.getFiles("businessLicenseFile"), "사업자등록증", partnerIdx, fileIds, result);
        processFiles(files.getFiles("idCardFile"), "대표자신분증", partnerIdx, fileIds, result);
    }

    public Long insertFile(WellFileStorageEntity file) {
        return fileStorageRepository.save(file).getId();
    }

    public Long insertPartnerFile(WellPartnerFIleStorageEntity partnerFile) {
        return partnerFileRepository.save(partnerFile).getId();
    }

    // 각 파일 업로드 필드를 처리하는 메서드
    private void processFiles(List<MultipartFile> files, String fileKind, String partnerIdx, List<Long> fileIds, Map<String, Object> result) {

        if (files != null) {
            for (MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    String originalFileName = file.getOriginalFilename();
                    String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                    String savedFileName = partnerIdx + File.separator + UUID.randomUUID().toString().substring(0,5) + "-" + file.getOriginalFilename();

                    File targetFile = new File(uploadDir + savedFileName);
                    File targetFolder = new File(uploadDir + partnerIdx);
                    if(!targetFolder.exists()) {
                        targetFolder.mkdirs();
                    }
                    targetFolder.setWritable(true);


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

                    WellPartnerFileCreateDTO partnerFileDto = WellPartnerFileCreateDTO.builder()
                            .pIdx(partnerIdx)
                            .build();

                    WellPartnerFIleStorageEntity partnerFileEntity = partnerFileDto.toEntity(fileEntity);
                    insertPartnerFile(partnerFileEntity);
                }
            }
        }
    }

    @Transactional
    public void deleteBoardFile(Long fileId) {
        // 원하는 파일id삭제
        partnerFileRepository.deleteByFileId(fileId);
        fileStorageRepository.deleteById(fileId);

    }

    public void deleteFileByPartnerIdx(String partnerIdx) {
        List<WellPartnerFIleStorageEntity> partnerFileList = partnerFileRepository.findByPartnerIdx(partnerIdx);

        for (WellPartnerFIleStorageEntity entity : partnerFileList) {
            partnerFileRepository.deleteById(entity.getId());
            fileStorageRepository.deleteById(entity.getFile().getId());
        }
        File targetFile = new File(uploadDir + File.separator + partnerIdx);
        File[] removeList = targetFile.listFiles();

        if (removeList != null) {
            for (File file : removeList) {
                file.delete(); //파일 삭제
            }
        }
    }
}
