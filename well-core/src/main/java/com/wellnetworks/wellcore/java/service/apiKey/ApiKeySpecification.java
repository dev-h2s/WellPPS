package com.wellnetworks.wellcore.java.service.apiKey;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class ApiKeySpecification {
    //발급자
    public static Specification<WellApikeyInEntity> apikeyIssuerContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("issuer"), "%" + searchKeyword + "%");
            }
        };
    }

    //ApiKey
    public static Specification<WellApikeyInEntity> apikeyInContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("apiKeyIn"), "%" + searchKeyword + "%");
            }
        };
    }
    //Url
    public static Specification<WellApikeyInEntity> apikeyUrlContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.isMember(searchKeyword, root.get("serverUrl"));
            }
        };
    }
    //Ip
    public static Specification<WellApikeyInEntity> apikeyIpContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.isMember(searchKeyword, root.get("apiServerIp"));
            }
        };
    }

    //거래처명(여러개 검색가능)
    public static Specification<WellApikeyInEntity> partnerNamesIn(List<String> partnerNames) {
        return (root, query, criteriaBuilder) -> {
            if (partnerNames == null || partnerNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                Join<WellApikeyInEntity, WellPartnerEntity> partnerJoin = root.join("partners");
                return partnerJoin.get("partnerName").in(partnerNames);
            }
        };
    }
}
