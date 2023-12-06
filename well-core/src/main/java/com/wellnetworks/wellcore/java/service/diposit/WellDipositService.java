package com.wellnetworks.wellcore.java.service.diposit;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.Diposit.WellDipositInfoDTO;
import com.wellnetworks.wellcore.java.dto.VirtualAccount.WellVirtualAccountInfoDTO;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.account.WellDipositRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.wellnetworks.wellcore", "com.wellnetworks.wellwebapi.java.controller"})
public class WellDipositService {

    private final WellDipositRepository dipositRepository;
    private final WellPartnerRepository partnerRepository;

    //개별 조회
    public Optional<WellDipositInfoDTO> getDipositById(String dipositIdx) {
        return dipositRepository.findById(dipositIdx)
                .map(dipositEntity -> {
                    WellPartnerEntity partnerEntity = dipositEntity.getPartner();
                    String partnerName = partnerEntity != null ? partnerRepository.findPartnerNameByPartnerIdx(partnerEntity.getPartnerIdx()) : null;
                    return new WellDipositInfoDTO(dipositEntity, partnerEntity, partnerName);
                });
    }

    //리스트 조회
    //다중 검색
    //입력
}
