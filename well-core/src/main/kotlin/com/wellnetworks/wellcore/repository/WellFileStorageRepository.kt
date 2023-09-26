package com.wellnetworks.wellcore.repository
// 파일 crud
import com.wellnetworks.wellcore.domain.WellFileStorageEntity
import org.springframework.data.repository.CrudRepository
import java.util.*

interface WellFileStorageRepository: CrudRepository<WellFileStorageEntity, String> {
    // 첫번째 파일 idx 찾기
    fun findFirstByIdx(idx: String): Optional<WellFileStorageEntity>
    // 첫번째 파일 idx 이면서 PublicTrue인 엔티티 찾기
    fun findFirstByIdxAndPublicTrue(idx: String): Optional<WellFileStorageEntity>
    fun findFirstByIdxAndPublicFalse(idx: String): Optional<WellFileStorageEntity>
    // Idx와 Public 두 가지 조건을 만족하는 엔티티가 존재하는지를 확인
    fun existsByIdxAndPublicTrue(idx: String): Boolean
    // 파일 Idx를 만족하는 엔티티가 존재하는지를 확인
    fun existsByIdx(idx: String): Boolean
    // 파일 Idx를 만족하는 엔티티 삭제
    fun deleteByIdx(idx: String): Optional<WellFileStorageEntity>
}