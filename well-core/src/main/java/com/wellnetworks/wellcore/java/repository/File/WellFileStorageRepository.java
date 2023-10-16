package com.wellnetworks.wellcore.java.repository.File;

import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WellFileStorageRepository extends JpaRepository<WellFileStorageEntity, String> {

    // 첫번째 파일 idx 찾기
    Optional<WellFileStorageEntity> findFirstByFileIdx(String fileIdx);
    // 파일 Idx를 만족하는 엔티티가 존재하는지를 확인
    boolean existsByFileIdx(String fileIdx);
    // 파일 Idx를 만족하는 엔티티 삭제
    Optional<WellFileStorageEntity> deleteByFileIdx(String fileIdx);
}
