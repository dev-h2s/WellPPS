package com.wellnetworks.wellcore.java.repository.member.employee;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//사원의 조회에 관련된 Repository
@Repository

public interface WellEmployeeRepository
extends JpaRepository<WellEmployeeEntity, String>, JpaSpecificationExecutor <WellEmployeeEntity> {
    //Employeeidx 검색
    WellEmployeeEntity findByEmployeeIdx(String employeeIdx);
    @Query("SELECT MAX(e.employeeId) FROM WellEmployeeEntity e")
    Long findMaxEmployeeId();


//    // 사원 name으로 조회
//    Optional<WellEmployeeEntity> findByEmployeeName(String name);




    Page<WellEmployeeEntity> findAll(Specification<WellEmployeeEntity> spec, Pageable pageable);


//    Page<WellEmployeeEntity> findAll(Specification<WellEmployeeUserEntity> spec, Pageable pageable);
}
