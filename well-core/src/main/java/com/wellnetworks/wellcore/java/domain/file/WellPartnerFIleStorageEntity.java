package com.wellnetworks.wellcore.java.domain.file;
//거래처 파일
import com.querydsl.core.annotations.QueryProjection;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class WellPartnerFIleStorageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "partner_file_id")
    private Long id;            //번호

    @Column(name = "p_idx")
    private String partnerIdx;

    @OneToOne
    @JoinColumn(name = "file_id")
    private WellFileStorageEntity file;

    @Builder
    public WellPartnerFIleStorageEntity(String partnerIdx, Long fileId, WellFileStorageEntity file){
        this.partnerIdx = partnerIdx;
        this.file = file;
    }
}
