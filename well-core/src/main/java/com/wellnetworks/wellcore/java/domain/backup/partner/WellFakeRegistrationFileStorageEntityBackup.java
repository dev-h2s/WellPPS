package com.wellnetworks.wellcore.java.domain.backup.partner;

import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellFakeRegistrationEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellFakeRegistrationFileStorageEntityBackup {
    @Id //파일_idx
    @Column(name = "file_idx")
    private String fileIdx;

    @ManyToOne(fetch = LAZY) //부정가입현황_id
    @JoinColumn(name = "fake_reg_id", insertable = false, updatable = false)
    private WellFakeRegistrationEntityBackup fakeRegistration;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL) // 부정가입현황 파일과 첨부파일 간의 연결
    @JoinColumn(name = "file_idx", insertable = false, updatable = false)
    private WellFileStorageEntityBackup file;
}