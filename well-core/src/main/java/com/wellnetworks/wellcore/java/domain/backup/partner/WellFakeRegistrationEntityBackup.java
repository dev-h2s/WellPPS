package com.wellnetworks.wellcore.java.domain.backup.partner;

import com.wellnetworks.wellcore.java.domain.file.WellFakeRegistrationFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.opening.WellOpeningEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellFakeRegistrationEntityBackup {

    @Id //부정가입현황_id
    @Column(name = "fake_reg_id")
    private Long fakeRegistrationId;

    @OneToOne(fetch = LAZY) //개통_id
    @JoinColumn(name = "op_info_id", insertable = false, updatable = false)
    private WellOpeningEntityBackup openingInfo;

    @OneToMany(mappedBy = "fakeRegistration", fetch = LAZY, cascade = CascadeType.ALL)  //여러 부정가입 파일을 가질 수 있음
    private List<WellFakeRegistrationFileStorageEntityBackup> files = new ArrayList<>();

    @Column(name = "upload_date") //등록일자
    private LocalDateTime uploadDate;

    @Column(name = "uploader") //입력자
    private String uploader;

    @Column(name = "fake_type") //형태
    private String fakeType;

    @Column(name = "termination_date") //해지일
    private LocalDateTime terminationDate;

    @Column(name = "mod_date")//수정일
    private LocalDateTime modifyDate;

    @Column(name = "modifier") //수정자
    private String modifier;

    @Column(name = "note") //비고
    private String note;
}