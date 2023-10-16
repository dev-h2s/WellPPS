package com.wellnetworks.wellcore.java.repository.member.employee;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//사원의 조회에 관련된 Repository
@Repository

public interface WellEmployeeRepository
extends JpaRepository<WellEmployeeEntity, String>, JpaSpecificationExecutor <WellEmployeeEntity> {
    //Employeeidx 검색
    WellEmployeeEntity findByEmployeeIdx(String partnerIdx);


    // 페이지네이션을 적용하여 모든 맴버 엔티티 검색
    Page<WellEmployeeEntity> findAll(Pageable pageable);

    // 사원 name으로 조회
    Optional<WellEmployeeEntity> findByName(String name);

    //사원 등록
//    Optional<WellEmployeeEntity> save(WellEmployeeEntity wellEmployeeEntity);

    // 사원 idx로 삭제 ?? user에서 해야할 듯
    Optional<Integer> deleteByemployeeIdx(String idx);

}
