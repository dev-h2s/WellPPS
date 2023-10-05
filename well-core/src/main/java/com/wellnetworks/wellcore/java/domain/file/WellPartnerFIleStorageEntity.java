package com.wellnetworks.wellcore.java.domain.file;
//거래처 파일
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellPartnerFIleStorageEntity {
    @Id //파일_idx
    @Column(name = "file_idx")
    private String fileIdx;

    @ManyToOne(fetch = LAZY) //거래처_idx
    @JoinColumn(name = "p_idx")
    private WellPartnerEntity partner;

    @ManyToOne(fetch = LAZY) // 가상계좌 파일과 첨부파일 간의 연결
    @JoinColumn(name = "file_id")
    private WellFileStorageEntity file;
}
