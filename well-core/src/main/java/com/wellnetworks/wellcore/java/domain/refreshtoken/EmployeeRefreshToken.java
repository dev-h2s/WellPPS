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
@Table(name = "employee_refresh_tokens")
public class EmployeeRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "employee_idx")
    private WellEmployeeUserEntity employeeUser;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    @Column(nullable = false)
    private Date expiryDate;

    // 생성자, getter, setter, 기타 메서드
}