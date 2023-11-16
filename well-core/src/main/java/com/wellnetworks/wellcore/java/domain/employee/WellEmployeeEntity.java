package com.wellnetworks.wellcore.java.domain.employee;
//직원 테이블
import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerUpdateDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeUpdateDTO;
import jakarta.persistence.*;
import lombok.Builder;
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
    @JoinColumn(name = "em_idx", insertable = false, updatable = false, nullable = false)
    private WellEmployeeUserEntity employeeUser;

    @Column(name = "em_id") //직원의 아이디
    private Long employeeId;

    //여러 사원 관련 파일을 가질 수 있음
//    @OneToMany(mappedBy = "employee")
//    private List<WellEmployeeFileStorageEntity> files = new ArrayList<>();

    @Column(name = "belong", nullable = false) // 소속회사
    private String belong;

    @Column(name = "name", nullable = false) //사원 이름
    private String employeeName;

    @Column(name = "e_mail") //사원 이메일
    private String email;

    @Column(name = "tel_private", nullable = false) // 개인 전화번호
    private String telPrivate;

    @Column(name = "tel_work") // 직장 전화번호
    private String telWork;

    @Column(name = "reg_num") //사원 등록번호
    private String registrationNumber;


    @Column(name = "position", nullable = false) //직급(대표,부장 등)
    private String position;

//    @Column(name = "level") //!!(미정의)
//    private String level;

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

    @Column(name = "emp_state", nullable = false) //재직 상태(재직,퇴사 등)
    private String employmentState;

    @Column(name = "emp_type", nullable = false) //고용형태 유형(정규직, 비정규직 등)
    private String jobType;

    @Column(name = "external_access_cert", columnDefinition = "bit") //외부 접속여부
    private Boolean externalAccessCert;

    @Column(name = "entry_dt", nullable = false) //입사 일자
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
    @Builder
    public WellEmployeeEntity(String employeeIdx, WellEmployeeUserEntity employeeUser, List<WellEmployeeFileStorageEntity> files,
                              Long employeeId, String belong, String employeeName, String email, String telPrivate, String telWork,
                              String registrationNumber, String position, String level, String homeAddress1, String homeAddress2,
                              String bankName, String bankAccount, String bankHolder, String employmentState, String jobType,
                              Boolean externalAccessCert, LocalDateTime entryDatetime, LocalDateTime employmentQuitDatetime,
                              String employmentQuitType, Float remainingLeaveDays, String residentRegistrationNumber, Boolean dbAccessPower,
                              String memo, LocalDateTime employeeModifyDate, LocalDateTime employeeRegisterDate) {
        this.employeeIdx = employeeIdx;
        this.employeeUser = employeeUser;
//        this.files = files;
        this.employeeId = employeeId;
        this.belong = belong;
        this.employeeName = employeeName;
        this.email = email;
        this.telPrivate = telPrivate;
        this.telWork = telWork;
        this.registrationNumber = registrationNumber;
        this.position = position;
//        this.level = level;
        this.homeAddress1 = homeAddress1;
        this.homeAddress2 = homeAddress2;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
        this.bankHolder = bankHolder;
        this.employmentState = employmentState;
        this.jobType = jobType;
        this.externalAccessCert = externalAccessCert;
        this.entryDatetime = entryDatetime;
        this.employmentQuitDatetime = employmentQuitDatetime;
        this.employmentQuitType = employmentQuitType;
        this.remainingLeaveDays = remainingLeaveDays;
        this.residentRegistrationNumber = residentRegistrationNumber;
        this.dbAccessPower = dbAccessPower;
        this.memo = memo;
        this.employeeModifyDate = employeeModifyDate;
        this.employeeRegisterDate = employeeRegisterDate;
    }
    public void updateFromDTO(WellEmployeeUpdateDTO updateDTO) {
        this.employeeName = updateDTO.getEmployeeName(); //이름
        this.belong = updateDTO.getBelong(); // 소속 회사
//        this.department; //부서
        this.position = updateDTO.getPosition(); // 직책
        this.employmentState = updateDTO.getEmploymentState(); // 재직상태
        this.jobType = updateDTO.getJobType(); // 고용형태
        this.entryDatetime = updateDTO.getEntryDatetime(); // 입사일자
        this.employmentQuitDatetime = updateDTO.getEmploymentQuitDatetime(); // 퇴사일자
        this.employmentQuitType = updateDTO.getEmploymentQuitType(); // 퇴사사유
        this.remainingLeaveDays = updateDTO.getRemainingLeaveDays(); // 잔여연차
        this.residentRegistrationNumber = updateDTO.getResidentRegistrationNumber();//주민등록번호
        this.telPrivate = updateDTO.getTelPrivate(); // 개인전화번호
        this.telWork = updateDTO.getTelWork(); // 업무 전화번호
        this.email = updateDTO.getEmail(); // 이메일
        this.bankName = updateDTO.getBankName(); // 급여 입금계좌명
        this.bankAccount = updateDTO.getBankAccount(); // 급여 입금계좌번호
        this.bankHolder = updateDTO.getBankHolder(); // 예금주
        this.homeAddress1 = updateDTO.getHomeAddress1(); // 자택 주소1
        this.homeAddress2 = updateDTO.getHomeAddress2(); // 자택 주소2
        this.externalAccessCert = updateDTO.getExternalAccessCert(); // 외부접속 여부
        this.memo = updateDTO.getMemo(); // 메모
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WellEmployeeEntity that = (WellEmployeeEntity) o;
        return Objects.equals(employeeIdx, that.employeeIdx);
    }

}