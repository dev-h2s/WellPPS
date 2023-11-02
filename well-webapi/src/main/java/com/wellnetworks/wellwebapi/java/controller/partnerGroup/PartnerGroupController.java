package com.wellnetworks.wellwebapi.java.controller.partnerGroup;

import com.wellnetworks.wellcore.java.domain.partner.WellPartnerGroupEntity;
import com.wellnetworks.wellcore.java.dto.PartnerGroup.WellPartnerGroupCreateDTO;
import com.wellnetworks.wellcore.java.service.partnerGroup.WellPartnerGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(("/admin/hr/"))
@ComponentScan(basePackages={"com.wellnetworks.wellcore"})
public class PartnerGroupController {
    @Autowired
    private WellPartnerGroupService wellPartnerGroupService;

    //거래처 그룹
    @GetMapping("group")
    public ResponseEntity<List<WellPartnerGroupCreateDTO>> getAllGroups() {
        List<WellPartnerGroupEntity> groupEntities = wellPartnerGroupService.getAllGroups();

        // 엔티티를 DTO로 변환
        List<WellPartnerGroupCreateDTO> groupDTOs = groupEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(groupDTOs);
    }

    // 엔티티를 DTO로 변환하는 메서드
    private WellPartnerGroupCreateDTO convertToDTO(WellPartnerGroupEntity entity) {
        WellPartnerGroupCreateDTO dto = new WellPartnerGroupCreateDTO();
        dto.setPartnerGroupId(entity.getPartnerGroupId());
        // 나머지 필드에 대한 매핑

        return dto;
    }

}
