package com.wellnetworks.wellcore.java.domain.file;
//첨부파일
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class WellFileStorageEntity {

    @Id //파일_idx
    @Column(name = "file_id", columnDefinition = "uniqueidentifier")
    private String fileId;

    @Column(name = "file_name") //원본파일명
    private String fileName;

    @Column(name = "stored_file_name")//저장된파일명
    private String storedFileName;

    @Column(name = "file_size") //파일사이즈
    private String fileSize;

    @Column(name = "file_extension") //파일확장자
    private String fileExtension;

    @Column(name = "file_down_count") //다운로드수
    private Integer fileDownCount;

    @Column(name = "file_description") //첨부파일설명
    private String fileDescription;

    @Column(name = "uploader") //등록자
    private String uploader;

    @Column(name = "upload_date") //등록일
    private LocalDateTime uploadDate;

    @Column(name = "file_path") //파일경로
    private String filePath;

    @Column(name = "file_kind") //파일종류
    private String fileKind;
}
