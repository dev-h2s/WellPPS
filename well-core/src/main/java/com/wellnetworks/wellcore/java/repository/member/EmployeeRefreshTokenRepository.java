package com.wellnetworks.wellcore.java.repository.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.refreshtoken.EmployeeRefreshTokenEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRefreshTokenRepository extends JpaRepository<EmployeeRefreshTokenEntity, Long> {
    // WellEmployeeUserEntity를 기준으로 EmployeeRefreshTokenEntity를 찾는 메서드
    Optional<EmployeeRefreshTokenEntity> findByEmployeeUser(WellEmployeeUserEntity employeeUser);

    //로그아웃시 삭제하는 메서드
    @Modifying
    @Query("delete from EmployeeRefreshTokenEntity e where e.employeeUser = :employeeUser")
    void deleteByEmployeeUser(@Param("employeeUser") WellEmployeeUserEntity employeeUser);
    // 만료된 토큰을 찾아 삭제하는 메서드
    @Modifying
    @Query("delete from EmployeeRefreshTokenEntity e where e.expiryDate <= CURRENT_TIMESTAMP")
    void deleteAllExpiredTokens();
}
