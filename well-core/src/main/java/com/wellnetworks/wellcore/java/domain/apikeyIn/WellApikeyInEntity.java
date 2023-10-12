package com.wellnetworks.wellcore.java.domain.apikeyIn;
// 내부 apikey

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductSearchEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
public class WellApikeyInEntity {

    @Id //APIKEY_idx
    @Column(name = "api_key_in_idx")
    private String apiKeyInIdx;

    // 거래처와의 일대다 관계 (하나의 API 키는 여러 개의 거래처를 가질 수 있음)
    @OneToMany(mappedBy = "apiKey")
    private List<WellPartnerEntity> partners = new ArrayList<>();

    @Column(name = "api_key_in") //내부APIKEY
    private String apiKeyIn;

    @Column(name = "api_key_in_reg_dt") //생성일
    private LocalDateTime apiKeyInRegisterDate;

    @Column(name = "api_key_in_end_flag") //내부APIKEY만료여부
    private Boolean apiKeyInEndFlag;

    @Column(name = "api_key_in_update") //내부APIKEY업데이트
    private LocalDateTime apiKeyInUpdate;

    @Column(name = "in_api_flag") //내부API연동여부
    private Boolean inApiFlag;

    @Column(name = "p_agree_flag") //거래처제공여부
    private Boolean partnerAgreeFlag;

    @Column(name = "issuer") //발급자
    private String issuer;

    @Column(name = "server_url") //SERVER_URL
    private String serverUrl;

    @Column(name = "api_server_ip") //API서버IP
    private String apiServerIp;

    @Column(name = "memo", length = 2000) //메모
    private String memo;
}
