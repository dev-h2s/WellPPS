package com.wellnetworks.wellcore.java.repository.operator;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WellOperatorRepository extends JpaRepository<WellOperatorEntity, String> {
    Optional<WellOperatorEntity> findByOperatorCode(String operatorCode);

    Optional<WellOperatorEntity> findByOperatorName(String operatorName);

    //전체통신사
    @Query("SELECT COUNT(p) FROM WellOperatorEntity p")
    Long operatorAllCount();

    //운영통신사
//    @Query("SELECT COUNT(p) FROM WellOperatorEntity p WHERE p.isRunFlag = true")
//    Long isRunFlagCount();

    //버전
    @Query("SELECT DISTINCT o.versionId, o.versionName FROM WellOperatorEntity o")
    List<Object[]> findDistinctVersions();
}
