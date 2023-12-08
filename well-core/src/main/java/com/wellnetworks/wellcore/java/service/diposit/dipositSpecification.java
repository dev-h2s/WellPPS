package com.wellnetworks.wellcore.java.service.diposit;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class dipositSpecification {

    //거래처명(여러개 검색가능)
    public static Specification<WellDipositEntity> partnerNamesIn(List<String> partnerNames) {
        return (root, query, criteriaBuilder) -> {
            if (partnerNames == null || partnerNames.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                Join<WellDipositEntity, WellPartnerEntity> partnerJoin = root.join("partner");
                return partnerJoin.get("partnerName").in(partnerNames);
            }
        };
    }
    //날짜
    public static Specification<WellDipositEntity> issueDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate != null && endDate != null) {
                LocalDateTime startDateTime = startDate.atStartOfDay(); // 2023-11-29T00:00:00
                LocalDateTime endDateTime = endDate.atTime(23, 59, 59); // 2023-11-30T23:59:59
                return criteriaBuilder.between(root.get("registerDate"), startDateTime, endDateTime);
            } else if (startDate != null) {
                LocalDateTime startDateTime = startDate.atStartOfDay();
                return criteriaBuilder.greaterThanOrEqualTo(root.get("registerDate"), startDateTime);
            } else if (endDate != null) {
                LocalDateTime endDateTime = endDate.atTime(23, 59, 59);
                return criteriaBuilder.lessThanOrEqualTo(root.get("registerDate"), endDateTime);
            } else {
                return criteriaBuilder.conjunction();
            }
        };
    }
    //예치금 조정 구분
//예치금 조정 구분
    public static Specification<WellDipositEntity> dipositAdjustmentEquals(Boolean searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.describeConstable().isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("dipositAdjustment"), searchKeyword);
            }
        };
    }

    //조정 사유
    public static Specification<WellDipositEntity> dipositStatusEquals(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("dipositStatus"), searchKeyword);
            }
        };
    }
    //메모 내용
    public static Specification<WellDipositEntity> memoContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("memo"), "%" + searchKeyword + "%");
            }
        };
    }
    //입력자
    public static Specification<WellDipositEntity> writerEquals(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("writer"), searchKeyword);
            }
        };
    }
}
