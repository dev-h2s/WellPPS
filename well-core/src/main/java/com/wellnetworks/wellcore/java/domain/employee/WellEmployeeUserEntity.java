package com.wellnetworks.wellcore.java.domain.employee;
//직원 유저 테이블

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "employee_user_tb")
public class WellEmployeeUserEntity  {

    @Id
    @Column(name = "em_idx", columnDefinition = "uniqueidentifier") //맴버 고유 식별자 idx
    private String employeeIdx;

    @OneToOne(mappedBy = "employeeUser", fetch = LAZY)
    private WellEmployeeEntity employee;

    @JsonIgnore //순환참조 문제 방지
    @ManyToOne(fetch = LAZY) //양방향 일때 유저의 맴버 그룹_id 연결
    @JoinColumn(name = "em_gkey")
    private WellEmployeeManagerGroupEntity employeeManagerGroupKey;

    @Column(name = "m_identification", unique = true) //로그인시 아이디
    private String employeeIdentification;

    @Column(name = "pwd") //로그인시 비밀번호
    private String employeeUserPwd = null;

    @Column(name = "permissions") //권한
    private String permissions;

    @Column(name = "tmp_pwd") //임시비밀번호
    private String tmpPwd;

    @Column(name = "tmp_pwd_expiration") //임시비밀번호 만료날짜
    private LocalDateTime tmpPwdExpiration;

    @Column(name = "tmp_pwd_count") //임시비밀번호 사용횟수
    private Integer tmpPwdCount;

    @Column(name = "tmp_pwd_dt") //임시비밀번호 생성일자
    private LocalDateTime tmpPwdDate;

    @Column(name = "tel_cert", columnDefinition = "bit") //전화번호 인증여부
    private Boolean isPhoneVerified;

    @Column(name = "phone_verification_code") // 휴대폰 인증 코드
    private String phoneVerificationCode;

    @Column(name = "phone_verification_attempts") // 휴대폰 인증 시도 횟수
    private Integer phoneVerificationAttempts;

    @Column(name = "phone_verification_expiration") // 휴대폰 인증 만료 시간
    private LocalDateTime phoneVerificationExpiration;

    @CreatedDate
    @Column(name = "phone_verification_sent_time") // 휴대폰 인증 코드 전송 시간
    private LocalDateTime phoneVerificationSentTime;

    @Column(name = "m_u_moddt") //유저정보 수정일자
    private LocalDateTime employeeUserModifyDate;

    @Column(name = "m_u_regdt") //유저정보 생성일자
    private LocalDateTime employeeUserRegisterDate;

    @Column(name = "Group_key") //그룹식별자
    private String groupKey;

    @Column(name = "password_reset_required") // 패스워드 변경해야하나 true면 임시패스워드로 로그인 가능
    private Boolean isPasswordResetRequired ;

    @Column
    private Boolean isFirstLogin ; // 첫로그인 여부
    public WellEmployeeEntity getEmployeeEntity() {
        return this.employee;
    }

    public WellEmployeeManagerGroupEntity getManagerGroupEntity() {
        return this.employeeManagerGroupKey;
    }
    public WellEmployeeUserEntity() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WellEmployeeUserEntity that)) return false;
        return Objects.equals(employeeIdx, that.employeeIdx);
    }
@Builder
    public WellEmployeeUserEntity(String employeeIdx, WellEmployeeEntity employee, WellEmployeeManagerGroupEntity employeeManagerGroupKey,
                                  String employeeIdentification, String employeeUserPwd,
                                  String permissions, String tmpPwd, LocalDateTime tmpPwdExpiration,
                                  Integer tmpPwdCount, LocalDateTime tmpPwdDate, Boolean isPhoneVerified,
                                  String phoneVerificationCode, Integer phoneVerificationAttempts, LocalDateTime phoneVerificationExpiration,
                                  LocalDateTime phoneVerificationSentTime, LocalDateTime employeeUserModifyDate, LocalDateTime employeeUserRegisterDate,
                                  String groupKey, String groupPermissionKey, Boolean isPasswordResetRequired,
                                  Boolean isFirstLogin, List<String> permissionsKeysStringList) {
        this.employeeIdx = employeeIdx;
        this.employee = employee;
        this.employeeManagerGroupKey = employeeManagerGroupKey;
        this.employeeIdentification = employeeIdentification;
        this.employeeUserPwd = employeeUserPwd;
        this.permissions = permissions;
        this.tmpPwd = tmpPwd;
        this.tmpPwdExpiration = tmpPwdExpiration;
        this.tmpPwdCount = tmpPwdCount;
        this.tmpPwdDate = tmpPwdDate;
        this.isPhoneVerified = isPhoneVerified;
        this.phoneVerificationCode = phoneVerificationCode;
        this.phoneVerificationAttempts = phoneVerificationAttempts;
        this.phoneVerificationExpiration = phoneVerificationExpiration;
        this.phoneVerificationSentTime = phoneVerificationSentTime;
        this.employeeUserModifyDate = employeeUserModifyDate;
        this.employeeUserRegisterDate = employeeUserRegisterDate;
        this.groupKey = groupKey;
        this.groupPermissionKey = groupPermissionKey;
//        this.permissionsKeysStringList = permissionsKeysStringList;
        this.isPasswordResetRequired = isPasswordResetRequired ;
        this.isFirstLogin = isFirstLogin;
    }

    //로그인시 패스워드 상태 매서드
    public void changePasswordAndInvalidateTempPassword(String newPassword, PasswordEncoder passwordEncoder) {
        this.employeeUserPwd = passwordEncoder.encode(newPassword);
        this.tmpPwd = null; // 임시 비밀번호 null로 설정
        this.isFirstLogin = false; // 첫 로그인 상태 업데이트
        this.isPasswordResetRequired = false;
    }


    // 추가한 권한 관련 필드
    private String groupPermissionKey;
    @ElementCollection //값 타입의 컬렉션임을 나타냅니다.
    @CollectionTable(name = "employee_permissions", joinColumns = @JoinColumn(name = "employee_idx")) //컬렉션 값을 저장할 테이블을 지정
    @Column(name = "permission")
//    private List<String> permissionsKeysStringList = new ArrayList<>();  // 예제 필드, 실제 필드와는 다를 수 있습니다.

    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 그룹 권한 추가
        if (groupPermissionKey != null && !groupPermissionKey.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(groupPermissionKey));
        }

        // 개별 권한 목록 추가
//        for (String permission : permissionsKeysStringList) {
//            authorities.add(new SimpleGrantedAuthority(permission));
//        }

        return authorities;
    }



//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 여기에 사용자를 조회하는 로직을 구현하십시오.
//        // 만약 사용자를 찾을 수 없다면 UsernameNotFoundException을 발생시켜야 합니다.
//        // 찾은 사용자 정보를 바탕으로 UserDetails 객체를 반환하십시오.
//        throw new UsernameNotFoundException("User not found with username: " + username);
//    }

}
