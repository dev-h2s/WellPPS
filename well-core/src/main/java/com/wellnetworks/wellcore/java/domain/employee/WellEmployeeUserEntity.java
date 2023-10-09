package com.wellnetworks.wellcore.java.domain.employee;
//직원 유저 테이블

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "employee_user_tb", indexes = {@Index(name = "m_idx", columnList = "employeeIdx",unique = true)})
public class WellEmployeeUserEntity {

    @Id
    @Column(name = "m_idx", columnDefinition = "uniqueidentifier") //맴버 고유 식별자 idx
    private String employeeIdx;

    @JsonIgnore //순환참조 문제 방지
    @OneToOne(fetch = LAZY, mappedBy = "employeeUser") //직원 1대1
    private WellEmployeeEntity employee; //직원 엔티티 참조

    @ManyToOne(fetch = LAZY) //유저의 맴버 그룹_id
    @JoinColumn(name = "mm_gkey")
    private WellEmployeeManagerGroupEntity employeeGroup;

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
}
