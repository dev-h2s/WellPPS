package com.wellnetworks.wellcore.java.dto.member;
// 사원 관리 리스트에 뿌리기위한 DTO
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WellEmployeeUpdateDTO {
    private String employeeIdx; // 변동 x
    private Long employeeId;
    private String employeeName; //이름
    private String belong; // 소속 회사
//    private String department; //부서
    private String position; // 직책
    private String employmentState; // 재직상태
    private String jobType; // 고용형태
    private LocalDate entryDate; // 입사일자
    private LocalDate retireDate; // 퇴사일자
    private String employmentQuitType; // 퇴사사유
    private Float remainingLeaveDays; // 잔여연차
    private String residentRegistrationNumber;//주민등록번호
    private String telPrivate; // 개인전화번호
    private String telWork; // 업무 전화번호
    private String email; // 이메일
    private String bankName; // 급여 입금계좌명
    private String bankAccount; // 급여 입금계좌번호
    private String bankHolder; // 예금주
    private String homeAddress1; // 자택 주소1
    private String homeAddress2; // 자택 주소2
    private Boolean externalAccessCert; // 외부접속 여부
    private String memo; // 메모
    private List<MultipartFile> files; //첨부파일
    private String employeeManagerGroupKey; //매니저 그룹키




}
