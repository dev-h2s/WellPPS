package com.wellnetworks.wellcore.java.dto.Partner;

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
    @NotBlank(message = "ceo이름은 필수 입력 항목입니다.")
    private String ceoName;

    @Email(message = "올바른 이메일 주소를 입력하세요.")
    @NotBlank(message = "이메일 주소는 필수 입력 항목입니다.")
    private String emailAddress;

    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    private String ceoTelephone;

    @NotBlank(message = "사업자 등록번호는 필수 입력 항목입니다.")
    private String registrationNumber;

    private List<WellFileDetailDTO> fileDetails = new ArrayList<>();
    private List<MultipartFile> businessLicenseFiles;
    private List<MultipartFile> idCardFiles;

    @NotBlank(message = "가입형태는 필수 입력 항목입니다.")
    private String discountCategory;

    private LocalDate desiredDate;
    private String registrationAddress;
    private String registrationDetailAddress;
    private String  partnerTelephone;
    private Boolean TermsOfUse;

}
