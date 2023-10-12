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

    @Id //APIKEY_id
    @Column(name = "api_key_in_id")
    private Long apiKeyInId;

    @Column(name = "p_idx", insertable = false, updatable = false) // 거래처_idx
    private String partnerIdx;

    @OneToMany(mappedBy = "apiKey", cascade = CascadeType.ALL) // 양방향
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
