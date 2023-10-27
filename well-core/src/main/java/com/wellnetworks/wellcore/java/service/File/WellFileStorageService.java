package com.wellnetworks.wellcore.java.service.File;

import com.wellnetworks.wellcore.java.dto.FIle.WellPartnerFileCreateDTO;
import com.wellnetworks.wellcore.java.repository.File.WellFileStorageRepository;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellFIleCreateDTO;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerCreateDTO;
import com.wellnetworks.wellcore.java.repository.File.WellPartnerFileRepository;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
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
    public Map<String, Object> saveFile(WellPartnerCreateDTO createDTO, String partnerIdx) throws Exception {
        List<MultipartFile> multipartFiles = createDTO.getMultipartFiles();
        Map<String, Object> result = new HashMap<String, Object>();
        List<Long> fileIds = new ArrayList<>();

        try {
            if (multipartFiles != null) {
                if (multipartFiles.size() > 0 && !multipartFiles.get(0).getOriginalFilename().equals("")) {
                    for (MultipartFile multipartFile : multipartFiles) {
                        String originalFileName = multipartFile.getOriginalFilename();
                        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                        String savedFileName = UUID.randomUUID() + extension;
                        String fileKind = null;

                        // 화면에서 선택한 파일 이름에 따라 fileKind 설정
                        if (fileKind.contains("사업자등록증")) {
                            fileKind = "사업자등록증";
                        } else if (originalFileName.contains("계약서")) {
                            fileKind = "계약서";
                        } else if (originalFileName.contains("대표자신분증")) {
                            fileKind = "대표자신분증";
                        } else if (originalFileName.contains("매장사진")) {
                            fileKind = "매장사진";
                        } else if (originalFileName.contains("대표자명함")) {
                            fileKind = "대표자명함";
                        } else {
                            fileKind = "기타";
                        }

                        File targetFile = new File(uploadDir + savedFileName);
                        result.put("result", "FAIL");

                        WellFIleCreateDTO fileDto = WellFIleCreateDTO.builder()
                                .originFileName(originalFileName)
                                .savedFileName(savedFileName)
                                .uploadDir(uploadDir)
                                .extension(extension)
                                .size(multipartFile.getSize())
                                .contentType(multipartFile.getContentType())
                                .fileKind(fileKind)
                                .build();

                        WellFileStorageEntity file = fileDto.toEntity();
                        Long fileId = insertFile(file);
                        log.info("fileId={}", fileId);

                        try {
                            InputStream fileStream = multipartFile.getInputStream();
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

                        WellPartnerFIleStorageEntity partnerFile = partnerFileDto.toEntity(file);
                        insertPartnerFile(partnerFile);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Transactional
    public WellPartnerFIleStorageEntity deleteBoardFile(Long partnerFileId){
        WellPartnerFIleStorageEntity boardFile = partnerFileRepository.findById(partnerFileId).get();

        //삭제
//        boardFile.delete("Y");
        return boardFile;
    }
}
