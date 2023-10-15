package com.wellnetworks.wellcore.java.service;

import com.wellnetworks.wellcore.java.domain.account.WellDipositEntity;
import com.wellnetworks.wellcore.java.domain.account.WellVirtualAccountEntity;
import com.wellnetworks.wellcore.java.domain.file.WellFileStorageEntity;
import com.wellnetworks.wellcore.java.domain.file.WellPartnerFIleStorageEntity;
import com.wellnetworks.wellcore.java.domain.partner.WellPartnerEntity;
import com.wellnetworks.wellcore.java.dto.Partner.WellPartnerInfoDTO;
import com.wellnetworks.wellcore.java.repository.File.WellFileStorageRepository;
import com.wellnetworks.wellcore.java.repository.Partner.WellPartnerRepository;
import com.wellnetworks.wellcore.java.repository.account.WellDipositRepository;
import com.wellnetworks.wellcore.java.repository.account.WellVirtualAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Service
public class WellPartnerService {

    @Autowired private WellPartnerRepository wellPartnerRepository;

    // 거래처 1개 조회
    public Optional<WellPartnerInfoDTO> getPartnerByPartnerIdx(String partnerIdx) {
        WellPartnerEntity partnerEntity = wellPartnerRepository.findByPartnerIdx(partnerIdx);

        // 해당 거래처와 연결된 파일 정보를 가져와서 리스트로 정리
        if (partnerEntity != null) {
            List<WellFileStorageEntity> fileStorages = partnerEntity.getFiles().stream()
                    .map(WellPartnerFIleStorageEntity::getFile)
                    .collect(Collectors.toList());

            // 거래처가 가상계좌를 가지고 있는 경우, 예치금 정보를 가져옴
            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

            return Optional.of(new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity));
        } else {
            return Optional.empty();
        }
    }

    //거래처 리스트 조회
    public List<WellPartnerInfoDTO> getAllPartners() {
        List<WellPartnerEntity> partners = wellPartnerRepository.findAll();
        List<WellPartnerInfoDTO> partnerInfoList = new ArrayList<>();

        for (WellPartnerEntity partnerEntity : partners) {
            List<WellFileStorageEntity> fileStorages = partnerEntity.getFiles().stream()
                    .map(WellPartnerFIleStorageEntity::getFile)
                    .collect(Collectors.toList());

            WellVirtualAccountEntity virtualAccountEntity = partnerEntity.getVirtualAccount();
            WellDipositEntity dipositEntity = virtualAccountEntity != null ? virtualAccountEntity.getDeposit() : null;

            WellPartnerInfoDTO partnerInfo = new WellPartnerInfoDTO(partnerEntity, fileStorages, dipositEntity);
            partnerInfoList.add(partnerInfo);
        }

        return partnerInfoList;
    }

    @Transactional
     public void deletePartnerIdx(String partnerIdx) {
        // 거래처 idx 삭제 -> 삭제 시 거래처랑 연결되어 있는 애들도 다 삭제 되어야함
        wellPartnerRepository.deleteByPartnerIdx(partnerIdx);
    }

    // 페이지네이션 거래처 검색
    public Page<WellPartnerEntity> getPaginatedPartners(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return wellPartnerRepository.findAll(pageable);
    }


//거래유무 엔티티 개수
//거래중 개수
//    @Query("SELECT p FROM WellPartnerEntity p " +
//            "WHERE p.transactionStatus = '거래중'")
//    Long countByTrading(String transactionStatus);
//    //거래중지 개수
//    @Query("SELECT p FROM WellPartnerEntity p " +
//            "WHERE p.transactionStatus = '거래중지'")
//    Long countByTradeStop(String transactionStatus);
//    //관리대상 개수
//    @Query("SELECT p FROM WellPartnerEntity p " +
//            "WHERE p.transactionStatus = '관리대상'")
//    Long countByTarget(String transactionStatus);
//    // 가등록 개수
//    @Query("SELECT p FROM WellPartnerEntity p " +
//            "WHERE p.transactionStatus = '가등록'")
//    Long countByFakeTrade(String transactionStatus);


    //    public WellPartnerInfoDTO getPartnerInfo(String partnerIdx) {
//        WellPartnerEntity partnerEntity = partnerRepository.findByPartnerIdx(partnerIdx);
//        List<WellFileCountDTO> attachments = getFileCountsByPartnerIdx(partnerIdx);
//        WellPartnerInfoDTO partnerInfoDTO = new WellPartnerInfoDTO(partnerEntity);
//        partnerInfoDTO.setAttachments(attachments);
//        return partnerInfoDTO;
//    }

//    public List<WellFileCountDTO> getFileCountsByPartnerIdx(String partnerIdx) {
//        List<WellFileCountDTO> fileCounts = new ArrayList<>();
//
//        // 파일 종류별 개수 가져오기
//        int registrationCount = fileStorageRepository.countByFileKindAndPartnerFileStorage_Partner_PartnerIdx("등록증", partnerIdx);
//        int contractCount = fileStorageRepository.countByFileKindAndPartnerFileStorage_Partner_PartnerIdx("계약서", partnerIdx);
//
//        WellFileCountDTO registrationDTO = new WellFileCountDTO("등록증", registrationCount);
//        WellFileCountDTO contractDTO = new WellFileCountDTO("계약서", contractCount);
//
//        fileCounts.add(registrationDTO);
//        fileCounts.add(contractDTO);
//
//        return fileCounts;
//    }
}
