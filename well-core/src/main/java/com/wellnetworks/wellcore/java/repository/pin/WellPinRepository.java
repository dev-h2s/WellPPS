package com.wellnetworks.wellcore.java.repository.pin;

import com.wellnetworks.wellcore.java.domain.pin.WellPinEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WellPinRepository extends JpaRepository<WellPinEntity, Long> {
    Optional<WellPinEntity> findByPinNum(String pinNum);

    // 통신사와 요금제로 그룹화하여 PIN 개수를 계산
    @Query("SELECT w.operatorName, w.productName, COUNT(w) FROM WellPinEntity w WHERE w.isUseFlag = false AND w.isSaleFlag = true GROUP BY w.operatorName, w.productName")
    List<Object[]> countPinsByOperatorNameAndProductName();

    Page<WellPinEntity> findAll(Specification<WellPinEntity> spec, Pageable pageable);

}
