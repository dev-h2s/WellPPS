package com.wellnetworks.wellcore.java.dto.FIle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WellFIleCreateDTO {
    @JsonProperty("file_idx")
    private String fileIdx;
    @JsonProperty("file_name")
    private String fileName;
    @JsonProperty("stored_file_name")
    private String storedFileName;
    @JsonProperty("file_size")
    private Long fileSize;
    @JsonProperty("file_extension")
    private String fileExtension;
    @JsonProperty("file_down_count")
    private Integer fileDownCount;
    @JsonProperty("file_description")
    private String fileDescription;
    @JsonProperty("uploader")
    private String uploader;
    @JsonProperty("upload_date")
    private LocalDateTime uploadDate;
    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("file_kind")
    private String fileKind;
}
