package com.wellnetworks.wellcore.java.service.member;


import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {

    //------------------select박스
    // 소속회사에 대한 Specification
    public static Specification<WellEmployeeUserEntity> belongContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                Join<WellEmployeeUserEntity, WellEmployeeEntity> employeeJoin = root.join("employee", JoinType.INNER);
                return criteriaBuilder.like(employeeJoin.get("belong"), "%" + searchKeyword + "%");
            }
        };
    }

    // 재직여부에 대한 Specification
    public static Specification<WellEmployeeUserEntity> employmentStateContains(String searchKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (searchKeyword == null || searchKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                Join<WellEmployeeUserEntity, WellEmployeeEntity> employeeJoin = root.join("employee", JoinType.INNER);
                return criteriaBuilder.equal(employeeJoin.get("employmentState"), searchKeyword);
            }
        };
    }


    // 이름으로 검색하는 조건
    public static Specification<WellEmployeeUserEntity> nameContains(String nameKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (nameKeyword == null || nameKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                Join<WellEmployeeUserEntity, WellEmployeeEntity> employeeJoin = root.join("employee", JoinType.INNER);
                return criteriaBuilder.like(employeeJoin.get("employeeName"), "%" + nameKeyword + "%");
            }
        };
    }

    // 직위로 검색하는 조건
    public static Specification<WellEmployeeUserEntity> positionContains(String positionKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (positionKeyword == null || positionKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                Join<WellEmployeeUserEntity, WellEmployeeEntity> employeeJoin = root.join("employee");
                return criteriaBuilder.like(employeeJoin.get("position"), "%" + positionKeyword + "%");
            }
        };
    }

    // 전화번호로 검색하는 조건
    public static Specification<WellEmployeeUserEntity> telPrivateContains(String telPrivateKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (telPrivateKeyword == null || telPrivateKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                Join<WellEmployeeUserEntity, WellEmployeeEntity> employeeJoin = root.join("employee");
                return criteriaBuilder.like(employeeJoin.get("telPrivate"), "%" + telPrivateKeyword + "%");
            }
        };
    }


    // 부서로 검색
    public static Specification<WellEmployeeUserEntity> departmentContains(String departmentKeyword) {
        return (root, query, criteriaBuilder) -> {
            if (departmentKeyword == null || departmentKeyword.isEmpty()) {
                return criteriaBuilder.conjunction();
            } else {
                Join<WellEmployeeUserEntity, WellEmployeeManagerGroupEntity> managerGroupJoin = root.join("employeeManagerGroupKey", JoinType.LEFT);
                return criteriaBuilder.like(managerGroupJoin.get("department"), "%" + departmentKeyword + "%");
            }
        };
    }

    // 사원 식별번호로 검색하는 조건
    public static Specification<WellEmployeeUserEntity> employeeIdentificationContains(String identificationKeyword) {
        return (root, query, criteriaBuilder) -> identificationKeyword == null || identificationKeyword.isEmpty() ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.like(root.get("employeeIdentification"), identificationKeyword);
    }





  //기타 검색부분
//  public static Specification<WellEmployeeUserEntity> advancedEmployeeSearchSpecification(
//          String nameKeyword, // WellEmployeeEntity
//          String employeeIdentificationKeyword, // WellEmployeeUserEntity
//          String positionKeyword, // WellEmployeeEntity
//          String telPrivateKeyword, // WellEmployeeEntity
//          String departmentKeyword) { // WellEmployeeManagerGroupEntity
//      return (root, query, criteriaBuilder) -> {
//          // WellEmployeeUserEntity와 WellEmployeeEntity를 join
//          Join<WellEmployeeUserEntity, WellEmployeeEntity> employeeJoin = root.join("employeeUser", JoinType.INNER);
//
//          // WellEmployeeUserEntity와 WellEmployeeManagerGroupEntity를 join
//          Join<WellEmployeeUserEntity, WellEmployeeManagerGroupEntity> managerGroupJoin = root.join("employeeManagerGroupKey", JoinType.INNER);
//
//          // 검색 조건들을 리스트로 준비
//          List<Predicate> predicates = new ArrayList<>();
//
//          // 이름으로 검색하는 조건 추가
//          if (nameKeyword != null && !nameKeyword.isEmpty()) {
//              predicates.add(criteriaBuilder.like(employeeJoin.get("name"), "%" + nameKeyword + "%"));
//          }
//
//          // employeeIdentification으로 검색하는 조건 추가
//          if (employeeIdentificationKeyword != null && !employeeIdentificationKeyword.isEmpty()) {
//              predicates.add(criteriaBuilder.like(root.get("employeeIdentification"), "%" + employeeIdentificationKeyword + "%"));
//          }
//
//          // position으로 검색하는 조건 추가
//          if (positionKeyword != null && !positionKeyword.isEmpty()) {
//              predicates.add(criteriaBuilder.like(employeeJoin.get("position"), "%" + positionKeyword + "%"));
//          }
//
//          // telPrivate으로 검색하는 조건 추가
//          if (telPrivateKeyword != null && !telPrivateKeyword.isEmpty()) {
//              predicates.add(criteriaBuilder.like(employeeJoin.get("telPrivate"), "%" + telPrivateKeyword + "%"));
//          }
//
//          // department으로 검색하는 조건 추가
//          if (departmentKeyword != null && !departmentKeyword.isEmpty()) {
//              predicates.add(criteriaBuilder.like(managerGroupJoin.get("department"), "%" + departmentKeyword + "%"));
//          }
//
//          // 모든 검색 조건을  결합
//          return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
//      };
//    }

}