package com.wellnetworks.wellsecure.java.service;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

/**
 * EmployeeUserDetails 클래스는 Spring Security에서 사용자의 세부 정보를 담는데 사용됨
 * 이 클래스는 UserDetails 인터페이스를 구현하여 Spring Security가 사용자 인증과 권한 부여를 수행할 수 있도록 함
 */

@Getter
public class EmployeeUserDetails implements UserDetails {

    private final WellEmployeeUserEntity employee; // 추가된 필드

    public EmployeeUserDetails(WellEmployeeUserEntity employee) {
        this.employee = employee;

    }

    // employee 필드를 반환하는 새 메서드
    public WellEmployeeUserEntity getEmployee() {
        return this.employee;
    }
    /**
     * 사용자에게 부여된 권한을 반환합니다.
     *
     * @return 사용자의 권한 컬렉션
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * 사용자의 비밀번호를 반환합니다.
     *
     * @return 사용자의 비밀번호
     */
    @Override
    public String getPassword() {
        return employee.getIsPasswordResetRequired() ?
                employee.getTmpPwd() : employee.getEmployeeUserPwd();
    }

//    public void getFirstLogin(boolean isFirstLogin) {
//        this.isFirstLogin = isFirstLogin;
//    }

    /**
     * 사용자의 이름을 반환합니다.
     *
     * @return 사용자의 이름
     */
    @Override
    public String getUsername() {
        return employee.getEmployeeIdx();
    }

    /**
     * 계정이 만료되지 않았는지 확인합니다.
     *
     * @return 계정이 만료되지 않았다면 true, 그렇지 않다면 false
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 계정이 잠겨있지 않은지 확인합니다.
     *
     * @return 계정이 잠겨있지 않다면 true, 그렇지 않다면 false
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 사용자의 자격 증명(비밀번호)이 만료되지 않았는지 확인합니다.
     *
     * @return 자격 증명이 만료되지 않았다면 true, 그렇지 않다면 false
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 계정이 활성화 상태인지 확인합니다.
     *
     * @return 계정이 활성화 상태라면 true, 그렇지 않다면 false
     */
    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeUserDetails that = (EmployeeUserDetails) o;
        return Objects.equals(employee.getEmployeeIdx(), that.getEmployee().getEmployeeIdx());
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee.getEmployeeIdx());
    }
}