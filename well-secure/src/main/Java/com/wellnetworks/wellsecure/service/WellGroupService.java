package com.wellnetworks.wellsecure.service;

import com.wellnetworks.wellcore.java.repository.member.WellGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ComponentScan("com.wellnetworks.wellcore.repository")
public class WellGroupService {
    @Autowired  // 스프링 빈 주입을 위한 어노테이션
    private WellGroupRepository wellGroupRepository;

    // 그룹 ID로 WellGroupDTO를 가져오는 메서드
//    public Optional<WellEmployeeManagerGroupDTOs> getGroupByIdx(String groupKey) {
//        return wellGroupRepository.findByemployeeManagerGroupKey(groupKey)
//                .map(WellEmployeeManagerGroupEntity::toDto);
//    }
}
