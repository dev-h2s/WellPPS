package com.wellnetworks.wellcore.java.repository.member.employee;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.awt.print.Pageable;
import java.util.Optional;

// 사원의 계정과 관련된 레포지토리
public interface WellEmployeeUserRepository
        extends JpaRepository<WellEmployeeUserEntity, String>, JpaSpecificationExecutor<WellEmployeeUserEntity> {
    // optinal = null 참조 처리 가능 비어있어도 empty로 반환
    // idx찾기
//    Optional<WellEmployeeUserEntity> findByIdx(String idx);

    // 모든 정보 찾기
//    Page<WellEmployeeUserEntity> findAll(Pageable pageable);

    // 그룹권한 키 개수
//    Long countByGroupPermissionKey(String gkey);

    // userId 찾기
//    Optional<WellEmployeeUserEntity> findByUserID(String userID);

    // userID 값이 존재하는지 여부 판단
//    boolean existsByUserID(String userID);

    // idx 삭제
//    Optional<Integer> deleteByIdx(String idx);

}
