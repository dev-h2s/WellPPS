package com.wellnetworks.wellcore.java.service.member;
//회원 기능에 관련된 서비스
import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeUserEntity;
import com.wellnetworks.wellcore.java.repository.member.employee.WellEmployeeUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WellEmployeeUserService {

@Autowired
    private WellEmployeeUserRepository wellEmployeeUserRepository;

//@Autowired
//    private WellPermissionRepository wellPermissionRepository;

    // 사용자 아이디로 사용자 정보 가져오기
    public Optional<WellUserDTO> getUserByUserID(String userid) {
        // 사용자 정보를 조회하여 DTO로 변환
        return WellEmployeeUserRepository.findByemployeeId(userid)
                .map(WellEmployeeUserEntity::getWellUserDTO);
    }

    // 사용자 아이디로 사용자가 존재하는지 확인
    public boolean existByUserID(String userid) {
        // 사용자 존재 유무를 반환
        return WellEmployeeUserRepository.existsByUserID(userid);
    }

    // 사용자 생성
    @Transactional  // 트랜잭션 처리를 위한 어노테이션
    public String createUser(WellUserDTOCreate user) {
        // UUID 생성
        String uuidUser = UUID.randomUUID().toString().toUpperCase();

        // 사용자 Entity 생성
        WellUserEntity createUser = new WellUserEntity(
                uuidUser,
                user.getUserID(), user.getPermissionsKeysStringList(), user.getGroupPermissionIdx(),
                user.getPassword_Hash(), "", ZonedDateTime.now(), 0, ZonedDateTime.now());

        try {
            // DB에 사용자 정보 저장
            wellUserRepository.save(createUser);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return uuidUser;
    }

    // 데이터 총 개수 가져오기
    public long dataTotalCount() {
        // 전체 사용자 수를 반환
        return wellUserRepository.count();
    }

    // 권한 목록 가져오기
    public List<WellPermissionDTO> getPermissionList() {
        // 모든 권한을 조회하여 DTO로 변환
        return wellPermissionRepository.findAll().stream()
                .map(WellPermissionEntity::getWellPermissionDTO)
                .collect(Collectors.toList());
    }

    // 비밀번호 업데이트
    @Transactional(rollbackFor = Exception.class)  // 예외가 발생하면 트랜잭션 롤백
    public boolean updatePassword(WellUserDTOUpdate user) {
        try {
            // 사용자 정보 조회
            WellUserEntity currentEntity = wellUserRepository.findByIdx(user.getIdx().toUpperCase()).orElse(null);
            if (currentEntity == null) {
                return false;
            }

            // TODO: 'updateDto' 메서드의 정의가 필요함
            // currentEntity.updateDto(user);

            // 업데이트된 사용자 정보 저장
            wellUserRepository.save(currentEntity);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    // 특정 사용자의 임시 비밀번호 생성 횟수 가져오기
    public byte getTmpPassCountByIdx(String idx) {
        // 사용자 정보를 조회
        Optional<WellUserEntity> user = wellUserRepository.findByIdx(idx);

        // 임시 비밀번호 생성 횟수 반환
        return user.map(WellUserEntity::getTemporaryPasswordCreateCount).orElse((byte) 0);
    }
}