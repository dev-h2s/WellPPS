package com.wellnetworks.wellcore.java.domain.opening;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDateTime;

//개통
@Entity
@Getter
public class WellOpeningEntity {

    @Id //개통_idx
    @Column(name = "op_info_idx", columnDefinition = "uniqueidentifier")
    private String openingInfoIdx;

    @Column(name = "p_idx", columnDefinition = "uniqueidentifier") //거래처_idx
    private String partnerIdx;

    @Column(name = "op_info_id", unique = true) //개통_id
    private Long openingInfoId;

    @Column(name = "passport_existence") //여권유무
    private Boolean passportExistence;

    @Column(name = "country") //국가
    private String country;

    @Column(name = "sales_representative") //영업담당
    private String salesRepresentative;

    @Column(name = "inspector") //검수자
    private String inspector;

    @Column(name = "op_tel") //개통전화번호
    private String openingPhoneNumber;

    @Column(name = "op_history_type") //개통내역구분
    private String openingHistoryType;

    @Column(name = "payment_type") //선불후불
    private String paymentType;

    @Column(name = "county_type") //내국인외국인
    private String countyType;

    @Column(name = "user_id") //입력자아이디
    private String userId;

    @Column(name = "inspection_flag") //검수
    private Boolean inspectionFlag;

    @Column(name = "device_model") //단말기모델
    private String deviceModel;

    @Column(name = "p_name") //고객명
    private String partnerName;

    @Column(name = "auto_charge_flag") //자동충전
    private Boolean autoChargeFlag;

    @Column(name = "write_type") //입력경로
    private String writeType;

    @Column(name = "reg_dt") //생성일자
    private LocalDateTime registerDate;

    @Column(name = "mod_dt") //수정일자
    private LocalDateTime modifyDate;

    @Column(name = "user_name") //입력자이름
    private String userName;

    @Column(name = "memo") //개통메모
    private String memo;
}
