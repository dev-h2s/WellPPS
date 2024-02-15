package com.wellnetworks.wellcore.java.domain.outerApiVersion;

import com.wellnetworks.wellcore.java.domain.BaseEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
//외부api버전
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Builder
public class WellOuterApiVersion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "api_version_id")
    private Long id;

    // API 키와의 일대일 관계
    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "api_key_in_idx")
    private WellApikeyInEntity apiKey;

    // 버전 설정과 일대다 관계
    @OneToMany(mappedBy = "outerApiVersion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WellOuterApiVersionStatus> versionStatuses = new ArrayList<>();

    @Column(name = "version_name")
    private String versionName;

    @Column(name = "version_type")
    private String versionType;

    @Column(name = "memo")
    private String memo;
}
