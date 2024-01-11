package com.wellnetworks.wellcore.java.domain.partner;
//회원가입 관리(거래처 회원가입시 충전점 리스트)

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)

public class WellPartnerSignEntity {
    @Id
    @Column(name = "sign_id")
    private Long id;

    @Column(name = "desired_date") //요청 일자
    private LocalDate desiredDate;

    @Column(name = "p_name", nullable = false) //거래처명
    private String partnerName;

    @Column(name = "ceo_name") //대표자명
    private String ceoName;

    @Column(name="email_addr") //이메일주소
    private String emailAddress;

    @Column(name="ceo_tel") //대표자전화번호
    private String ceoTelephone;

    @Column(name = "registration_number") //사업자등록번호
    private String registrationNumber;

    @Column(name = "discount_category") //가입형태(거래처할인타입)
    private String discountCategory;

    @Column(name = "visit_status") //방문요청여부
    private Boolean visitStatus;

    @Column(name = "vidit_desired_date") //방문희망날짜
    private LocalDate visitDesiredDate;

    @Column(name = "confirmed_visit_date") //방문확정날짜
    private LocalDate ConfirmedVisitDate;

    @Column(name = "registration_address") //사업자등록주소(방문주소)
    private String registrationAddress;

    @Column(name = "registration_detailAddress") //사업자상세주소
    private String registrationDetailAddress;

    @Column(name = "partner_telephone") //사업장 연락처
    private String  partnerTelephone;

    @Column(name = "TermsOf_use") //이용약관 동의 여부
    private Boolean TermsOfUse;

}
