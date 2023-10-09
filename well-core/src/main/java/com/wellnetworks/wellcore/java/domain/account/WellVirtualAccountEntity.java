package com.wellnetworks.wellcore.java.domain.account;
// 가상계좌
import com.wellnetworks.wellcore.java.domain.file.WellVirtualAccountFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellVirtualAccountEntity {

    @Id // 가상계좌_idx(pk)
    @Column(name = "v_account_idx", columnDefinition = "uniqueidentifier")
    private String virtualAccountIdx;

    @OneToMany(mappedBy = "v_account_file", fetch = LAZY, cascade = CascadeType.ALL)  //여러 가상계좌 파일을 가질 수 있음
    private List<WellVirtualAccountFIleStorageEntity> files = new ArrayList<>();

    @OneToOne(fetch = LAZY) //거래처_idx 거래처랑 1대1
    @JoinColumn(name = "p_idx", unique = true, nullable = false)
    private WellPartnerEntity partner;

    @OneToOne(mappedBy = "virtualAccount", fetch = LAZY) // 예치금이랑 1대1(양방향)
    private WellDipositEntity deposit;

    @Column(name = "reg_dt") // 작성일
    private LocalDateTime registerDate;

    @Column(name = "writer") // 작성자
    private String writer;

    @Column(name = "v_bank_name") // 은행명
    private String virtualBankName;

    @Column(name = "v_account") // 가상계좌번호
    private String virtualAccount;

    @Column(name = "v_bank_holder") // 가상계좌예금주
    private String virtualBankHolder;

    @Column(name = "issue_flag") // 발급유무
    private Boolean issueFlag;

    @Column(name = "issue_date") // 발급날짜
    private LocalDateTime issueDate;
}
