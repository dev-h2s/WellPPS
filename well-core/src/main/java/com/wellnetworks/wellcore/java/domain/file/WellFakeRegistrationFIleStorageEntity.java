package com.wellnetworks.wellcore.java.domain.file;
// 부정가입현황 파일
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class WellFakeRegistrationFIleStorageEntity {
    @Id //파일_idx
    @Column(name = "file_idx")
    private String fileIdx;

    @Column(name = "fake_reg_id") //부정가입현황_id
    private String fakeRegistrationId;
}
