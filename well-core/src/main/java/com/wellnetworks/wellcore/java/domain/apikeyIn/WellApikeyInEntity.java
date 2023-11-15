package com.wellnetworks.wellcore.java.domain.apikeyIn;
// 내부 apikey

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class WellApikeyInEntity {

    @Id //APIKEY_idx
    @Column(name = "api_key_in_idx", columnDefinition = "uniqueidentifier")
    private String apiKeyInIdx;

    // 거래처와의 일대다 관계 (하나의 API 키는 여러 개의 거래처를 가질 수 있음)
    @OneToMany(mappedBy = "apiKey")
    private List<WellPartnerEntity> partners = new ArrayList<>();

    @Column(name = "api_key_in") //내부APIKEY
    private String apiKeyIn;

    @Column(name = "api_key_in_reg_dt") //생성일
    private LocalDate apiKeyInRegisterDate;

    @Column(name = "api_key_in_end_flag") //내부APIKEY만료여부
    private boolean apiKeyInEndFlag;

    @Column(name = "p_agree_flag") //거래처제공여부
    private boolean partnerAgreeFlag;

    @Column(name = "issuer") //발급자
    private String issuer;

    @ElementCollection(targetClass=String.class)
    @Column(name = "server_url")
    private List<String> serverUrl;

    @ElementCollection(targetClass=String.class)
    @Column(name = "api_server_ip") //API서버IP
    private List<String> apiServerIp;

    @Column(name = "memo", length = 2000) //메모
    private String memo;

    @Column(name = "partner_idx")
    private String partnerIdx;

    @Builder
    public WellApikeyInEntity(String apiKeyInIdx, String apiKeyIn, LocalDate apiKeyInRegisterDate, boolean apiKeyInEndFlag
                              , boolean partnerAgreeFlag, String issuer, List<String> serverUrl, List<String> apiServerIp, String memo
                              , String partnerIdx) {
        this.apiKeyInIdx = apiKeyInIdx;
        this.apiKeyIn = apiKeyIn;
        this.apiKeyInRegisterDate = apiKeyInRegisterDate;
        this.apiKeyInEndFlag = apiKeyInEndFlag;
        this.partnerAgreeFlag = partnerAgreeFlag;
        this.issuer = issuer;
        this.serverUrl = serverUrl;
        this.apiServerIp = apiServerIp;
        this.memo = memo;
        this.partnerIdx = partnerIdx;
    }

    public void setPartnerIdx(String partnerIdx) {
        this.partnerIdx = partnerIdx;
    }
}
