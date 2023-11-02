package com.wellnetworks.wellcore.java.service.partnerGroup;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WellPartnerGroupService {

    @Autowired
    private WellPartnerGroupRepository wellPartnerGroupRepository;

    //거래처그룹 리스트 조회
    public List<WellPartnerGroupEntity> getAllGroups() {
        return wellPartnerGroupRepository.findAll();
    }
}
