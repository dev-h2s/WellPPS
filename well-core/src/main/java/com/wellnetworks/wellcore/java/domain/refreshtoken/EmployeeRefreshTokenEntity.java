package com.wellnetworks.wellcore.java.domain.refreshtoken;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Date;
@Entity
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 포함한 생성자 생성
@Builder // 빌더 패턴 구현
public class EmployeeRefreshTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_idx")
    private WellEmployeeUserEntity employeeUser;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    @Column(nullable = false)
    private Date expiryDate;

    public EmployeeRefreshTokenEntity(WellEmployeeUserEntity employeeUser, String refreshToken, Date expiryDate) {
        this.employeeUser = employeeUser;
        this.refreshToken = refreshToken;
        this.expiryDate = expiryDate;
    }

    public static EmployeeRefreshTokenEntity createToken(WellEmployeeUserEntity employee, String token, Date expiryDate) {
        // 부분 생성자를 사용하여 새로운 인스턴스를 생성
        return new EmployeeRefreshTokenEntity(employee, token, expiryDate);
    }

    // 리프레쉬 토큰 업데이트 메소드
    public void updateToken(String newToken, Date newExpiryDate) {
        this.refreshToken = newToken;
        this.expiryDate = newExpiryDate;
    }
}
