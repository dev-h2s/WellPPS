package com.wellnetworks.wellcore.java.domain.backup.partner;

import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellPartnerFileStorageEntityBackup {
    @Id //파일_idx
    @Column(name = "file_idx")
    private String fileIdx;

    @Column(name = "p_idx")
    private String partnerIdx;

    @ManyToOne(fetch = LAZY) //거래처_idx
    @JoinColumn(name = "p_idx", insertable = false, updatable = false)
    private WellPartnerEntityBackup partner;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL) // 거래처 파일과 첨부파일 간의 연결
    @JoinColumn(name = "file_idx", insertable = false, updatable = false)
    private WellFileStorageEntityBackup file;
}
