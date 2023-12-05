package com.wellnetworks.wellcore.java.service.account;

import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.VirtualAccount.*;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.account.WellVirtualAccountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    // 상세 조회
    public Optional<WellVirtualAccountDetailDTO> getDetailVirtualAccountById(String virtualAccountIdx) {
        return virtualAccountRepository.findById(virtualAccountIdx)
                .map(virtualAccountEntity -> {
                    WellPartnerEntity partnerEntity = virtualAccountEntity.getPartner();
                    String partnerName = partnerEntity != null ? partnerRepository.findPartnerNameByPartnerIdx(partnerEntity.getPartnerIdx()) : null;
                    return new WellVirtualAccountDetailDTO(virtualAccountEntity, partnerEntity, partnerName);
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

    // 생성
    public WellVirtualAccountEntity convertToEntity(WellVirtualAccountCreateDTO dto) {
        WellVirtualAccountEntity entity = new WellVirtualAccountEntity();
        entity.setVirtualBankName(dto.getVirtualBankName());
        entity.setVirtualAccount(dto.getVirtualAccount());
        // 필요한 추가 로직
        return entity;
    }

    //가상계좌 발급(거래처)
    @Transactional
    public WellVirtualAccountIssueDTO issueVirtualAccount(String partnerIdx, String virtualBankName) {
        Optional<WellVirtualAccountEntity> existingAccount = virtualAccountRepository.findByPartnerPartnerIdx(partnerIdx);
        if (existingAccount.isPresent()) {
            throw new RuntimeException("이미 가상계좌를 가진 거래처입니다.");
        }

        List<WellVirtualAccountEntity> availableAccounts = virtualAccountRepository.findAvailableAccountsByVirtualBankNameAndIssuance(virtualBankName, "미발급");

        if (availableAccounts.isEmpty()) {
            throw new RuntimeException("찾으시는 은행의 가상계좌가 존재하지 않습니다. " + virtualBankName);
        }

        Random rand = new Random();
        WellVirtualAccountEntity selectedAccount = availableAccounts.get(rand.nextInt(availableAccounts.size()));

        // 거래처 엔티티 조회 및 설정
        WellPartnerEntity partnerEntity = partnerRepository.findById(partnerIdx)
                .orElseThrow(() -> new RuntimeException("거래처가 존재하지 않습니다."));

        selectedAccount.setPartner(partnerEntity);
        selectedAccount.setIssueDate(LocalDateTime.now());
        selectedAccount.setIssuance("발급");

        WellVirtualAccountEntity savedAccount = virtualAccountRepository.save(selectedAccount);
        return new WellVirtualAccountIssueDTO(savedAccount, partnerEntity);
    }

    //가상계좌 변경(거래처)
    @Transactional
    public WellVirtualAccountIssueDTO changeVirtualAccount(String partnerIdx, String virtualBankName) {
        // 기존 가상 계좌 찾기
        Optional<WellVirtualAccountEntity> currentAccountOpt = virtualAccountRepository.findByPartnerPartnerIdx(partnerIdx);
        if (currentAccountOpt.isPresent()) {
            // 기존 연결 해제 및 상태 변경
            WellVirtualAccountEntity currentAccount = currentAccountOpt.get();
            currentAccount.setPartner(null);
            currentAccount.setIssuance("회수");
            currentAccount.setMemo("거래처 가상계좌 변경");
            virtualAccountRepository.save(currentAccount);
        }

        // 새로운 가상 계좌 선택 및 연결
        List<WellVirtualAccountEntity> availableAccounts = virtualAccountRepository.findAvailableAccountsByVirtualBankNameAndIssuance(virtualBankName, "미발급");
        if (availableAccounts.isEmpty()) {
            throw new RuntimeException("찾으시는 은행의 가상계좌가 존재하지 않습니다. " + virtualBankName);
        }
        Random rand = new Random();
        WellVirtualAccountEntity newAccount = availableAccounts.get(rand.nextInt(availableAccounts.size()));

        // 거래처 엔티티 조회 및 설정
        WellPartnerEntity partnerEntity = partnerRepository.findById(partnerIdx)
                .orElseThrow(() -> new RuntimeException("거래처가 존재하지 않습니다."));

        newAccount.setPartner(partnerEntity);
        newAccount.setIssueDate(LocalDateTime.now());
        newAccount.setIssuance("발급");

        // 새로운 가상 계좌 저장
        WellVirtualAccountEntity savedAccount = virtualAccountRepository.save(newAccount);
        return new WellVirtualAccountIssueDTO(savedAccount, partnerEntity);
    }

    //수정(발급)
    @Transactional(rollbackOn = Exception.class)
    public void updateVirtualAccount(String virtualAccountIdx, String partnerIdx) {
        try {
            WellVirtualAccountEntity virtualAccount = virtualAccountRepository.findById(virtualAccountIdx)
                    .orElseThrow(() -> new RuntimeException("가상계좌를 찾을 수 없습니다."));

            WellPartnerEntity partner = partnerRepository.findById(partnerIdx)
                    .orElseThrow(() -> new RuntimeException("거래처를 찾을 수 없습니다."));

            virtualAccount.setPartner(partner);
            virtualAccount.setIssueDate(LocalDateTime.now());
            virtualAccount.setIssuance("발급");

            virtualAccountRepository.save(virtualAccount);
        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("가상계좌 수정 중 오류 발생", e);
        }
    }


    //수정(미발급, 회수)
    @Transactional(rollbackOn = Exception.class)
    public void updateCollectVirtualAccount(String virtualAccountIdx, String issuance) {
        try {
            WellVirtualAccountEntity virtualAccount = virtualAccountRepository.findById(virtualAccountIdx)
                    .orElseThrow(() -> new RuntimeException("가상계좌를 찾을 수 없습니다."));

            virtualAccount.setPartner(null);
            virtualAccount.setIssueDate(LocalDateTime.now());
            virtualAccount.setIssuance(issuance);
            virtualAccount.setMemo(LocalDate.now() + " : 수정 완료");

            virtualAccountRepository.save(virtualAccount);
        } catch (Exception e) {
            // 롤백을 위해 예외 발생
            throw new RuntimeException("가상계좌 수정 중 오류 발생", e);
        }
    }

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

        Long issuedCount = virtualAccountRepository.countByIssuance("발급");
        Long notIssuedCount = virtualAccountRepository.countByIssuance("미발급");
        Long collectCount = virtualAccountRepository.countByIssuance("회수");

        for (WellVirtualAccountEntity account : accounts) {
            WellPartnerEntity partnerEntity = account.getPartner();
            String partnerName = getPartnerName(partnerEntity);
            WellVirtualAccountInfoDTO accountInfo = new WellVirtualAccountInfoDTO(account, partnerEntity, partnerName, issuedCount, notIssuedCount, collectCount);
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
    @Transactional(rollbackOn = Exception.class)
    public void deleteAccount(String virtualAccountIdx) {
        Optional<WellVirtualAccountEntity> virtualAccount = virtualAccountRepository.findById(virtualAccountIdx);
        virtualAccount.ifPresent(virtualAccountRepository::delete);
    }
}
