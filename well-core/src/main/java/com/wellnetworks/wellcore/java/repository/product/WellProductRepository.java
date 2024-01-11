package com.wellnetworks.wellcore.java.repository.product;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WellProductRepository extends JpaRepository<WellProductEntity, String> {
    List<WellProductEntity> findByOperator(WellOperatorEntity operator);

    Optional<WellProductEntity> findByProductName(String productName);

    //전체요금제
    @Query("SELECT COUNT(p) FROM WellProductEntity p")
    Long productAllCount();
}
