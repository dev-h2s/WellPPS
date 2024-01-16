package com.wellnetworks.wellcore.java.service.pin;

import com.wellnetworks.wellcore.java.domain.pin.WellPinEntity;
import org.springframework.data.jpa.domain.Specification;

public class PinSpecification {
    //판매전용여부
    public static Specification<WellPinEntity> isSaleFlagEquals(Boolean searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("isSaleFlag"), searchKeyword);
            }
        };
    }
    //사용유무
    public static Specification<WellPinEntity> isUseFlagEquals(Boolean searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("isUseFlag"), searchKeyword);
            }
        };
    }
    //통신망
    public static Specification<WellPinEntity> networkContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("network"), "%" + searchKeyword + "%");
            }
        };
    }
    //통신사
    public static Specification<WellPinEntity> operatorNameContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("operatorName"), "%" + searchKeyword + "%");
            }
        };
    }
    //요금제
    public static Specification<WellPinEntity> productNameContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("productName"), "%" + searchKeyword + "%");
            }
        };
    }
    //pin번호
    public static Specification<WellPinEntity> pinNumContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("pinNum"), "%" + searchKeyword + "%");
            }
        };
    }
    //관리번호
    public static Specification<WellPinEntity> managementNumContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("managementNum"), "%" + searchKeyword + "%");
            }
        };
    }
    //입력자
    public static Specification<WellPinEntity> writerContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("writer"), "%" + searchKeyword + "%");
            }
        };
    }
    //사용자
    public static Specification<WellPinEntity> userNameContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("userName"), "%" + searchKeyword + "%");
            }
        };
    }
}
