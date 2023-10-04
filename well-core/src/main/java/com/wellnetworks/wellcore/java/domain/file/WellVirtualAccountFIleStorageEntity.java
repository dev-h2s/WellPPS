package com.wellnetworks.wellcore.java.domain.file;
// 가상계좌 파일

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class WellVirtualAccountFIleStorageEntity {

    @Id //파일_idx
    @Column(name = "file_idx")
    private String fileIdx;

    @Column(name = "v_account_idx") //가상계좌_idx
    private String virtualAccountIdx;
}
