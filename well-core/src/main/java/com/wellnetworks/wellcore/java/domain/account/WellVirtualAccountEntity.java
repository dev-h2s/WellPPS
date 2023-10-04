package com.wellnetworks.wellcore.java.domain.account;
// 가상계좌
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class WellVirtualAccountEntity {

    @Id // 가상계좌_idx
    @Column(name = "v_account_idx", columnDefinition = "uniqueidentifier")
    private String virtualAccountIdx;

    @Column(name = "p_idx", columnDefinition = "uniqueidentifier") // 거래처_idx
    private String partnerIdx;

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
