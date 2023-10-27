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
        List<MultipartFile> multipartFile = createDTO.getMultipartFiles();
        //결과 Map
        Map<String, Object> result = new HashMap<String, Object>();

        //파일 시퀀스 리스트
        List<Long> fileIds = new ArrayList<>();

        try {
            if (multipartFile != null) {
                if (multipartFile.size() > 0 && !multipartFile.get(0).getOriginalFilename().equals("")) {
                    for (MultipartFile file1 : multipartFile) {
                        String originalFileName = file1.getOriginalFilename();    //오리지날 파일명
                        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));    //파일 확장자
                        String savedFileName = UUID.randomUUID() + extension;    //저장될 파일 명

                        File targetFile = new File(uploadDir + savedFileName);

                        //초기값으로 fail 설정
                        result.put("result", "FAIL");

                        WellFIleCreateDTO fileDto = WellFIleCreateDTO.builder()
                                .originFileName(originalFileName)
                                .savedFileName(savedFileName)
                                .uploadDir(uploadDir)
                                .extension(extension)
                                .size(file1.getSize())
                                .contentType(file1.getContentType())
                                .build();
                        //파일 insert
                        WellFileStorageEntity file = fileDto.toEntity();
                        Long fileId = insertFile(file);
                        log.info("fileId={}", fileId);

                        try {
                            InputStream fileStream = file1.getInputStream();
                            FileUtils.copyInputStreamToFile(fileStream, targetFile); //파일 저장
                            //배열에 담기
                            fileIds.add(fileId);
                            result.put("fileIdxs", fileIds.toString());
                            result.put("result", "OK");
                        } catch (Exception e) {
                            //파일삭제
                            FileUtils.deleteQuietly(targetFile);    //저장된 현재 파일 삭제
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
