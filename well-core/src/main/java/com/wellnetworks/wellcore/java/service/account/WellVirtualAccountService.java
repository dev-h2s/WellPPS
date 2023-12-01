package com.wellnetworks.wellcore.java.service.account;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.apikeyIn.WellApikeyInEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.APIKEYIN.WellApiKeyInfoDTO;
import com.wellnetworks.wellcore.java.dto.VirtualAccount.WellVirtualAccountCreateDTO;
import com.wellnetworks.wellcore.java.dto.VirtualAccount.WellVirtualAccountInfoDTO;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.account.WellVirtualAccountRepository;
import com.wellnetworks.wellcore.java.service.partner.PartnerSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = {"com.wellnetworks.wellcore", "com.wellnetworks.wellwebapi.java.controller.account"})
public class WellVirtualAccountService {

    private final WellVirtualAccountRepository virtualAccountRepository;
    private final WellPartnerRepository partnerRepository;

    // 개별 조회
    public Optional<WellVirtualAccountInfoDTO> getVirtualAccountById(String virtualAccountIdx) {
        return virtualAccountRepository.findById(virtualAccountIdx)
                .map(virtualAccountEntity -> {
                    WellPartnerEntity partnerEntity = virtualAccountEntity.getPartner();
                    String partnerName = partnerEntity != null ? partnerRepository.findPartnerNameByPartnerIdx(partnerEntity.getPartnerIdx()) : null;
                    return new WellVirtualAccountInfoDTO(virtualAccountEntity, partnerEntity, partnerName);
                });
    }

    // 리스트 조회
    public Page<WellVirtualAccountInfoDTO> getAllAccounts(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "issueDate"));

        Page<WellVirtualAccountEntity> accounts = virtualAccountRepository.findAll(pageable);
        List<WellVirtualAccountInfoDTO> virtualAccountInfoDTOList = new ArrayList<>();

        Long issuedCount = virtualAccountRepository.countByIssuance("발급");
        Long notIssuedCount = virtualAccountRepository.countByIssuance("미발급");
        Long collectCount = virtualAccountRepository.countByIssuance("회수");

        for (WellVirtualAccountEntity virtualAccount : accounts) {
            WellPartnerEntity partnerEntity = virtualAccount.getPartner();
            String partnerName = partnerEntity != null ? partnerRepository.findPartnerNameByPartnerIdx(partnerEntity.getPartnerIdx()) : null;

            WellVirtualAccountInfoDTO accountInfoDTO = new WellVirtualAccountInfoDTO(
                    virtualAccount, partnerEntity, partnerName, issuedCount, notIssuedCount, collectCount
            );
            virtualAccountInfoDTOList.add(accountInfoDTO);
        }
        return new PageImpl<>(virtualAccountInfoDTOList);
    }


    //상세 조회
    // 생성
    public WellVirtualAccountEntity convertToEntity(WellVirtualAccountCreateDTO dto) {
        WellVirtualAccountEntity entity = new WellVirtualAccountEntity();
        entity.setVirtualBankName(dto.getVirtualBankName());
        entity.setVirtualAccount(dto.getVirtualAccount());
        // 필요한 추가 로직
        return entity;
    }
    //수정
    //다중검색
    public Page<WellVirtualAccountInfoDTO> searchAccountList(List<String> partnerNames
            , LocalDate startDate, LocalDate endDate
            , String virtualBankName
            , String issuance
            , String virtualAccount
            , String writer
            , Pageable pageable) {
        Specification<WellVirtualAccountEntity> spec = Specification.where(VirtualAccountSpecification.partnerNamesIn(partnerNames))
                .and(VirtualAccountSpecification.issueDateBetween(startDate, endDate))
                .and(VirtualAccountSpecification.virtualBankNameContains(virtualBankName))
                .and(VirtualAccountSpecification.issuanceEquals(issuance))
                .and(VirtualAccountSpecification.virtualAccountContains(virtualAccount))
                .and(VirtualAccountSpecification.writerContains(writer))
                ;

        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "issueDate"));

        Page<WellVirtualAccountEntity> accounts = virtualAccountRepository.findAll(spec, pageable);

        List<WellVirtualAccountInfoDTO> accountInfoList = new ArrayList<>();

        for (WellVirtualAccountEntity account : accounts) {
            WellPartnerEntity partnerEntity = account.getPartner();
            String partnerName = getPartnerName(partnerEntity);
            WellVirtualAccountInfoDTO accountInfo = new WellVirtualAccountInfoDTO(account, partnerEntity, partnerName);
            accountInfoList.add(accountInfo);
        }
        return new PageImpl<>(accountInfoList, pageable, accounts.getTotalElements());
    }
    // 중복 코드 제거
    private String getPartnerName(WellPartnerEntity partnerEntity) {
        if (partnerEntity != null) {
            return partnerEntity.getPartnerName();
        }
        return null;
    }

    //삭제
}
