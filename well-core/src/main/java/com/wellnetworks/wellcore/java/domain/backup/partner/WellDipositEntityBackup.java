package com.wellnetworks.wellcore.java.domain.backup.partner;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellDipositEntityBackup {

    @Id //    예치금_idx
    @Column(name = "dipo_idx", columnDefinition = "uniqueidentifier")
    private String dipositIdx;

    @OneToOne(fetch = LAZY)//가상계좌_idx
    @JoinColumn(name = "v_account_idx", insertable = false, updatable = false)
    private WellVirtualAccountEntityBackup virtualAccount;

    @Column(name = "dipo_balance") //예치금잔액
    private Integer dipositBalance;

    @Column(name = "depo_adjustment") //예치금조정
    private String depositAdjustment;

    @Column(name = "inc_dec_details") //증액차감내역
    private String incDecDetails;

    @Column(name = "charge_amount") //예치금충전금액
    private Integer chargeAmount;

    @Column(name = "depo_amount") //예치금입금액
    private Integer depositAmount;

    @Column(name = "deduction_amount") //예치금차감액
    private Integer deductionAmount;

    @Column(name = "depo_status") //예치금상태
    private String depositStatus;

    @Column(name = "depositor") //입금자
    private String depositor;

    @Column(name = "reg_dt") //registerdate
    private LocalDateTime registerDate;

    @Column(name = "memo") //메모
    private String memo;

    @Column(name = "contents") //내용
    private String contents;

    @Column(name = "writer") //작성자
    private String writer;


}