package com.wellnetworks.wellcore.java.service.partner;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class PartnerSpecification {
    //거래처명
    public static Specification<WellPartnerEntity> partnerNameContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("partnerName"), "%" + searchKeyword + "%");
            }
        };
    }
    //ceo이름
    public static Specification<WellPartnerEntity> partnerCeoNameContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("ceoName"), "%" + searchKeyword + "%");
            }
        };
    }
    //ceo번호
    public static Specification<WellPartnerEntity> partnerCeoTelephoneContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("ceoTelephone"), "%" + searchKeyword + "%");
            }
        };
    }
    //p코드
    public static Specification<WellPartnerEntity> partnerCodeContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("partnerCode"), "%" + searchKeyword + "%");
            }
        };
    }
    //주소
    public static Specification<WellPartnerEntity> partnerAddressContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                // 합친 주소 검색
                Expression<String> combinedAddress = criteriaBuilder.concat(
                        root.get("registrationAddress"),
                        criteriaBuilder.concat(
                                root.get("registrationDetailAddress"),
                                criteriaBuilder.concat(
                                        root.get("locationAddress"),
                                        root.get("locationDetailAddress")
                                )
                        )
                );
                return criteriaBuilder.like(combinedAddress, "%" + searchKeyword + "%");
            }
        };
    }
    //작성자
    public static Specification<WellPartnerEntity> writerContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("writer"), "%" + searchKeyword + "%");
            }
        };
    }
    //사업장전화번호
    public static Specification<WellPartnerEntity> partnerTelephoneContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.like(root.get("partnerTelephone"), "%" + searchKeyword + "%");
            }
        };
    }
    //날짜
    public static Specification<WellPartnerEntity> productRegisterDateBetween(LocalDate startDate, LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null || endDate == null) {
                return criteriaBuilder.conjunction();
            } else {
                LocalDateTime startDateTime = startDate.atStartOfDay();
                LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
                return criteriaBuilder.between(root.get("productRegisterDate"), startDateTime, endDateTime);
            }
        };
    }


    //------------------select박스
    //상부점
    public static Specification<WellPartnerEntity> partnerUpperNameEquals(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("partnerUpperIdx"), searchKeyword);
            }
        };
    }
    //충전할인율구분
    public static Specification<WellPartnerEntity> discountCategoryEquals(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("discountCategory"), searchKeyword);
            }
        };
    }
    //거래처구분
    public static Specification<WellPartnerEntity> partnerTypeEquals(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("partnerType"), searchKeyword);
            }
        };
    }
    //영업담당자
    public static Specification<WellPartnerEntity> salesManagerEquals(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("salesManager"), searchKeyword);
            }
        };
    }
    //등록증여부
    //계약서여부
    //거래유무
    public static Specification<WellPartnerEntity> transactionStatusEquals(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("transactionStatus"), searchKeyword);
            }
        };
    }
    //지역
    public static Specification<WellPartnerEntity> regionAddressContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                // 합친 주소 검색
                Expression<String> regionAddress = criteriaBuilder.concat(
                        root.get("registrationAddress"),
                        root.get("locationAddress")
                );
                return criteriaBuilder.like(regionAddress, "%" + searchKeyword + "%");
            }
        };
    }
}


