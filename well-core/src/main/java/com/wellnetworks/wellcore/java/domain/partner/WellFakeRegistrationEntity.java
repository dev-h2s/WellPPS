package com.wellnetworks.wellcore.java.domain.partner;
//부정가입현황

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class WellFakeRegistrationEntity {

    @Id //부정가입현황_id
    @Column(name = "fake_reg_id")
    private Integer fakeRegistrationId;

    @Column(name = "op_info_id") //개통_id
    private Integer openingInfoId;

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
