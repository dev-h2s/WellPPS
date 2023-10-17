package com.wellnetworks.wellcore.java.domain.backup.partner;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellVirtualAccountFileStorageEntityBackup {

    @Id //파일_idx(pk이자 fk)
    @Column(name = "file_idx")
    private String fileIdx;

    @ManyToOne(fetch = LAZY)//가상계좌_idx
    @JoinColumn(name = "v_account_idx", insertable = false, updatable = false)
    private WellVirtualAccountEntityBackup virtualAccount;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL) // 가상계좌 파일과 첨부파일 간의 연결
    @JoinColumn(name = "file_idx", insertable = false, updatable = false)
    private WellFileStorageEntityBackup file;
}