package com.wellnetworks.secure.java.service;

import com.wellnetworks.wellcore.java.dto.member.WellEmployeeManagerGroupDTO;
import com.wellnetworks.wellcore.java.repository.member.WellGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * WellGroupSecurityService 클래스는 그룹에 관련된 보안 관련 서비스를 제공합니다.
 * 주로 그룹의 권한 키를 기반으로 그룹 정보를 조회하는 기능을 포함하고 있습니다.
 */
@Component  // 스프링의 빈으로 등록하기 위한 어노테이션
@ComponentScan("com.wellnetworks.wellcore.repository")  // 지정된 패키지에서 스프링 빈을 검색하는 어노테이션
public class WellGroupService {

    @Autowired  // 의존성 주입을 위한 어노테이션
    private WellGroupRepository wellGroupRepository;  // 그룹 정보를 조회하는 레포지토리

    /**
     * groupKey를 기반으로 그룹 정보를 조회하는 메서드
     * @param groupKey 그룹의 권한 키
     * @return Optional<WellGroupDTO> 해당 그룹의 DTO 정보를 포함하는 Optional 객체
     */
    public Optional<WellEmployeeManagerGroupDTO> getGroupByIdx(String groupKey) {
        var group = wellGroupRepository.findByemployeeManagerGroupKey(groupKey);
        return group.map(WellEmployeeManagerGroupDTO::new);  // 그룹 엔터티를 DTO로 변환
    }
}
