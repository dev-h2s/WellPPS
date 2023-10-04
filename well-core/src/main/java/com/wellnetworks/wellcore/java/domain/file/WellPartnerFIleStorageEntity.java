package com.wellnetworks.wellcore.java.domain.file;
//거래처 파일
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class WellPartnerFIleStorageEntity {
    @Id //파일_idx
    @Column(name = "file_idx")
    private String fileIdx;

    @Column(name = "p_idx") //거래처_idx
    private String partnerIdx;
}
