package com.wellnetworks.wellcore.java.dto.FIle;

import com.querydsl.core.annotations.QueryProjection;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellPartnerFileCreateDTO {
    private Long partnerFileId;
    private Long id;
    private Long fileId;
    private String pIdx;
    private String originFileName;
    private Long size;
    private String extension;

    @Builder
    public WellPartnerFileCreateDTO(String pIdx){
        this.pIdx = pIdx;
    }

    @QueryProjection
    public WellPartnerFileCreateDTO(Long partnerFileId, Long fileId, String originFileName, Long size, String extension){
        this.partnerFileId = partnerFileId;
        this.fileId = fileId;
        this.originFileName = originFileName;
        this.size = size;
        this.extension = extension;
    }

    public WellPartnerFIleStorageEntity toEntity(WellFileStorageEntity file){
        return WellPartnerFIleStorageEntity.builder()
                .partnerIdx(pIdx)
                .file(file)
                .build();
    }
}
