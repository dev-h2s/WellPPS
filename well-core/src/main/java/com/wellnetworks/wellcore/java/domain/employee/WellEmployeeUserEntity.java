package com.wellnetworks.wellcore.java.domain.employee;
//직원 유저 테이블

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellnetworks.wellcore.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "employee_user_tb", indexes = {@Index(name = "em_idx", columnList = "employeeIdx",unique = true)})
public class WellEmployeeUserEntity extends BaseEntity implements UserDetailsService {

    @Id
    @Column(name = "em_idx", columnDefinition = "uniqueidentifier" , unique = true, nullable = false) //맴버 고유 식별자 idx
    private String employeeIdx;

    @JsonIgnore //순환참조 문제 방지
    @OneToOne(fetch = LAZY, mappedBy = "employeeUser") //직원과 1대1 양방향
    private WellEmployeeEntity employee; //직원 엔티티 참조

    @JsonIgnore //순환참조 문제 방지
    @ManyToOne(fetch = LAZY) //양방향 일때 유저의 맴버 그룹_id 연결
    @JoinColumn(name = "em_gkey")
    private WellEmployeeManagerGroupEntity emGroup;

    @Column(name = "m_identification") //로그인시 아이디
    private String employeeIdentification;

    @Column(name = "pwd") //로그인시 비밀번호
    private String employeeUserPwd;

    @Column(name = "permissions") //권한
    private String permissions;

    @Column(name = "tmp_pwd") //임시비밀번호
    private String tmpPwd;

    @Column(name = "tmp_pwd_expiration") //임시비밀번호 만료날짜
    private LocalDateTime tmpPwdExpiration;

    @Column(name = "tmp_pwd_count") //임시비밀번호 사용횟수
    private int tmpPwdCount;

    @Column(name = "tmp_pwd_dt") //임시비밀번호 생성일자
    private LocalDateTime tmpPwdDate;

    @Column(name = "m_u_moddt") //유저정보 수정일자
    private LocalDateTime employeeUserModifyDate;

    @Column(name = "m_u_regdt") //유저정보 수정일자
    private LocalDateTime getemployeeUserRegisterDate;

    @Column(name = "Group_key") //그룹식별자
    private String groupKey;







    // 추가한 권한 관련 필드
    private String groupPermissionKey;
    @ElementCollection //값 타입의 컬렉션임을 나타냅니다.
    @CollectionTable(name = "employee_permissions", joinColumns = @JoinColumn(name = "employee_idx")) //컬렉션 값을 저장할 테이블을 지정
    @Column(name = "permission")
    private List<String> permissionsKeysStringList = Collections.emptyList();  // 예제 필드, 실제 필드와는 다를 수 있습니다.

    public Collection<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 그룹 권한 추가
        if (groupPermissionKey != null && !groupPermissionKey.isEmpty()) {
            authorities.add(new SimpleGrantedAuthority(groupPermissionKey));
        }

        // 개별 권한 목록 추가
        for (String permission : permissionsKeysStringList) {
            authorities.add(new SimpleGrantedAuthority(permission));
        }

        return authorities;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 여기에 사용자를 조회하는 로직을 구현하십시오.
        // 만약 사용자를 찾을 수 없다면 UsernameNotFoundException을 발생시켜야 합니다.
        // 찾은 사용자 정보를 바탕으로 UserDetails 객체를 반환하십시오.
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

}
