package com.wellnetworks.wellcore.java.domain.employee;
//직원 테이블
import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "employee_tb")
public class WellEmployeeEntity {

    @Id
    @Column(name= "em_idx")
    private String employeeIdx;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "em_idx", insertable = false, updatable = false)
    private WellEmployeeUserEntity employeeUser;

    //여러 사원 관련 파일을 가질 수 있음
    @OneToMany(mappedBy = "employee")
    private List<WellEmployeeFileStorageEntity> files = new ArrayList<>();

    @Column(name = "em_id") //직원의 아이디
    private Integer employeeId;

    @Column(name = "belong") // 소속회사
    private String belong;

    @Column(name = "name") //사원 이름
    private String name;

    @Column(name = "e_mail") //사원 이메일
    private String email;

    @Column(name = "tel_private") // 개인 전화번호
    private String telPrivate;

    @Column(name = "tel_work") // 직장 전화번호
    private String telWork;

    @Column(name = "reg_num") //사원 등록번호
    private String registrationNumber;

//    @Column(name = "department") //부서
//    private String department;

    @Column(name = "position") //직위(대표,부장 등)
    private String position;

    @Column(name = "level") //!!(미정의)
    private String level;

    @Column(name = "home_addr1") //도로명주소
    private String homeAddress1;

    @Column(name = "home_addr2") //상세주소
    private String homeAddress2;

    @Column(name = "bank_name") //은행 이름
    private String bankName;

    @Column(name = "bank_account") //은행 계좌
    private String bankAccount;

    @Column(name = "bank_holder") //은행 계좌 소유자
    private String bankHolder;

    @Column(name = "emp_state") //재직 상태(재직,퇴사 등)
    private String employmentState;

    @Column(name = "emp_type") //고용형태 유형(정규직, 비정규직 등)
    private String jobType;

    @Column(name = "tel_cert", columnDefinition = "bit") //전화번호 인증여부
    private Boolean certificationtel;

    @Column(name = "email_cert", columnDefinition = "bit") //이메일 인증여부
    private Boolean certificationEmail;

    @Column(name = "external_access_cert", columnDefinition = "bit") //외부 접속여부
    private Boolean externalAccessCert;

    @Column(name = "entry_dt") //입사 일자
    private LocalDateTime entryDatetime;

    @Column(name = "retire_dt") //퇴사 일자
    private LocalDateTime employmentQuitDatetime;

    @Column(name = "retire_type") //퇴사 사유
    private String employmentQuitType;

    @Column(name = "remaining_leave_days") //잔여연차
    private Float remainingLeaveDays;

    @Column(name = "resident_Registration_number") //주민번호
    private String residentRegistrationNumber;

    @Column(name = "db_access_power", columnDefinition = "bit")  //데이터 베이스 접근권한
    private Boolean dbAccessPower;

    @Column(name = "memo") //사원 메모
    private String memo;

    @Column(name = "em_moddt") //마지막 수정 날짜
    private LocalDateTime employeeModifyDate;

    @Column(name = "em_regdt") //생성 날짜와 시간
    private LocalDateTime employeeRegisterDate;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WellEmployeeEntity that = (WellEmployeeEntity) o;
        return Objects.equals(employeeIdx, that.employeeIdx);
    }

}