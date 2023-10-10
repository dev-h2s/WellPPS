package com.wellnetworks.wellcore.java.repository.member.employee;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.awt.print.Pageable;
import java.util.Optional;

//
public interface WellEmployeeInfoRepository
extends JpaRepository<WellEmployeeEntity, String>, JpaSpecificationExecutor <WellEmployeeEntity> {
// optinal = null 참조 처리 가능 비어있어도 empty로 반환
    // employee idx로 조회
    Optional<WellEmployeeEntity> findByIdx(String idx);

    // 페이지네이션을 적용하여 모든 맴버 엔티티 검색
    Page<WellEmployeeEntity> findAll(Pageable pageable);

    // employee name으로 조회
    Optional<WellEmployeeEntity> findByName(String name);

    // employee idx로 삭제
    Optional<Integer> deleteByIdx(String idx);

}
