package com.wellnetworks.wellcore.java.service.member;
import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;

import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerUpdateDTO;
import com.wellnetworks.wellcore.java.dto.member.*;
import com.wellnetworks.wellcore.java.repository.File.WellEmployeeFileRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeGroupRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;

import com.wellnetworks.wellcore.java.service.File.WellFileStorageService;
import io.micrometer.common.KeyValues;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WellEmployeeService {
    /*
    사원의 정부에 관련된 기능을 처리:
            주어진 회원 ID에 해당하는 회원 정보를 가져오는 함수 : getMemberByIdx
            회원 정보를 검색하고 페이지별로 반환하는 함수: searchMember
            주어진 회원 이름에 해당하는 회원 정보를 가져오는 함수: getMemberByMemberName
            새 회원 정보를 생성하는 함수: createMember
            회원 정보를 업데이트하고 파일을 업로드하는 함수: updateMember
            주어진 회원 ID에 해당하는 회원 정보를 삭제하는 함수: deleteMemberById
    */

    @Autowired private WellEmployeeUserRepository wellEmployeeUserRepository; // 사원에 관한

    @Autowired private WellEmployeeRepository wellEmployeeRepository;
    @Autowired private WellFileStorageService fileStorageService;
    @Autowired private WellEmployeeGroupRepository wellEmployeeGroupRepository;

    @Autowired private WellEmployeeFileStorageService employeeFileService;

    @Autowired private  WellEmployeeFileRepository employeeFileRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private KeyValues baseSpec;


    // 사원 상세 조회
    public Optional<WellEmployeeInfoDetailDTO> getEmployeeByEmployeeIdx(String employeeIdx) {
        // 사원의 인덱스를 기반으로 DB에서 사원 정보 조회
        WellEmployeeEntity employeeEntity = wellEmployeeRepository.findByEmployeeIdx(employeeIdx);

        if (employeeEntity != null) {
            // employeeEntity와 연결된 파일 정보를 가져와 리스트로 변환
            // Java Streams를 사용하여 employeeEntity에 연결된 파일들을 가져온 후,
            // 그 파일 정보만을 추출하여 List에 담는다
            List<WellEmployeeFileStorageEntity> fileStorages = employeeFileRepository.findByEmployeeIdx(employeeIdx);


            // WellEmployeeEntity에서 WellEmployeeUserEntity 가져오기
            WellEmployeeUserEntity employeeUser = employeeEntity.getEmployeeUser();
            // 부서 정보를 저장할 변수를 초기화
            WellEmployeeManagerGroupEntity department = null;
            WellEmployeeUserEntity identification = null;
            // employeeUser가 null이 아닌 경우,
            // employeeUser와 연결된 WellEmployeeManagerGroupEntity (즉, 부서 정보)를 가져온다.
            if (employeeUser != null) {
                department = employeeUser.getEmployeeManagerGroupKey();
            }
            if (employeeUser != null) {
                identification = employeeUser;
            }
            // WellEmployeeInfoDTO 객체를 생성하여 반환
            // 이 DTO 객체는 사원 정보, 파일 정보, 부서 정보를 포함한다
            return Optional.of(new WellEmployeeInfoDetailDTO(employeeEntity, identification, department, fileStorages));

        }else {
            // 해당 사원 정보가 DB에 없는 경우, 빈 Optional 객체를 반환
            return Optional.empty();
        }
    }


    //사원 리스트 조회
    public Page<WellEmployeeInfoDTO> getAllemployees(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "employeeUserRegisterDate"));
        Page<WellEmployeeUserEntity> employeeUsers = wellEmployeeUserRepository.findAll(pageable);

        // 검색 조건에 맞는 데이터 조회
        List<WellEmployeeInfoDTO> employeeList = new ArrayList<>();

        // 검색된 WellEmployeeUserEntity 리스트를 WellEmployeeInfoDTO 리스트로 변환
//        List<WellEmployeeInfoDTO> employeeInfoDTOList = employeeUsers.stream()
//                .map(user -> {
//                    WellEmployeeEntity employeeEntity = user.getEmployeeEntity();
//                    WellEmployeeManagerGroupEntity managerGroupEntity = user.getManagerGroupEntity();
//                    return new WellEmployeeInfoDTO(employeeEntity, managerGroupEntity);
//                })
//                .collect(Collectors.toList());

        for(WellEmployeeUserEntity employeeUser : employeeUsers){
            WellEmployeeEntity employeeEntity = employeeUser.getEmployeeEntity(); // 가정
            WellEmployeeManagerGroupEntity managerGroupEntity = employeeUser.getManagerGroupEntity(); // 가정

            WellEmployeeInfoDTO employeeInfo = new WellEmployeeInfoDTO(employeeEntity, managerGroupEntity, employeeUser);
            employeeList.add(employeeInfo);
        }

        // PageImpl 객체 생성 및 반환
        return new PageImpl<>(employeeList, pageable, employeeUsers.getTotalElements());
    }

    public class UserAndEmployee {
        private WellEmployeeUserEntity userEntity;
        private WellEmployeeEntity employeeEntity;

        // 생성자
        public UserAndEmployee(WellEmployeeUserEntity userEntity, WellEmployeeEntity employeeEntity) {
            this.userEntity = userEntity;
            this.employeeEntity = employeeEntity;
        }}



// employee id 생성
    private Long generateNextEmployeeId() {
        // 여기에서 다음 직원 ID를 조회하고 1을 더해서 반환하도록 로직을 작성
        // 이 로직은 현재 저장된 직원 중 가장 큰 ID를 조회하고 1을 더하는 방식으로 구현 가능
        // 예를 들어, wellEmployeeRepository에서 가장 큰 직원 ID를 조회하는 메서드를 작성하고 활용

        Long maxEmployeeId = wellEmployeeRepository.findMaxEmployeeId();
        if (maxEmployeeId == null) {
            return 1L; // 만약 직원이 하나도 없으면 1부터 시작
        } else {
            return maxEmployeeId + 1L;
        }
    }

//패스워드 랜덤
    public class PasswordUtil {
        private static final String ALLOWED_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()";
        private static final int TEMP_PWD_LENGTH = 10;
        private static final SecureRandom RANDOM = new SecureRandom();
        private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

    public static String[] generateRandomPassword() {
        StringBuilder builder = new StringBuilder(TEMP_PWD_LENGTH);

        for (int i = 0; i < TEMP_PWD_LENGTH; i++) {
            builder.append(ALLOWED_STRING.charAt(RANDOM.nextInt(ALLOWED_STRING.length())));
        }

        String rawPassword = builder.toString();
        String encryptedPassword = ENCODER.encode(rawPassword); // 암호화된 비밀번호를 반환
        return new String[] {rawPassword, encryptedPassword};
    }
    }

    // 사원 생성
@Transactional
public String employeeJoin (WellEmployeeJoinDTO joinDTO) throws Exception {
    // 중복 아이디 검사
    boolean exists = wellEmployeeUserRepository.existsByEmployeeIdentification(joinDTO.getEmployeeIdentification());
    if (exists) {
        throw new IllegalArgumentException("사용 불가능한 아이디 입니다");
    }
    String[] passwords = PasswordUtil.generateRandomPassword();
    String tempPasswordPlainText = passwords[0]; // 사용자에게 전달할 임시 비밀번호 평문
    String tempPasswordEncrypted = passwords[1]; // 데이터베이스에 저장할 암호화된 비밀번호

// DTO에서 department 값을 가져옴
    String department = joinDTO.getDepartment();

    // department 값을 기준으로 WellEmployeeGroupEntity 객체 조회
    Optional<WellEmployeeManagerGroupEntity> groupOptional = wellEmployeeGroupRepository.findByDepartment(department);

    // 그룹(부서) 정보가 없으면 예외 처리
    WellEmployeeManagerGroupEntity group = groupOptional.orElseThrow(()
            -> new IllegalArgumentException("없는 부서 입니다: " + department));


    // 휴대폰 인증 코드의 만료 시간 설정. 예를 들어, 현재 시간으로부터 10분 후로 설정.
    LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
    LocalDateTime currentDateTime = LocalDateTime.now();
    String newEmployeeIdx = UUID.randomUUID().toString();
    // WellEmployeeUserEntity 객체 생성 및 설정
    WellEmployeeUserEntity userEntity = WellEmployeeUserEntity.builder()
            .employeeIdx(newEmployeeIdx) //생성되는 idx
            .employeeIdentification(joinDTO.getEmployeeIdentification()) // 로그인 id
//            .employeeUserPwd(joinDTO.getEmployeeUserPwd())
            .isPhoneVerified(joinDTO.getIsPhoneVerified()) // user-휴대폰 인증 여부
            .phoneVerificationCode(joinDTO.getPhoneVerificationCode()) // user-휴대폰 인증 코드
            .phoneVerificationAttempts(joinDTO.getPhoneVerificationAttempts()) // user-휴대폰 인증 시도 횟수
            .phoneVerificationExpiration(expirationTime) // user-휴대폰 인증 만료 시간
            .tmpPwd(tempPasswordEncrypted) // 임시 암호화된 비밀번호를 설정
            .employeeManagerGroupKey(group) // 연관 관계 설정
            .isFirstLogin(true)
            .isPasswordResetRequired(true)
            .employeeUserRegisterDate(LocalDateTime.now())
            .build();

    wellEmployeeUserRepository.save(userEntity);

    Long newEmployeeId = generateNextEmployeeId(); // id 증가
    // WellEmployeeEntity 객체 생성 및 설정
    WellEmployeeEntity employeeEntity = WellEmployeeEntity.builder()
            .employeeIdx(newEmployeeIdx)
            .employeeId(newEmployeeId)
            .employeeName(joinDTO.getEmployeeName())
            .belong(joinDTO.getBelong())
            .position(joinDTO.getPosition())
            .employmentState(joinDTO.getEmploymentState())
            .jobType(joinDTO.getJobType())
            .entryDate(joinDTO.getEntryDate())
            .retireDate(joinDTO.getRetireDate())
            .employmentQuitType(joinDTO.getEmploymentQuitType())
            .remainingLeaveDays(joinDTO.getRemainingLeaveDays())
            .residentRegistrationNumber(joinDTO.getResidentRegistrationNumber())
            .telPrivate(joinDTO.getTelPrivate())
            .telWork(joinDTO.getTelWork())
            .email(joinDTO.getEmail())
            .bankName(joinDTO.getBankName())
            .bankAccount(joinDTO.getBankAccount())
            .bankHolder(joinDTO.getBankHolder())
            .homeAddress1(joinDTO.getHomeAddress1())
            .homeAddress2(joinDTO.getHomeAddress2())
            .externalAccessCert(joinDTO.getExternalAccessCert())
            .memo(joinDTO.getMemo())
//            .employeeRegisterDate(LocalDateTime.now())
            .build();
    wellEmployeeRepository.save(employeeEntity);

    employeeFileService.saveFiles(joinDTO, employeeEntity.getEmployeeIdx());
    // 임시 비밀번호 반환
    return tempPasswordPlainText; // 호출하는 곳에서 임시 비밀번호를 받아 화면에 출력할 수 있음
    }

    //패스워드 변경 : user pwd 통일
//    public void changePassword(ChangePasswordRequest changePasswordRequest) {
//        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
//            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
//        }
//
//        WellEmployeeUserEntity userEntity = wellEmployeeUserRepository.findByEmployeeIdentification(changePasswordRequest.getUsername())
//                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
//
//        // 임시 비밀번호로 로그인된 상태인지 확인
////        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), userEntity.getTmpPwd())) {
////            throw new IllegalArgumentException("Current password is incorrect.");
////        }
//
//        // 새 비밀번호 설정 및 임시 비밀번호 무효화
//        userEntity.changePasswordAndInvalidateTempPassword(changePasswordRequest.getNewPassword(), passwordEncoder);
//
//        wellEmployeeUserRepository.save(userEntity);
//    }




    // 사원 검색
    public Page<WellEmployeeInfoDTO> searchEmployeeList(String belong, String employmentState,
                                                        String employeeName,
                                                        String employeeIdentification,
                                                        String position,
                                                        String telPrivate,
                                                        String department,
                                                        Pageable pageable

    ) {
        // 복합 검색 조건에 대한 Specification 생성
        Specification<WellEmployeeUserEntity> spec = // Specification을 WellEmployeeUserEntity에 대해 적용
                Specification.where(EmployeeSpecification.belongContains(belong))
                        .and(EmployeeSpecification.employmentStateContains(employmentState))
                        .and(EmployeeSpecification.nameContains(employeeName)) // 이름 조건 추가
                        .and(EmployeeSpecification.employeeIdentificationContains(employeeIdentification)) // 사원 식별번호 조건 추가
                        .and(EmployeeSpecification.positionContains(position))
                        .and(EmployeeSpecification.telPrivateContains(telPrivate))
                        .and(EmployeeSpecification.departmentContains(department));

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "employeeUserRegisterDate"));

        Page<WellEmployeeUserEntity> employeeUsers = wellEmployeeUserRepository.findAll(spec, pageable);

        // 검색 조건에 맞는 데이터 조회
        List<WellEmployeeInfoDTO> employeeList = new ArrayList<>();

        // 검색된 WellEmployeeUserEntity 리스트를 WellEmployeeInfoDTO 리스트로 변환
//        List<WellEmployeeInfoDTO> employeeInfoDTOList = employeeUsers.stream()
//                .map(user -> {
//                    WellEmployeeEntity employeeEntity = user.getEmployeeEntity();
//                    WellEmployeeManagerGroupEntity managerGroupEntity = user.getManagerGroupEntity();
//                    return new WellEmployeeInfoDTO(employeeEntity, managerGroupEntity);
//                })
//                .collect(Collectors.toList());

        for(WellEmployeeUserEntity employeeUser : employeeUsers){
            WellEmployeeEntity employeeEntity = employeeUser.getEmployeeEntity(); // 가정
            WellEmployeeManagerGroupEntity managerGroupEntity = employeeUser.getManagerGroupEntity(); // 가정

            WellEmployeeInfoDTO employeeInfo = new WellEmployeeInfoDTO(employeeEntity, managerGroupEntity, employeeUser);
            employeeList.add(employeeInfo);
        }

        // PageImpl 객체 생성 및 반환
        return new PageImpl<>(employeeList, pageable, employeeUsers.getTotalElements());
    }


    //수정
    @Transactional(rollbackOn = Exception.class)
    public void update(String employeeIdx, WellEmployeeUpdateDTO updateDTO) throws Exception {
        try {
            // DTO를 통해 엔티티 업데이트
            WellEmployeeEntity employee = wellEmployeeRepository.findByEmployeeIdx(employeeIdx);
            WellEmployeeUserEntity employeeUser = wellEmployeeUserRepository.findByEmployeeIdx(employeeIdx);
            BeanUtils.copyProperties(updateDTO, employee);

            // 거래처 그룹 및 API 키 설정
            WellEmployeeManagerGroupEntity employeeGroup = wellEmployeeGroupRepository.findByEmployeeManagerGroupKey(updateDTO.getEmployeeManagerGroupKey());


            if (employeeGroup == null) {
                throw new RuntimeException("해당 사원 그룹을 찾을 수 없습니다.");
            }

            employeeUser.setEmployeeGroup(employeeGroup);
            employeeUser.setEmployeeUserModifyDate(LocalDateTime.now());
            employeeFileService.updateFiles(updateDTO, employeeIdx);

            // 엔티티의 업데이트 메서드 호출
            employee.updateFromDTO(updateDTO);

        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("사원 수정 중 오류 발생", e);
        }
    }


}




