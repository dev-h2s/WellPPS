package com.wellnetworks.wellcore.java.service.member;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDetailDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeJoinDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeManagerGroupDTO;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeGroupRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
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

    @Autowired private WellEmployeeGroupRepository wellEmployeeGroupRepository;




    // 사원 1개 조회
    public Optional<WellEmployeeInfoDetailDTO> getemployeeByemployeeIdx(String employeeIdx) {
        // 사원의 인덱스를 기반으로 DB에서 사원 정보 조회
        WellEmployeeEntity employeeEntity = wellEmployeeRepository.findByEmployeeIdx(employeeIdx);


        // employeeEntity와 연결된 파일 정보를 가져와 리스트로 변환
        // Java Streams를 사용하여 employeeEntity에 연결된 파일들을 가져온 후,
        // 그 파일 정보만을 추출하여 List에 담는다
        if (employeeEntity != null) {
            List<WellFileStorageEntity> fileStorages = employeeEntity.getFiles().stream()
                    .map(WellEmployeeFileStorageEntity::getFile)
                    .collect(Collectors.toList());


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
    public List<WellEmployeeInfoDTO> getAllemployees() {
        List<WellEmployeeEntity> employees = wellEmployeeRepository.findAll();
        List<WellEmployeeInfoDTO> employeeInfoList = new ArrayList<>();

        for (WellEmployeeEntity employeeEntity : employees) {
            List<WellFileStorageEntity> fileStorages = employeeEntity.getFiles().stream()
                    .map(WellEmployeeFileStorageEntity::getFile)
                    .collect(Collectors.toList());

            // WellEmployeeEntity에서 WellEmployeeUserEntity 가져오기
            WellEmployeeUserEntity employeeUser = employeeEntity.getEmployeeUser();
            // 부서 정보를 저장할 변수를 초기화
            WellEmployeeManagerGroupEntity department = null;

            // employeeUser가 null이 아닌 경우,
            // employeeUser와 연결된 WellEmployeeManagerGroupEntity (즉, 부서 정보)를 가져온다.
            if (employeeUser != null) {
                department = employeeUser.getEmployeeManagerGroupKey();
            }

            WellEmployeeInfoDTO employeeInfo = new WellEmployeeInfoDTO(employeeEntity, fileStorages, department);
            employeeInfoList.add(employeeInfo);
        }

        return employeeInfoList;
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
        private static final String ALLOWED_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        private static final int TEMP_PWD_LENGTH = 10;
        private static final SecureRandom RANDOM = new SecureRandom();

        public static String generateRandomPassword() {
            StringBuilder builder = new StringBuilder(TEMP_PWD_LENGTH);

            for (int i = 0; i < TEMP_PWD_LENGTH; i++) {
                builder.append(ALLOWED_STRING.charAt(RANDOM.nextInt(ALLOWED_STRING.length())));
            }

            return builder.toString();
        }
    }

    // 사원 생성
@Transactional
public void employeeJoin (WellEmployeeJoinDTO joinDTO) throws Exception {

// DTO에서 department 값을 가져옴
    String department = joinDTO.getDepartment();

    // department 값을 기준으로 WellEmployeeGroupEntity 객체 조회
    Optional<WellEmployeeManagerGroupEntity> groupOptional = wellEmployeeGroupRepository.findByDepartment(department);

    // 그룹(부서) 정보가 없으면 예외 처리
    WellEmployeeManagerGroupEntity group = groupOptional.orElseThrow(()
            -> new IllegalArgumentException("Invalid department: " + department));

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
            .tmpPwd(PasswordUtil.generateRandomPassword())
            .employeeManagerGroupKey(group) // 연관 관계 설정
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
            .entryDatetime(joinDTO.getEntryDatetime())
            .employmentQuitDatetime(currentDateTime)
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
            .employeeRegisterDate(LocalDateTime.now())
            .build();
    wellEmployeeRepository.save(employeeEntity);

//    return new UserAndEmployee(userEntity, employeeEntity);
    }}



//
//    // 사용자 아이디로 사용자가 존재하는지 확인
//    public boolean existByUserID(String userid) {
//        // 사용자 존재 유무를 반환
//        return WellEmployeeUserRepository.existsByUserID(userid);
//    }
//
    // 사용자 생성
//public String createEmployee(WellEmployeeInsertDTO employeeInsertDTO) {
//    // 사용자 식별자를 WellEmployeeUserService클래스의 createUser 메서드에서 가져와야한다
////    String employeeIdx = UUID.randomUUID().toString().toUpperCase();
//    String employeeIdx = employeeInsertDTO.getEmployeeIdx();
//    // WellEmployeeUserEntity 객체를 생성하여 저장
//    WellEmployeeEntity employeeEntity = new WellEmployeeEntity(
//            employeeIdx,
//            employeeInsertDTO.getEmployeeIdx(),
//            null,  // employeeUser에 대한 정보는 DTO에 없으므로 null 또는 적절한 값을 설정
//            new ArrayList<>(), // files 초기화
//            null,  // tableID에 대한 정보는 DTO에 없으므로 null 또는 적절한 값을 설정
//            employeeInsertDTO.getEmployeeId(),
//            employeeInsertDTO.getBelong(),
//            employeeInsertDTO.getName(),
//            employeeInsertDTO.getEMail(),
//            null,  // telPrivate 정보는 DTO에 없으므로 null 또는 적절한 값을 설정
//            employeeInsertDTO.getTelWork(),
//            null,  // registrationNumber 정보는 DTO에 없으므로 null 또는 적절한 값을 설정
//            employeeInsertDTO.getDepartment(),
//            employeeInsertDTO.getPosition(),
//            null,  // level 정보는 DTO에 없으므로 null 또는 적절한 값을 설정
//            employeeInsertDTO.getHomeAddress1(),
//            employeeInsertDTO.getHomeAddress2(),
//            employeeInsertDTO.getBankName(),
//            employeeInsertDTO.getBankAccount(),
//            employeeInsertDTO.getBankHolder(),
//            employeeInsertDTO.getEmploymentState(),
//            employeeInsertDTO.getJobType(),
//            employeeInsertDTO.isCertificationtel(),
//            false,  // certificationEmail 정보는 DTO에 없으므로 기본값으로 설정
//            employeeInsertDTO.isExternalAccessCert(),
//            employeeInsertDTO.getEntryDatetime(),
//            employeeInsertDTO.getEmploymentQuitDatetime(),
//            employeeInsertDTO.getEmploymentQuitType(),
//            employeeInsertDTO.getRemainingLeaveDays(),
//            employeeInsertDTO.getResidentRegistrationNumber(),
//            false,  // dbAccessPower 정보는 DTO에 없으므로 기본값으로 설정
//            employeeInsertDTO.getMemo(),
//            null,  // employeeModifyDate 정보는 DTO에 없으므로 null 또는 적절한 값을 설정
//            null   // employeeRegisterDate 정보는 DTO에 없으므로 null 또는 적절한 값을 설정
//    );
//
//    employeeUserRepository.save(employeeUserEntity);
//
//    return employeeIdx;


//
//    // 데이터 총 개수 가져오기
//    public long dataTotalCount() {
//        // 전체 사용자 수를 반환
//        return wellUserRepository.count();
//    }
//
//    // 권한 목록 가져오기
//    public List<WellPermissionDTO> getPermissionList() {
//        // 모든 권한을 조회하여 DTO로 변환
//        return wellPermissionRepository.findAll().stream()
//                .map(WellPermissionEntity::getWellPermissionDTO)
//                .collect(Collectors.toList());
//    }
//
//    // 비밀번호 업데이트
//    @Transactional(rollbackFor = Exception.class)  // 예외가 발생하면 트랜잭션 롤백
//    public boolean updatePassword(WellUserDTOUpdate user) {
//        try {
//            // 사용자 정보 조회
//            WellUserEntity currentEntity = wellUserRepository.findByIdx(user.getIdx().toUpperCase()).orElse(null);
//            if (currentEntity == null) {
//                return false;
//            }
//
//            // TODO: 'updateDto' 메서드의 정의가 필요함
//            // currentEntity.updateDto(user);
//
//            // 업데이트된 사용자 정보 저장
//            wellUserRepository.save(currentEntity);
//        } catch (Exception e) {
//            return false;
//        }
//
//        return true;
//    }
//
//    // 특정 사용자의 임시 비밀번호 생성 횟수 가져오기
//    public byte getTmpPassCountByIdx(String idx) {
//        // 사용자 정보를 조회
//        Optional<WellUserEntity> user = wellUserRepository.findByIdx(idx);
//
//        // 임시 비밀번호 생성 횟수 반환
//        return user.map(WellUserEntity::getTemporaryPasswordCreateCount).orElse((byte) 0);
//    }

