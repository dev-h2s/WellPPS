package com.wellnetworks.wellcore.java.repository.account;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellDipositRepository extends JpaRepository<WellDipositEntity, String> {
    //예치금_idx 검색
    WellDipositEntity findByDipositIdx(String dipositIdx);

    //예치금 등록
    WellDipositEntity save(WellDipositEntity wellDipositEntity);

    //예치금_idx삭제(체크항목 삭제)
    WellDipositEntity deleteByDipositIdx(String dipositIdx);
}