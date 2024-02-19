package com.wellnetworks.wellcore.java.repository.code;

import com.wellnetworks.wellcore.java.domain.code.WellCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellCodeRepository extends JpaRepository<WellCodeEntity, Long> {
    @Query("SELECT DISTINCT c.codeType FROM WellCodeEntity c")
    List<String> findAllDistinctCType();

    List<WellCodeEntity> findByCodeType(String codeType);
}
