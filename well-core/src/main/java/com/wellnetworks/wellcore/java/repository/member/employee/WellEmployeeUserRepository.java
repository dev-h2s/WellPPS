package com.wellnetworks.wellcore.java.repository.member.employee;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.stereotype.Repository;

// 사원의 계정과 관련된 레포지토리
@Repository
public interface WellEmployeeUserRepository
        extends JpaRepository<WellEmployeeUserEntity, String>, JpaSpecificationExecutor<WellEmployeeUserEntity> {
    // optinal = null 참조 처리 가능 비어있어도 empty로 반환
//    // idx찾기
//    Optional<WellEmployeeUserEntity> findByemployeeId(String idx);
//
//    // 모든 정보 찾기
//    Page<WellEmployeeUserEntity> findAll(Pageable pageable);
//
//    // userId 찾기
//    Optional<WellEmployeeUserEntity> findByemployeeIdentification(String userID);
//
//    // userID 값이 존재하는지 여부 판단
//    boolean existsByemployeeIdentification(String userID);
//
//    // idx 삭제
//    Optional<Integer> deleteByemployeeIdx(String idx);

}
