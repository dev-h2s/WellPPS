package com.wellnetworks.wellwebapi.java.controller.File;


import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.repository.File.WellFileStorageRepository;
import com.wellnetworks.wellcore.java.service.File.WellFileStorageService;
import com.wellnetworks.wellcore.java.service.member.WellEmployeeFileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping(("/admin/hr/"))
@RequiredArgsConstructor
public class FIleStorageController {
    private final WellFileStorageRepository fileRepository;
    private final WellFileStorageService fileStorageService;
    private final WellEmployeeFileStorageService employeeFileStorageService;

    //다운로드
    @GetMapping(value = {"/fileDownload/{fileId}"})
    @ResponseBody
    public void downloadFile(HttpServletResponse res, @PathVariable Long fileId) throws UnsupportedEncodingException {

        //파일 조회
        WellFileStorageEntity fileInfo = fileRepository.findById(fileId).get();

        //파일 경로
        Path saveFilePath = Paths.get(fileInfo.getUploadDir() + java.io.File.separator + fileInfo.getSavedFileName());

        //해당 경로에 파일이 없으면
        if(!saveFilePath.toFile().exists()) {
            throw new RuntimeException("file not found");
        }

        //파일 헤더 설정
        setFileHeader(res, fileInfo);

        //파일 복사
        fileCopy(res, saveFilePath);
    }

    /**
     * 파일 header 설정
     */
    private void setFileHeader(HttpServletResponse res, WellFileStorageEntity fileInfo) throws UnsupportedEncodingException {
        res.setHeader("Content-Disposition", "attachment; filename=\"" +  URLEncoder.encode((String) fileInfo.getOriginFileName(), "UTF-8") + "\";");
        res.setHeader("Content-Transfer-Encoding", "binary");
        res.setHeader("Content-Type", "application/download; utf-8");
        res.setHeader("Pragma", "no-cache;");
        res.setHeader("Expires", "-1;");
    }

    /**
     * 파일 복사
     */
    private void fileCopy(HttpServletResponse res, Path saveFilePath) {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(saveFilePath.toFile());
            FileCopyUtils.copy(fis, res.getOutputStream());
            res.getOutputStream().flush();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                fis.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @DeleteMapping("/boardFileDelete/{fileId}")
    public void boardFileDelete(@PathVariable Long fileId){

        //게시판 파일삭제
        fileStorageService.deleteBoardFile(fileId);

    }

    @DeleteMapping("employee/boardFileDelete/{fileId}")
    public void employeeBoardFileDelete(@PathVariable Long fileId){

        //게시판 파일삭제
        employeeFileStorageService.deleteBoardFile(fileId);

    }
}
