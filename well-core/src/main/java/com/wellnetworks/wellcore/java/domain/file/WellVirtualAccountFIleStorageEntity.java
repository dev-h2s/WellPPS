package com.wellnetworks.wellcore.java.domain.file;
// 가상계좌 파일

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellVirtualAccountFIleStorageEntity {

    @Id //파일_idx(pk이자 fk)
    @Column(name = "file_idx")
    private String fileIdx;


    @ManyToOne(fetch = LAZY)//가상계좌_idx
    @JoinColumn(name = "v_account_idx")
    private WellVirtualAccountEntity virtualAccount;

    @ManyToOne(fetch = LAZY) // 가상계좌 파일과 첨부파일 간의 연결
    @JoinColumn(name = "file_id")
    private WellFileStorageEntity file;
}
