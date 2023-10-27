//package com.wellnetworks.wellcore.java.service.File;
//
//
//import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
//import com.wellnetworks.wellcore.java.dto.FIle.WellFIleStorageDTO;
//import com.wellnetworks.wellcore.java.repository.File.WellFileStorageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.UrlResource;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Optional;
//
//@Service
//public class WellFileStorageService {
//    private final WellFileStorageRepository wellFileStorageRepository;
//
//    @Value("${well.filestorage.directory}")
//    private String uploadDirectory;
//
//    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMM");
//
//    public WellFileStorageService(WellFileStorageRepository wellFileStorageRepository) {
//        this.wellFileStorageRepository = wellFileStorageRepository;
//    }
//
//    // 파일 다운로드를 위한 메소드
//    public Optional<WellFIleStorageDTO> getOneDownload(String fileIdx) {
//        Optional<WellFileStorageEntity> fileItem = wellFileStorageRepository.findFirstByFileIdx(fileIdx);
//
//        if (fileItem.isPresent()) {
//            return Optional.of(new WellFIleStorageDTO(fileItem.get()));
//        }
//
//        return Optional.empty();
//    }
//
//    // 파일 존재 여부 확인 메소드
//    public boolean existByFileIdx(String fileIdx) {
//        return wellFileStorageRepository.existsByFileIdx(fileIdx);
//    }
//
//    // 파일 가져오기를 위한 메소드
//    public Optional<Resource> getFile(String fileIdx) {
//        Optional<WellFileStorageEntity> fileItem = wellFileStorageRepository.findFirstByFileIdx(fileIdx);
//
//        if (fileItem.isPresent()) {
//            Path file = Paths.get(uploadDirectory).resolve(
//                    Paths.get(fileItem.get().getFileKind() + "/" + fileItem.get().getFileName())
//                            .resolve(fileIdx + "." + (fileItem.get().getFileExtension() != null ? fileItem.get().getFileExtension() : ""))
//            );
//
//            try {
//                Resource resource = new UrlResource(file.toUri());
//
//                if (resource.exists() && resource.isReadable()) {
//                    return Optional.of(resource);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        return Optional.empty();
//    }
//
//    // 파일 저장 메소드
//    @Transactional(rollbackFor = Exception.class)
//    public String saveFile(WellFIleStorageDTO wellFIleStorageDTO) {
//        String fileIdx = wellFIleStorageDTO.getFileIdx();
//
//        if (fileIdx == null || wellFIleStorageDTO.getFileName() == null) {
//            return null;
//        }
//
//        String createYYYYMM = LocalDateTime.now().format(DATE_FORMATTER);
//        String orgName = wellFIleStorageDTO.getStoredFileName();
//        long fileSize = wellFIleStorageDTO.getFileSize();
//        String ext = null;
//
//        int extPosIndex = orgName.lastIndexOf(".");
//        if (extPosIndex != -1) {
//            ext = orgName.substring(extPosIndex + 1);
//        }
//
//        String fileName = wellFIleStorageDTO.getFileKind() + "/" + wellFIleStorageDTO.getFileName() + "/" + fileIdx + "." + (ext != null ? ext : "");
//
//        WellFileStorageEntity createFileEntity = new WellFileStorageEntity(
//                fileIdx, fileName, orgName, fileSize, wellFIleStorageDTO.getUploader().toUpperCase(), createYYYYMM, 0
//        );
//
//        try {
//            Files.createDirectories(Paths.get(uploadDirectory).resolve(wellFIleStorageDTO.getFileKind() + "/" + createYYYYMM + "/"));
//            wellFileStorageRepository.save(createFileEntity);
//            return fileIdx;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    // 파일 삭제 메소드
//    @Transactional(rollbackFor = Exception.class)
//    public boolean deleteFile(String fileIdx) {
//        Optional<WellFileStorageEntity> deleteFileEntity = wellFileStorageRepository.findFirstByFileIdx(fileIdx);
//
//        if (deleteFileEntity.isPresent()) {
//            String fileName = deleteFileEntity.get().getFileKind() + "/" + deleteFileEntity.get().getFileName() + "/" + fileIdx + "." + (deleteFileEntity.get().getFileExtension() != null ? deleteFileEntity.get().getFileExtension() : "");
//            File file = new File(Paths.get(uploadDirectory).resolve(fileName).toString());
//
//            if (file.delete()) {
//                wellFileStorageRepository.deleteByFileIdx(fileIdx);
//                return true;
//            }
//        }
//
//        return false;
//    }
//}
//
