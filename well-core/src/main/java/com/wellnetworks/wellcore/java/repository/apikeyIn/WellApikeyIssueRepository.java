package com.wellnetworks.wellcore.java.repository.apikeyIn;

import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellApikeyIssueRepository extends JpaRepository<WellApikeyIssueEntity, Long> {
}