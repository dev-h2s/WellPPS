package com.wellnetworks.wellcore.java.repository.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.refreshtoken.EmployeeRefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRefreshTokenRepository extends JpaRepository<EmployeeRefreshTokenEntity, Long> {
    // WellEmployeeUserEntity를 기준으로 EmployeeRefreshTokenEntity를 찾는 메서드
    Optional<EmployeeRefreshTokenEntity> findByEmployeeUser(WellEmployeeUserEntity employeeUser);
}
