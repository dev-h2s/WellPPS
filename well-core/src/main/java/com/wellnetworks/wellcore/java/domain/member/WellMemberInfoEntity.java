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


    @Id
    @Column(name = "m_idx", columnDefinition = "uniqueidentifier") //맴버의 고유 식별자 idx
    private String memberIdx;
    //!! ??뭐지
//    @Column(name = "tbl_id",  )
//    private String tableID;
    @Column(name = "m_id") //member의 아이디
    private String memberId;

    @Column(name = "belong") // 소속
    private String currentEmployment;

    @Column(name = "name") //맴버 이름
    private String name;

    @Column(name = "e_mail") //맴버 이메일
    private String eMail;

    @Column(name = "tel_private") // 개인 전화번호
    private String telPrivate;

    @Column(name = "tel_work") // 직장 전화번호
    private String telWork;

    @Column(name = "reg_num") //사원 등록번호
    private String registrationNumber;

    @Column(name = "department") //부서
    private String department;

    @Column(name = "possition") //직위(대표,부장 등)
    private String jobPosition;

    @Column(name = "level") //!!(미정의)
    private byte level;

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

    @Column(name = "emp_type") //직원 유형(정규직, 비정규직 등)
    private String jobType;

    @Column(name = "tel_cert", columnDefinition = "bit") //전화번호 인증여부
    private boolean certificationtel;

    @Column(name = "email_cert", columnDefinition = "bit") //이메일 인증여부
    private boolean certificationEmail;

    @Column(name = "entry_dt") //입사 일자
    private ZonedDateTime entryDatetime;

    @Column(name = "retire_dt") //퇴사 일자
    private ZonedDateTime employmentQuitDatetime;

    @Column(name = "retire_type") //퇴사 사유
    private String employmentQuitType;

    @Column(name = "db_access_power", columnDefinition = "bit")  //데이터 베이스 접근권한
    private boolean dbAccessPower;

    @Column(name = "memo") //맴버 메모
    private String memo;

    @Column(name = "file1_idx") //첨부 파일1
    private String file1Idx;

    @Column(name = "file2_idx") //첨부 파일2
    private String file2Idx;

    @Column(name = "file3_idx") //첨부 파일3
    private String file3Idx;

    @Column(name = "file4_idx") //첨부 파일4
    private String file4Idx;

    @Column(name = "file5_idx") //첨부 파일5
    private String file5Idx;

    @Column(name = "m_moddt") //마지막 수정 날짜
    private ZonedDateTime memberModifyDate;

    @Column(name = "m_regdt") //생성 날짜와 시간
    private ZonedDateTime memberRegisterDate;


}