package com.wellnetworks.wellcore.java.service.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDetailDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeJoinDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeUpdateDTO;
import com.wellnetworks.wellcore.java.repository.File.WellEmployeeFileRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeGroupRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WellEmployeeService {
    private static final Logger log = LoggerFactory.getLogger(WellEmployeeService.class);

    private final WellEmployeeUserRepository wellEmployeeUserRepository; // 사원에 관한
    private final WellEmployeeRepository wellEmployeeRepository;
    private final WellEmployeeGroupRepository wellEmployeeGroupRepository;
    private final WellEmployeeFileStorageService employeeFileService;
    private final WellEmployeeFileRepository employeeFileRepository;

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

        } else {
            // 해당 사원 정보가 DB에 없는 경우, 빈 Optional 객체를 반환
            return Optional.empty();
        }
    }


    //사원 리스트 조회
    public Page<WellEmployeeInfoDTO> getAllEmployees(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "employeeUserRegisterDate"));
        Page<WellEmployeeUserEntity> employeeUsers = wellEmployeeUserRepository.findAll(pageable);

        // 검색 조건에 맞는 데이터 조회
        List<WellEmployeeInfoDTO> employeeList = new ArrayList<>();

        for (WellEmployeeUserEntity employeeUser : employeeUsers) {
            WellEmployeeEntity employeeEntity = employeeUser.getEmployeeEntity(); // 가정
            WellEmployeeManagerGroupEntity managerGroupEntity = employeeUser.getManagerGroupEntity(); // 가정

            WellEmployeeInfoDTO employeeInfo = new WellEmployeeInfoDTO(employeeEntity, managerGroupEntity, employeeUser);
            employeeList.add(employeeInfo);
        }

        // PageImpl 객체 생성 및 반환
        return new PageImpl<>(employeeList, pageable, employeeUsers.getTotalElements());
    }

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
            return new String[]{rawPassword, encryptedPassword};
        }
    }

    // 사원 생성
    @Transactional
    public String employeeJoin(MultipartHttpServletRequest request, WellEmployeeJoinDTO joinDTO) throws Exception {


        // 중복 아이디 검사
        boolean exists = wellEmployeeUserRepository.existsByEmployeeIdentification(joinDTO.getEmployeeIdentification());
        if (exists) {
            throw new IllegalArgumentException("중복된 아이디 입니다");
        }
        String[] passwords = PasswordUtil.generateRandomPassword();
        String tempPasswordPlainText = passwords[0]; // 사용자에게 전달할 임시 비밀번호 평문
        String tempPasswordEncrypted = passwords[1]; // 데이터베이스에 저장할 암호화된 비밀번호

// DTO에서 department 값을 가져옴
        String department = joinDTO.getDepartment();

        // department 값을 기준으로 WellEmployeeGroupEntity 객체 조회
        Optional<WellEmployeeManagerGroupEntity> groupOptional = wellEmployeeGroupRepository.findByDepartment(department);
//    WellEmployeeManagerGroupEntity group = groupOptional.orElseThrow(IllegalArgumentException::new);
        // 그룹(부서) 정보가 없으면 예외 처리
        WellEmployeeManagerGroupEntity group = groupOptional.orElseThrow(()
                -> new IllegalArgumentException("없는 부서 입니다: " + department));


        // 휴대폰 인증 코드의 만료 시간 설정. 예를 들어, 현재 시간으로부터 10분 후로 설정.
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(10);
        LocalDateTime currentDateTime = LocalDateTime.now();
        String newEmployeeIdx = UUID.randomUUID().toString();
        // WellEmployeeUserEntity 객체 생성 및 설정
        try {
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
            employeeFileService.saveFiles(request, employeeEntity.getEmployeeIdx());
        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("사원 생성 중 오류 발생", e);
        }

        // 임시 비밀번호 반환
        return tempPasswordPlainText; // 호출하는 곳에서 임시 비밀번호를 받아 화면에 출력할 수 있음
    }

    // 사원 검색
    public Page<WellEmployeeInfoDTO> searchEmployeeList(String belong, String employmentState, String employeeName,
                                                        String employeeIdentification, String position,
                                                        String telPrivate, String department, Pageable pageable) {
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

        for (WellEmployeeUserEntity employeeUser : employeeUsers) {
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
    public void update(MultipartHttpServletRequest request, String employeeIdx, WellEmployeeUpdateDTO updateDTO) throws RuntimeException {
        try {
            // DTO를 통해 엔티티 업데이트
            WellEmployeeEntity employee = wellEmployeeRepository.findByEmployeeIdx(employeeIdx);
            WellEmployeeUserEntity employeeUser = wellEmployeeUserRepository.findByEmployeeIdx(employeeIdx);
            BeanUtils.copyProperties(updateDTO, employee);

            // 요청에서 department 이름을 받습니다.
            String departmentName = updateDTO.getDepartment();

            // 유효하지 않은 부서 이름을 처리합니다.
            if (departmentName == null || departmentName.trim().isEmpty()) {
                throw new IllegalArgumentException("부서 이름이 유효하지 않습니다.");
            }

            // department 이름으로 WellEmployeeManagerGroupEntity를 조회합니다.
            Optional<WellEmployeeManagerGroupEntity> employeeGroupOptional = wellEmployeeGroupRepository.findByDepartment(departmentName);

            if (employeeGroupOptional.isEmpty()) {
                throw new RuntimeException("해당 부서를 찾을 수 없습니다."); // todo 사설 에러로 변경
            }
            WellEmployeeManagerGroupEntity employeeGroup = employeeGroupOptional.get();
            String employeeManagerGroupKey = employeeGroup.getEmployeeManagerGroupKey();
            updateDTO.setEmployeeManagerGroupKey(employeeManagerGroupKey);

            employeeUser.setEmployeeGroup(employeeGroup);
            employeeUser.setEmployeeUserModifyDate(LocalDateTime.now());
            employeeFileService.deleteFileByEmployeeIdx(employeeIdx);
            employeeFileService.saveFiles(request, employeeIdx);

            // 엔티티의 업데이트 메서드 호출
            employee.updateFromDTO(updateDTO);
            wellEmployeeRepository.save(employee);

        } catch (Exception e) {
            // 롤백을 위해 예외 발생
//            throw new RuntimeException("사원 수정 중 오류 발생", e);
        }
    }


}




