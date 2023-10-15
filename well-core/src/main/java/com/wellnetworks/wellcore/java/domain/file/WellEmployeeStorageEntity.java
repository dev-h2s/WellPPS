package com.wellnetworks.wellcore.java.domain.file;
//사원 파일
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellEmployeeStorageEntity {
    @Id //파일_idx
    @Column(name = "file_idx")
    private String fileIdx;

    @ManyToOne(fetch = LAZY) //거래처_idx
    @JoinColumn(name = "em_idx", insertable = false, updatable = false)
    private WellEmployeeStorageEntity Employee;

    @OneToOne(fetch = LAZY) // 거래처 파일과 첨부파일 간의 연결
    @JoinColumn(name = "file_idx", insertable = false, updatable = false)
    private WellFileStorageEntity file;
}
