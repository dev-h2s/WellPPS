package com.wellnetworks.wellcore.java.domain.account;
// 가상계좌
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
public class WellVirtualAccountEntity {

    @Id // 가상계좌_idx(pk)
    @Column(name = "v_account_idx", columnDefinition = "uniqueidentifier")
    private String virtualAccountIdx = UUID.randomUUID().toString();

    @OneToOne(fetch = LAZY) //거래처_idx 거래처랑 1대1
    @JoinColumn(name = "p_idx", referencedColumnName = "p_idx")
    private WellPartnerEntity partner;

    @OneToOne(mappedBy = "virtualAccount", fetch = LAZY, cascade = CascadeType.ALL) // 예치금이랑 1대1(양방향)
    private WellDipositEntity deposit;

    @CreatedDate
    @Column(name = "reg_dt") // 작성일
    private LocalDate registerDate;

    @Column(name = "writer") // 작성자
    private String writer;

    @Column(name = "v_bank_name") // 은행명
    private String virtualBankName;

    @Column(name = "v_account") // 가상계좌번호
    private String virtualAccount;

    @Column(name = "v_bank_holder") // 가상계좌예금주
    private String virtualBankHolder;

    @Column(name = "issuance") // 발급유무
    private String issuance = "미발급";

    @Column(name = "issue_date") // 발급날짜
    private LocalDateTime issueDate;

    private String memo;

    public static WellVirtualAccountEntity createFromExcelData(Map<String, String> excelData) {
        WellVirtualAccountEntity entity = new WellVirtualAccountEntity();
        entity.setVirtualBankName(excelData.get("은행명"));
        entity.setVirtualAccount(excelData.get("가상계좌번호"));
        return entity;
    }


    public void setVirtualBankName(String virtualBankName) {this.virtualBankName = virtualBankName;}
    public void setVirtualAccount(String virtualAccount) {this.virtualAccount = virtualAccount;}
    public void setIssueDate(LocalDateTime issueDate) {this.issueDate = issueDate;}
    public void setIssuance(String issuance) {this.issuance = issuance;}
    public void setPartner(WellPartnerEntity partner) {this.partner = partner;}
    public void setMemo(String memo) {this.memo = memo;}
}
