package com.wellnetworks.wellcore.java.repository.operator;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellOperatorRepository extends JpaRepository<WellOperatorEntity, String> {
}
