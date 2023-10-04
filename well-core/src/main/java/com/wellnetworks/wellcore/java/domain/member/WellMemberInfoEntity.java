package com.wellnetworks.wellcore.java.domain.member;

import com.wellnetworks.wellcore.domain.converter.*;
import com.wellnetworks.wellcore.domain.dto.WellMemberDTOUpdate;
import com.wellnetworks.wellcore.domain.dto.WellMemberInfoDTO;
import com.wellnetworks.wellcore.domain.enums.*;
import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.time.ZonedDateTime;

@Entity
@Table(name = "member_tb", indexes = {@Index(name = "IX_tid", columnList = "tbl_id ASC"),})
public class WellMemberInfoEntity {

    //맴버의 고유 식별자 idx
    @Id
    @Column(name = "m_idx", columnDefinition = "uniqueidentifier")
    private String memberIdx;

    //!! ??뭐지
//    @Column(name = "tbl_id",  )
//    private String tableID;
    //member의 아이디
    @Column(name = "m_id")
    private String memberId;

    // 소속
    @Column(name = "belong")
    private String currentEmployment;

    //맴버 이름
    @Column(name = "name")
    private String name;

    //맴버 이메일
    @Column(name = "e_mail")
    private String eMail;
    // 개인 전화번호
    @Column(name = "tel_private")
    private String telPrivate;
    // 직장 전화번호
    @Column(name = "tel_work")
    private String telWork;
    //사원 등록번호
    @Column(name = "reg_num")
    private String registrationNumber;
    //부서
    @Column(name = "department")
    private String department;
    //직위(대표,부장 등)
    @Column(name = "possition")
    private String jobPosition;
    //!!(미정의)
    @Column(name = "level")
    private byte level;
    //도로명주소
    @Column(name = "home_addr1")
    private String homeAddress1;
    //상세주소
    @Column(name = "home_addr2")
    private String homeAddress2;
    //은행 이름
    @Column(name = "bank_name")
    private String bankName;
    //은행 계좌
    @Column(name = "bank_account")
    private String bankAccount;
    //은행 계좌 소유자
    @Column(name = "bank_holder")
    private String bankHolder;
    //재직 상태(재직,퇴사 등)
    @Column(name = "emp_state")
    private String employmentState;
    //직원 유형(정규직, 비정규직 등)
    @Column(name = "emp_type")
    private String jobType;
    //전화번호 인증여부
    @Column(name = "tel_cert", columnDefinition = "bit")
    private boolean certificationtel;
    //이메일 인증여부
    @Column(name = "email_cert", columnDefinition = "bit")
    private boolean certificationEmail;
    //입사 일자
    @Column(name = "entry_dt")
    private ZonedDateTime entryDatetime;
    //퇴사 일자
    @Column(name = "retire_dt")
    private ZonedDateTime employmentQuitDatetime;
    //퇴사 사유
    @Column(name = "retire_type")
    private String employmentQuitType;
    //데이터 베이스 접근권한
    @Column(name = "db_access_power", columnDefinition = "bit")
    private boolean dbAccessPower;
    //맴버 메모
    @Column(name = "memo")
    private String memo;
    //첨부 파일1
    @Column(name = "file1_idx")
    private String file1Idx;
    //첨부 파일2
    @Column(name = "file2_idx")
    private String file2Idx;
    //첨부 파일3
    @Column(name = "file3_idx")
    private String file3Idx;
    //첨부 파일4
    @Column(name = "file4_idx")
    private String file4Idx;
    //첨부 파일5
    @Column(name = "file5_idx")
    private String file5Idx;
    //마지막 수정 날짜
    @Column(name = "m_moddt")
    private ZonedDateTime memberModifyDate;
    //생성 날짜와 시간
    @Column(name = "m_regdt")
    private ZonedDateTime memberRegisterDate;


    //기본 생성자
    public WellMemberInfoEntity() {
    }


}