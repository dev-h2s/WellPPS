package com.wellnetworks.wellcore.java.repository.account;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellDipositRepository extends JpaRepository<WellDipositEntity, String> {
}