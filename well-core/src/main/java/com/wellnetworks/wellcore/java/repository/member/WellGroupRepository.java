package com.wellnetworks.wellcore.java.repository.member;

import com.wellnetworks.wellcore.java.domain.employee.WellEmployeeManagerGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface WellGroupRepository extends CrudRepository<WellEmployeeManagerGroupEntity, String> {

    // 그룹 권한 키로 WellGroupEntity를 찾는다
    Optional<WellEmployeeManagerGroupEntity> findByGroupPermissionKey(String groupPermissionKey);

    // 모든 WellGroupEntity를 pageable 형식으로 반환한다
    Page<WellEmployeeManagerGroupEntity> findAll(Pageable pageable);

    // 그룹 권한 키로 WellGroupEntity를 삭제하고 삭제된 개수를 반환한다
    Optional<Integer> deleteByGroupPermissionKey(String groupPermissionKey);
}