package com.wellnetworks.wellcore.java.domain.file;
// 부정가입현황 파일
import com.wellnetworks.wellcore.java.domain.partner.WellFakeRegistrationEntity;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellFakeRegistrationFIleStorageEntity {
    @Id //파일_idx
    @Column(name = "file_idx")
    private String fileIdx;

    @ManyToOne(fetch = LAZY) //부정가입현황_id
    @JoinColumn(name = "fake_reg_id", insertable = false, updatable = false)
    private WellFakeRegistrationEntity fakeRegistration;

    @OneToOne(fetch = LAZY) // 부정가입현황 파일과 첨부파일 간의 연결
    @JoinColumn(name = "file_idx", insertable = false, updatable = false)
    private WellFileStorageEntity file;
}
