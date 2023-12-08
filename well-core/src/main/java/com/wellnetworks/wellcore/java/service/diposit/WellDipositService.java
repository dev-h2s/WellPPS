package com.wellnetworks.wellcore.java.service.diposit;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.Diposit.WellDipositCreateDTO;
import com.wellnetworks.wellcore.java.dto.Diposit.WellDipositInfoDTO;
import com.wellnetworks.wellcore.java.dto.Diposit.WellDipositListDTO;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.account.WellDipositRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Page<WellDipositListDTO> getAllDiposits(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "registerDate"));

        Page<WellDipositEntity> diposits = dipositRepository.findAll(pageable);
        List<WellDipositListDTO> dipositListDTOList = new ArrayList<>();

        for (WellDipositEntity diposit : diposits) {
            WellPartnerEntity partnerEntity = diposit.getPartner();
            String partnerName = partnerEntity != null ? partnerRepository.findPartnerNameByPartnerIdx(partnerEntity.getPartnerIdx()) : null;

            WellDipositListDTO dipositListDTO = new WellDipositListDTO(
                    diposit, partnerEntity, partnerName
            );
            dipositListDTOList.add(dipositListDTO);
        }
        return new PageImpl<>(dipositListDTOList);
    }




    //다중 검색
    //입력
    @Transactional
    public WellDipositEntity adjustDiposit(WellDipositCreateDTO createDTO) {
        WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(createDTO.getPartnerIdx());
        if (partnerEntity == null) {
            throw new IllegalArgumentException("해당 거래처가 존재하지 않습니다.");
        }

        WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();

        // 각 필드에 대한 값 유효성 검사 및 초기화
        String dipositIdx = UUID.randomUUID().toString();
        LocalDateTime registerDate = LocalDateTime.now();
        boolean dipositAdjustment = createDTO.isDipositAdjustment();
        String dipositStatus = createDTO.getDipositStatus();
        Integer dipositAmount = dipositAdjustment ? createDTO.getDipositAmount() : -createDTO.getDipositAmount();

        // WellDipositEntity 생성 및 저장
        WellDipositEntity dipositEntity = WellDipositEntity.builder()
                .dipositIdx(dipositIdx)
                .virtualAccount(virtualAccountEntity)
                .partner(partnerEntity)
                .registerDate(registerDate)
                .dipositAdjustment(dipositAdjustment)
                .dipositStatus(dipositStatus)
                .dipositAmount(dipositAmount)
                .memo(createDTO.getMemo())
                .writer("작성자")
                .build();

        dipositEntity = dipositRepository.save(dipositEntity);

        // dipositBalance 갱신
        dipositEntity.setDipositBalance(calculateDipositBalance(partnerEntity));

        // 변경된 dipositEntity를 저장
        dipositEntity = dipositRepository.save(dipositEntity);

        return dipositEntity;
    }

    private Integer calculateDipositBalance(WellPartnerEntity partnerEntity) {
        return dipositRepository.calculateDipositSumByPartner(partnerEntity);
    }

}
