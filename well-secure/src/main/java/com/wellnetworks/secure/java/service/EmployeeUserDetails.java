package com.wellnetworks.secure.java.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
/**
 * EmployeeUserDetails 클래스는 Spring Security에서 사용자의 세부 정보를 담는데 사용됨
 * 이 클래스는 UserDetails 인터페이스를 구현하여 Spring Security가 사용자 인증과 권한 부여를 수행할 수 있도록 함
 */
public class EmployeeUserDetails implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * EmployeeUserDetails 객체를 생성합니다.
     *
     * @param username    사용자의 이름
     * @param password    사용자의 비밀번호
     * @param authorities 사용자에게 부여된 권한
     */
    public EmployeeUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * 사용자에게 부여된 권한을 반환합니다.
     *
     * @return 사용자의 권한 컬렉션
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * 사용자의 비밀번호를 반환합니다.
     *
     * @return 사용자의 비밀번호
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * 사용자의 이름을 반환합니다.
     *
     * @return 사용자의 이름
     */
    @Override
    public String getUsername() {
        return username;
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
}