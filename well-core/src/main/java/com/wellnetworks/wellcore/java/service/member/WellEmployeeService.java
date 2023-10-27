package com.wellnetworks.wellcore.java.service.member;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDTO;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDetailDTO;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeRepository;
import com.wellnetworks.wellcore.java.repository.member.WellEmployeeUserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
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
    // 레포지토리
    @PersistenceContext private EntityManager em;

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

// 사원 생성



//@Autowired
//    private WellPermissionRepository wellPermissionRepository; 권한 나중에

//    // 사원 상세 조회
//    public Optional<WellEmployeeInfoDTO> getEmployeeByEmployeeIdx(String employeeIdx) {
//        WellEmployeeEntity employeeEntity = wellEmployeeRepository.findByEmployeeIdx(employeeIdx);
//
//        // 해당 사원과 연결된 파일 정보를 가져와서 리스트로 정리
//        if (employeeEntity != null) {
//            List<WellFileStorageEntity> fileStorages = employeeEntity.getFiles().stream()
//                    .map(WellEmployeeFileStorageEntity::getFile)
//                    .collect(Collectors.toList());
//
//            return Optional.of(new WellEmployeeInfoDTO(employeeEntity, fileStorages));
//        } else {
//            return Optional.empty();
//        }
//    }
////
//    //사원 리스트 조회
//    public List<WellEmployeeInfoDTO> getAllemployees() {
//        List<WellEmployeeEntity> employees = wellEmployeeRepository.findAll();
//        List<WellEmployeeInfoDTO> employeeInfoList = new ArrayList<>();
//        {
//            for (WellEmployeeEntity employeeEntity : employees) {
//                WellEmployeeInfoDTO employeeInfo = new WellEmployeeInfoDTO(employeeEntity);
//                employeeInfoList.add(employeeInfo);
//            }
//        }
//        return employeeInfoList;
//
//    }
//
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

}
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

