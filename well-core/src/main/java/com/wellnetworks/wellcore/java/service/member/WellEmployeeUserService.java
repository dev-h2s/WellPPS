package com.wellnetworks.wellcore.java.service.member;
//회원 기능에 관련된 서비스
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeEntity;
import com.wellnetworks.wellcore.java.domain.file.WellEmployeeFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.member.WellEmployeeInfoDTO;
import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeRepository;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WellEmployeeUserService {

@Autowired // 사원 - 유저에 관한
    private WellEmployeeUserRepository wellEmployeeUserRepository;

@Autowired // 사원에 관한
    private WellEmployeeRepository wellEmployeeRepository;

//@Autowired
//    private WellPermissionRepository wellPermissionRepository; 권한 나중에

    // 사원 조회
    public Optional<WellEmployeeInfoDTO> getEmployeeByEmployeeIdx(String employeeIdx) {
        WellEmployeeEntity employeeEntity = wellEmployeeRepository.findByEmployeeIdx(employeeIdx);

        // 해당 사원과 연결된 파일 정보를 가져와서 리스트로 정리
        if (employeeEntity != null) {
            List<WellFileStorageEntity> fileStorages = employeeEntity.getFiles().stream()
                    .map(WellEmployeeFileStorageEntity::getFile)
                    .collect(Collectors.toList());

            return Optional.of(new WellEmployeeInfoDTO(employeeEntity, fileStorages));
        } else {
            return Optional.empty();
        }
    }
//
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
//
//    // 사용자 아이디로 사용자가 존재하는지 확인
//    public boolean existByUserID(String userid) {
//        // 사용자 존재 유무를 반환
//        return WellEmployeeUserRepository.existsByUserID(userid);
//    }
//
//    // 사용자 생성
//    @Transactional  // 트랜잭션 처리를 위한 어노테이션
//    public String createUser(WellUserDTOCreate user) {
//        // UUID 생성
//        String uuidUser = UUID.randomUUID().toString().toUpperCase();
//
//        // 사용자 Entity 생성
//        WellUserEntity createUser = new WellUserEntity(
//                uuidUser,
//                user.getUserID(), user.getPermissionsKeysStringList(), user.getGroupPermissionIdx(),
//                user.getPassword_Hash(), "", ZonedDateTime.now(), 0, ZonedDateTime.now());
//
//        try {
//            // DB에 사용자 정보 저장
//            wellUserRepository.save(createUser);
//        } catch (IllegalArgumentException e) {
//            return null;
//        }
//        return uuidUser;
//    }
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
    }