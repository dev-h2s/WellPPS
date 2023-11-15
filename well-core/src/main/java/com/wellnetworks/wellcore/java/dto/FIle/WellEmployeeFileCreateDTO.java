package com.wellnetworks.wellcore.java.dto.FIle;

import com.querydsl.core.annotations.QueryProjection;
import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellEmployeeFileCreateDTO {
    private Long employeeFileId;
    private Long id;
    private Long fileId;
    private String emIdx;
    private String originFileName;
    private Long size;
    private String extension;

    @Builder
    public WellEmployeeFileCreateDTO(String emIdx){
        this.emIdx = emIdx;
    }

    @QueryProjection
    public WellEmployeeFileCreateDTO(Long employeeFileId, Long fileId, String originFileName, Long size, String extension){
        this.employeeFileId = employeeFileId;
        this.fileId = fileId;
        this.originFileName = originFileName;
        this.size = size;
        this.extension = extension;
    }

    public WellEmployeeFileStorageEntity toEntity(WellFileStorageEntity file){
        return WellEmployeeFileStorageEntity.builder()
                .employeeIdx(emIdx)
                .file(file)
                .build();
    }
}
