package com.wellnetworks.wellcore.service.utils

import org.springframework.data.jpa.domain.Specification
import java.time.ZonedDateTime
import javax.persistence.criteria.Predicate

class WellServiceUtil {
    companion object {
        fun <T> Specification(searchKeywords: List<SearchCriteria>): Specification<T> {
            return Specification<T> { root, query, criteriaBuilder ->
                val predicates: MutableList<Predicate> = mutableListOf()
                for (keyword in searchKeywords) {
                    if (keyword.Operation.equals("=", true)) {
                        predicates.add(
                            criteriaBuilder.equal(
                                root.get<String>(keyword.Key), keyword.value
                            )
                        )
                    } else if (keyword.Operation.equals(">", true)) {
                        if (keyword.value is ZonedDateTime) {
                            predicates.add(
                                criteriaBuilder.greaterThanOrEqualTo(
                                    root.get(keyword.Key), (keyword.value as ZonedDateTime)
                                )
                            )
                        } else {
                            predicates.add(
                                criteriaBuilder.greaterThanOrEqualTo(
                                    root.get(keyword.Key), keyword.value.toString()
                                )
                            )
                        }
                    } else if (keyword.Operation.equals("<", true)) {
                        if (keyword.value is ZonedDateTime) {
                            predicates.add(
                                criteriaBuilder.greaterThanOrEqualTo(
                                    root.get(keyword.Key), (keyword.value as ZonedDateTime)
                                )
                            )
                        } else {
                            predicates.add(
                                criteriaBuilder.lessThanOrEqualTo(
                                    root.get(keyword.Key), keyword.value.toString()
                                )
                            )
                        }
                    } else if (keyword.Operation.equals("%", true)) {
                        predicates.add(
                            criteriaBuilder.like(
                                root.get(keyword.Key), "%" + keyword.value + "%"
                            )
                        )
                    }
                }
                return@Specification criteriaBuilder.and(*predicates.toTypedArray())
            }
        }
    }
}