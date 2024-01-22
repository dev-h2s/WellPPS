package com.wellnetworks.wellcore.java.domain.pin;

import com.wellnetworks.wellcore.java.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
public class WellPinEntity extends BaseEntity { // ctrl + alt + l

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pin_idx")
    private Long pinIdx;

    @Column(name = "o_name") //통신사 (코드)
    private String operatorName;

    @Column(name = "pr_name") //요금제 (코드)
    private String productName;

    @Column(name = "network") //통신망 (코드)
    private String network;

    @Column(name = "pin_num") //PIN번호
    private String pinNum;

    @Column(name = "management_num")//관리번호
    private String managementNum;

    @Column(name = "store") //입고처(거래처)
    private String store;

    @Column(name = "release") //출고처(거래처)
    private String release;

    @Column(name = "use_flag") //사용유무
    private Boolean isUseFlag = false;

    @Column(name = "user_name") //사용자
    private String userName;

    @CreatedDate //사용날짜
    @Column(updatable = false)
    private LocalDateTime usedAt;

    @Column(name = "sale_flag") //판매전용여부
    private Boolean isSaleFlag;
}
