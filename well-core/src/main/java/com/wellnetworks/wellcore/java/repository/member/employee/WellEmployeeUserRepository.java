package com.wellnetworks.wellcore.java.repository.member.employee;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


// 사원의 계정과 관련된 레포지토리
@Repository
public interface WellEmployeeUserRepository extends JpaRepository<WellEmployeeUserEntity, String>, JpaSpecificationExecutor<WellEmployeeUserEntity> {

    //중복 id 체크하는 메서드
    Optional<WellEmployeeUserEntity> findByEmployeeIdentification(String employeeIdentification);
//    boolean existsByEmployeeIdentification(String employeeIdentification);

    // 아이디 중복검사
    boolean existsByEmployeeIdentification(String employeeIdentification);

    WellEmployeeUserEntity findByEmployeeIdx(String employeeIdx);

    Page<WellEmployeeUserEntity> findAll(Specification<WellEmployeeUserEntity> spec, Pageable pageable);

}

