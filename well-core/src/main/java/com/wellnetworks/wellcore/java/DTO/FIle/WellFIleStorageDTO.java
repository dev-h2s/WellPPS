package com.wellnetworks.wellcore.java.dto.FIle;

import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
public class WellFIleStorageDTO {
    private String fileIdx;
    private String fileName;
    private String storedFileName;
    private Long fileSize;
    private String fileExtension;
    private Integer fileDownCount;
    private String fileDescription;
    private String uploader;
    private LocalDateTime uploadDate;
    private String filePath;
    private String fileKind;

    public WellFIleStorageDTO(WellFileStorageEntity entity) {
        this.fileIdx = entity.getFileIdx();
        this.fileName = entity.getFileName();
        this.storedFileName = entity.getStoredFileName();
        this.fileSize = entity.getFileSize();
        this.fileExtension = entity.getFileExtension();
        this.fileDownCount = entity.getFileDownCount();
        this.fileDescription = entity.getFileDescription();
        this.uploader = entity.getUploader();
        this.uploadDate = entity.getUploadDate();
        this.filePath = entity.getFilePath();
        this.fileKind = entity.getFileKind();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        WellFIleStorageDTO other = (WellFIleStorageDTO) obj;

        return fileIdx != null && fileIdx.equals(other.fileIdx);
    }
}
