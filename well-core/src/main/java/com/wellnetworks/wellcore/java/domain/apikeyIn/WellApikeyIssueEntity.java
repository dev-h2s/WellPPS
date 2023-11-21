package com.wellnetworks.wellcore.java.domain.apikeyIn;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

//apikey 발급

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EntityListeners(AuditingEntityListener.class)
public class WellApikeyIssueEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apikey_issue_id")
    private Long id;

    @Column(name = "api_key_in") //내부APIKEY
    private String apiKeyIn;
}
