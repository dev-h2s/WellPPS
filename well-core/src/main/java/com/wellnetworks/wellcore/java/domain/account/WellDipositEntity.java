package com.wellnetworks.wellcore.java.domain.account;
//예치금

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
public class WellDipositEntity {

        @Id //    예치금_idx
        @Column(name = "dipo_idx", columnDefinition = "uniqueidentifier")
        private String dipositIdx;

        @OneToOne(fetch = LAZY)//가상계좌_idx
        @JoinColumn(name = "v_account_idx")
        private WellVirtualAccountEntity virtualAccount;

        @OneToOne(fetch = LAZY)//거래처_idx
        @JoinColumn(name = "p_idx")
        private WellPartnerEntity partner;

        @Column(name = "reg_dt") //발생일시
        private LocalDateTime registerDate;

        @Column(name = "depo_adjustment", columnDefinition = "BIT DEFAULT 1")
        //예치금조정(기본값 true)(true : 증액, false : 차감)
        private boolean dipositAdjustment;

        @Column(name = "depo_status") //조정사유
        private String dipositStatus;

        @Column(name = "depo_amount") //예치금조정액
        private Integer dipositAmount;

        @Column(name = "dipo_balance") //예치금잔액
        private Integer dipositBalance;

        @Column(name = "memo") //메모
        private String memo;

        @Column(name = "writer") //작성자
        private String writer;

        @Builder
        public WellDipositEntity(String dipositIdx, WellVirtualAccountEntity virtualAccount, WellPartnerEntity partner,
                                 LocalDateTime registerDate, boolean dipositAdjustment, String dipositStatus,
                                 Integer dipositAmount, Integer dipositBalance, String memo, String writer) {
                this.dipositIdx = dipositIdx;
                this.virtualAccount = virtualAccount;
                this.partner = partner;
                this.registerDate = registerDate;
                this.dipositAdjustment = dipositAdjustment;
                this.dipositStatus = dipositStatus;
                this.dipositAmount = dipositAmount;
                this.dipositBalance = dipositBalance;
                this.memo = memo;
                this.writer = writer;
        }

        public void setDipositBalance(Integer dipositBalance) {this.dipositBalance = dipositBalance;}
}
