package com.wellnetworks.wellcore.java.repository.product;

import com.wellnetworks.wellcore.java.domain.operator.WellOperatorEntity;
import com.wellnetworks.wellcore.java.domain.product.WellProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WellProductRepository extends JpaRepository<WellProductEntity, String> {
    List<WellProductEntity> findByOperator(WellOperatorEntity operator);

    //전체요금제
    @Query("SELECT COUNT(p) FROM WellProductEntity p")
    Long productAllCount();
}
