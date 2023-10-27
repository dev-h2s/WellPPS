package com.wellnetworks.wellcore.java.repository.opening;

import com.wellnetworks.wellcore.java.domain.opening.WellOpeningEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WellOpeningRepository extends JpaRepository<WellOpeningEntity, String> {

    //개통_idx 검색
    WellOpeningEntity findByOpeningInfoIdx(String openingInfoIdx);

    //개통 등록
    WellOpeningEntity save(WellOpeningEntity wellOpeningEntity);

    //개통_idx삭제(체크항목 삭제)
    WellOpeningEntity deleteByOpeningInfoIdx(String openingInfoIdx);
}
