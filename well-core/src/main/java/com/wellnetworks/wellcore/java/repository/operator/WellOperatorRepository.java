package com.wellnetworks.wellcore.java.repository.operator;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WellOperatorRepository extends JpaRepository<WellOperatorEntity, String> {
    Optional<WellOperatorEntity> findByOperatorCode(String operatorCode);
}
