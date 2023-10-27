package com.wellnetworks.wellcore.java.dto.FIle;

import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import lombok.Builder;
import lombok.Data;

@Data
public class WellFIleCreateDTO {
    private Long id;                    //id
    private String originFileName;      //원본 파일명
    private String savedFileName;       //저장된 파일명
    private String uploadDir;           //경로명
    private String extension;           //확장자
    private Long size;                  //파일 사이즈
    private String contentType;         //ContentType

    public WellFIleCreateDTO(){}

    @Builder
    public WellFIleCreateDTO(Long id, String originFileName, String savedFileName, String uploadDir
            , String extension, Long size, String contentType){
        this.id = id;
        this.originFileName = originFileName;
        this.savedFileName = savedFileName;
        this.uploadDir = uploadDir;
        this.extension = extension;
        this.size = size;
        this.contentType = contentType;
    }

    public WellFileStorageEntity toEntity(){
        return WellFileStorageEntity.builder()
                .originFileName(originFileName)
                .savedFileName(savedFileName)
                .uploadDir(uploadDir)
                .extension(extension)
                .size(size)
                .contentType(contentType)
                .build();
    }
}
