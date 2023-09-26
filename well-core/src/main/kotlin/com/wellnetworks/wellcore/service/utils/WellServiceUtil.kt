package com.wellnetworks.wellcore.service.utils

import org.springframework.data.jpa.domain.Specification
import java.time.ZonedDateTime
import jakarta.persistence.criteria.Predicate

class WellServiceUtil {
    companion object {
        fun <T> Specification(searchKeywords: List<SearchCriteria>): Specification<T> {
            // Specification : 검색 조건을 조합하고 동적으로 쿼리를 생성
            // criteriaBuilder : JPA 쿼리를 동적으로 생성하고, 다양한 조건으로 데이터를 검색하는 데 도움을 주는 일련의 메서드를 제공
            return Specification<T> { root, query, criteriaBuilder ->
                val predicates: MutableList<Predicate> = mutableListOf()
                for (keyword in searchKeywords) {
                    // 검색 시 Operation이 "=" 일 경우 주어진 값과 정확히 일치한 엔티티 검색
                    if (keyword.Operation.equals("=", true)) {
                        predicates.add(
                            criteriaBuilder.equal(
                                root.get<String>(keyword.Key), keyword.value
                            )
                        )
                    // 검색 시 Operation이 ">" 일 경우 주어진 값의 시간보다 큰 엔티티 검색
                    } else if (keyword.Operation.equals(">", true)) {
                        // 시간
                        if (keyword.value is ZonedDateTime) {
                            predicates.add(
                                criteriaBuilder.greaterThanOrEqualTo(
                                    root.get(keyword.Key), (keyword.value as ZonedDateTime)
                                )
                            )
                            // 문자열
                        } else {
                            predicates.add(
                                criteriaBuilder.greaterThanOrEqualTo(
                                    root.get(keyword.Key), keyword.value.toString()
                                )
                            )
                        }
                        // 검색 시 Operation이 "<" 일 경우 주어진 값의 시간보다 작은 엔티티 검색
                    } else if (keyword.Operation.equals("<", true)) {
                        //시간
                        if (keyword.value is ZonedDateTime) {
                            predicates.add(
                                criteriaBuilder.lessThanOrEqualTo(
                                    root.get(keyword.Key), (keyword.value as ZonedDateTime)
                                )
                            )
                            //문자열
                        } else {
                            predicates.add(
                                criteriaBuilder.lessThanOrEqualTo(
                                    root.get(keyword.Key), keyword.value.toString()
                                )
                            )
                        }
                        // 검색 시 Operation이 "%%" 일 경우 주어진 값이 포함된 엔티티 검색
                    } else if (keyword.Operation.equals("%%", true)) {
                        predicates.add(
                            criteriaBuilder.like(
                                root.get(keyword.Key), "%${keyword.value}%"
                            )
                        )

                    } // 검색 시 Operation이 "^%" 일 경우 주어진 값으로 시작하는 엔티티를 검색
                    else if (keyword.Operation.equals("^%", true)) {
                        predicates.add(
                            criteriaBuilder.like(
                                root.get(keyword.Key), "%${keyword.value}"
                            )
                        )
                    } // 검색 시 Operation이 "%$" 일 경우 주어진 값으로 끝나는 엔티티를 검색
                    else if (keyword.Operation.equals("%$", true)) {
                        predicates.add(
                            criteriaBuilder.like(
                                root.get(keyword.Key), "${keyword.value}%"
                            )
                        )
                    }

                }
                // 검색 조건에 대한 Predicate 생성 후 AND 연산자로 결합하여 최종 검색 조건을 반환
                return@Specification criteriaBuilder.and(*predicates.toTypedArray())
            }
        }
    }
}