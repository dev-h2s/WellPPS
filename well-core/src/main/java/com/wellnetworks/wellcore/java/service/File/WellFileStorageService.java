package com.wellnetworks.wellcore.java.service.File;

import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellPartnerFileCreateDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerUpdateDTO;
import com.wellnetworks.wellcore.java.repository.File.WellFileStorageRepository;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellFIleCreateDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerCreateDTO;
import com.wellnetworks.wellcore.java.repository.File.WellPartnerFileRepository;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
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
    public Map<String, Object> saveFiles(WellPartnerCreateDTO createDTO, String partnerIdx) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<Long> fileIds = new ArrayList<>();

        List<MultipartFile> businessLicenseFiles = createDTO.getBusinessLicenseFiles();
        List<MultipartFile> contractDocumentFiles = createDTO.getContractDocumentFiles();
        List<MultipartFile> idCardFiles = createDTO.getIdCardFiles();
        List<MultipartFile> storePhotoFiles = createDTO.getStorePhotoFiles();
        List<MultipartFile> businessCardFiles = createDTO.getBusinessCardFiles();

        // 각 파일 업로드 필드를 처리
        processFiles(businessLicenseFiles, "사업자등록증", partnerIdx, fileIds, result);
        processFiles(contractDocumentFiles, "계약서", partnerIdx, fileIds, result);
        processFiles(idCardFiles, "대표자신분증", partnerIdx, fileIds, result);
        processFiles(storePhotoFiles, "매장사진", partnerIdx, fileIds, result);
        processFiles(businessCardFiles, "대표자명함", partnerIdx, fileIds, result);

        // 다른 파일 업로드 필드들도 처리

        return result;
    }




    @Transactional
    public Map<String, Object> updateFiles(WellPartnerUpdateDTO updateDTO, String partnerIdx) throws Exception {
        Map<String, Object> result = new HashMap<>();
        List<Long> fileIds = new ArrayList<>();

        List<MultipartFile> businessLicenseFiles = updateDTO.getBusinessLicenseFiles();
        List<MultipartFile> contractDocumentFiles = updateDTO.getContractDocumentFiles();
        List<MultipartFile> idCardFiles = updateDTO.getIdCardFiles();
        List<MultipartFile> storePhotoFiles = updateDTO.getStorePhotoFiles();
        List<MultipartFile> businessCardFiles = updateDTO.getBusinessCardFiles();

        // 각 파일 업로드 필드를 처리
        processFiles(businessLicenseFiles, "사업자등록증", partnerIdx, fileIds, result);
        processFiles(contractDocumentFiles, "계약서", partnerIdx, fileIds, result);
        processFiles(idCardFiles, "대표자신분증", partnerIdx, fileIds, result);
        processFiles(storePhotoFiles, "매장사진", partnerIdx, fileIds, result);
        processFiles(businessCardFiles, "대표자명함", partnerIdx, fileIds, result);

        // 다른 파일 업로드 필드들도 처리

        return result;
    }

    @Transactional
    public Long insertFile(WellFileStorageEntity file) {
        return fileStorageRepository.save(file).getId();
    }

    @Transactional
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
}
