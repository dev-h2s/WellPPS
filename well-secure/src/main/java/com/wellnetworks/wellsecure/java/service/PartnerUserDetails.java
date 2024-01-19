package com.wellnetworks.wellsecure.java.service;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerUserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartnerUserDetails implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isFirstLogin; // 첫 로그인 여부
    // partner 필드를 반환하는 메서드
    @Getter
    private WellPartnerUserEntity partner;
    /**
     * EmployeeUserDetails 객체를 생성합니다.
     *
     * @param username    사용자의 이름
     * @param password    사용자의 비밀번호
     * @param authorities 사용자에게 부여된 권한
     */
    public PartnerUserDetails(String username, String password,Boolean isFirstLogin , Collection<? extends GrantedAuthority> authorities
    , WellPartnerUserEntity partner) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isFirstLogin = isFirstLogin;
        this.partner =partner;

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

    public void getFirstLogin(boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartnerUserDetails that = (PartnerUserDetails) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}