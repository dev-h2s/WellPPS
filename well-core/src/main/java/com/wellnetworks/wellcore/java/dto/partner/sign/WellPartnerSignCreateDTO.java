package com.wellnetworks.wellcore.java.dto.partner.sign;

import com.wellnetworks.wellcore.java.dto.FIle.WellFileDetailDTO;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Validated
public class WellPartnerSignCreateDTO {
    private String partnerCode;
    private String ceoName; // CEO 이름
    private String partnerName; //거래처 이름
    private String emailAddress; //이메일

    private String ceoTelephone; // ceo 전화번호

    private String registrationNumber; // 사업자 등록번호

    private List<WellFileDetailDTO> fileDetails = new ArrayList<>();
    private List<MultipartFile> businessLicenseFiles; // 사업자 등록증 첨부
    private List<MultipartFile> idCardFiles; // 대표자신분증 첨부

    private String discountCategory; //충전할인점 구분(개통점,충전점)
    private Boolean visitStatus; // 방문요청
    private LocalDate desiredDate; //방문 희망 날짜
    private String registrationAddress; // 매장 주소
    private String registrationDetailAddress; // 상세 주소
    private String  partnerTelephone; // 연락처
    private Boolean termsOfUse; // 이용약관 동의
    private LocalDateTime signRequestDate; // 회원가입 요청일자
    private Boolean deleteStatus; // 삭제 여부
    private String apiKeyInIdx;
    private boolean inApiFlag;
    private String department; // 부서
    private Long partnerGroupId; // 거래처 그룹 정보 추가

}
