package com.wellnetworks.wellcore.java.dto.Partner.sign;

import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Validated
public class WellPartnerSignCreateDTO {
    private String ceoName; // CEO 이름

    private String emailAddress; //이메일

    private String ceoTelephone; // ceo 전화번호

    private String registrationNumber; // 사업자 등록번호

    private List<WellFileDetailDTO> fileDetails = new ArrayList<>();
    private List<MultipartFile> businessLicenseFiles; // 사업자 등록증 첨부
    private List<MultipartFile> idCardFiles; // 대표자신분증 첨부

    private String discountCategory; //

    private LocalDate desiredDate; //희망 날짜
    private String registrationAddress; // 매장 주소
    private String registrationDetailAddress; // 상세 주소
    private String  partnerTelephone; // 연락처
    private Boolean TermsOfUse; // 이용약관 동의

}
