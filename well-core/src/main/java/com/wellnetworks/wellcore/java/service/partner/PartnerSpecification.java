package com.wellnetworks.wellcore.java.service.partner;

import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.*;
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
    //날짜(subscriptionDate로 판단)
    public static Specification<WellPartnerEntity> productRegisterDateBetween(@Nullable LocalDate startDate, @Nullable LocalDate endDate) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();

            if (startDate != null) {
                LocalDateTime startDateTime = startDate.atStartOfDay();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(root.get("subscriptionDate"), startDateTime));
            }
            if (endDate != null) {
                LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay();
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThan(root.get("subscriptionDate"), endDateTime));
            }
            return predicate;
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
    public static Specification<WellPartnerEntity> businessLicenseEquals(Boolean searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null) {
                return criteriaBuilder.conjunction(); // 모든 정보 가져오기
            } else if (searchKeyword) {
                // "사업자등록증"이 있는 거래처를 검색
                Subquery<String> subquery = query.subquery(String.class);
                Root<WellPartnerFIleStorageEntity> subRoot = subquery.from(WellPartnerFIleStorageEntity.class);

                // 조인 WellFileStorageEntity와 연결
                Join<WellPartnerFIleStorageEntity, WellFileStorageEntity> fileJoin = subRoot.join("file" );

                subquery.select(subRoot.get("partnerIdx" ));
                subquery.where(criteriaBuilder.equal(fileJoin.get("fileKind" ), "사업자등록증" ));

                Predicate predicate = criteriaBuilder.in(root.get("partnerIdx" )).value(subquery);
                return predicate;
            } else {
                Subquery<String> subquery = query.subquery(String.class);
                Root<WellPartnerFIleStorageEntity> subRoot = subquery.from(WellPartnerFIleStorageEntity.class);
                Join<WellPartnerFIleStorageEntity, WellFileStorageEntity> fileJoin = subRoot.join("file" );
                subquery.select(subRoot.get("partnerIdx" ));
                subquery.where(criteriaBuilder.equal(fileJoin.get("fileKind" ), "사업자등록증" ));

                Predicate excludePredicate = criteriaBuilder.not(root.get("partnerIdx" ).in(subquery));

                return excludePredicate;
            }
        };
    }




    //계약서여부
    public static Specification<WellPartnerEntity> contractDocumentEquals(Boolean searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null) {
                return criteriaBuilder.conjunction();
            } else if (searchKeyword) {
                Subquery<String> subquery = query.subquery(String.class);
                Root<WellPartnerFIleStorageEntity> subRoot = subquery.from(WellPartnerFIleStorageEntity.class);

                Join<WellPartnerFIleStorageEntity, WellFileStorageEntity> fileJoin = subRoot.join("file");

                subquery.select(subRoot.get("partnerIdx"));
                subquery.where(criteriaBuilder.equal(fileJoin.get("fileKind"), "계약서"));

                Predicate predicate = criteriaBuilder.in(root.get("partnerIdx")).value(subquery);
                return predicate;
            } else {
                Subquery<String> subquery = query.subquery(String.class);
                Root<WellPartnerFIleStorageEntity> subRoot = subquery.from(WellPartnerFIleStorageEntity.class);
                Join<WellPartnerFIleStorageEntity, WellFileStorageEntity> fileJoin = subRoot.join("file" );
                subquery.select(subRoot.get("partnerIdx" ));
                subquery.where(criteriaBuilder.equal(fileJoin.get("fileKind" ), "계약서" ));

                Predicate excludePredicate = criteriaBuilder.not(root.get("partnerIdx" ).in(subquery));

                return excludePredicate;
            }
        };
    }



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

//회원가입 관리
    //회원가입 승인 여부
    public static Specification<WellPartnerEntity> registrationStatusContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("registrationStatus"), searchKeyword);
            }
        };
    }
//개통점 신청
    public static Specification<WellPartnerEntity> openingStatusContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                return criteriaBuilder.equal(root.get("openingStatus"), searchKeyword);
            }
        };
    }

//    public static Specification<WellPartnerEntity> registrationStatusContains(String searchKeyword) {
//        return (root, query, criteriaBuilder) -> {
//            if (searchKeyword == null || searchKeyword.isEmpty()) {
//                return criteriaBuilder.conjunction();
//            } else {
//                return criteriaBuilder.like(root.get("registrationStatus"), "%" + searchKeyword + "%");
//            }
//        };
//    }


}


