package com.wellnetworks.wellcore.java.service.account;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class VirtualAccountSpecification {
    //거래처명(여러개 검색가능)
    public static Specification<WellVirtualAccountEntity> partnerNamesIn(List<String> partnerNames) {
        return (root, query, criteriaBuilder) -> {
            if (partnerNames == null || partnerNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                Join<WellVirtualAccountEntity, WellPartnerEntity> partnerJoin = root.join("partner");
                return partnerJoin.get("partnerName").in(partnerNames);
            }
        };
    }

    //날짜
    public static Specification<WellVirtualAccountEntity> issueDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null) {
                LocalDateTime startDateTime = startDate.atStartOfDay(); // 2023-11-29T00:00:00
                LocalDateTime endDateTime = endDate.atTime(23, 59, 59); // 2023-11-30T23:59:59
                return criteriaBuilder.between(root.get("issueDate"), startDateTime, endDateTime);
            } else if (startDate != null) {
                LocalDateTime startDateTime = startDate.atStartOfDay();
                return criteriaBuilder.greaterThanOrEqualTo(root.get("issueDate"), startDateTime);
            } else if (endDate != null) {
                LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
                return criteriaBuilder.lessThanOrEqualTo(root.get("issueDate"), endDateTime);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }


    //은행명
    public static Specification<WellVirtualAccountEntity> virtualBankNameContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("virtualBankName"), "%" + searchKeyword + "%");
            }
        };
    }
    //발급유무
    public static Specification<WellVirtualAccountEntity> issuanceEquals(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("issuance"), searchKeyword);
            }
        };
    }
    //계좌번호
    public static Specification<WellVirtualAccountEntity> virtualAccountContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("virtualAccount"), "%" + searchKeyword + "%");
            }
        };
    }
    //작성자
    public static Specification<WellVirtualAccountEntity> writerContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("writer"), searchKeyword);
            }
        };
    }
}
