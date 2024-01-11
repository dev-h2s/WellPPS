package com.wellnetworks.wellcore.java.repository.pin;

import com.wellnetworks.wellcore.java.domain.pin.WellPinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellPinRepository extends JpaRepository<WellPinEntity, String> {

}
