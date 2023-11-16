package com.wellnetworks.wellcore.java.repository.member.employee;


import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WellEmployeeGroupRepository  extends CrudRepository<WellEmployeeManagerGroupEntity, String> {
    // 그룹 권한 키 찾기
    WellEmployeeManagerGroupEntity findByEmployeeManagerGroupKey(String employeeManagerGroupKey);
    Optional<WellEmployeeManagerGroupEntity> findByDepartment(String department);

    // 모든 정보 찾기
    Page<WellEmployeeManagerGroupEntity> findAll(Pageable pageable);

    // 그룹 권한 키 삭제
    Optional<Integer> deleteByEmployeeManagerGroupKey(String employeeManagerGroupKey);
}