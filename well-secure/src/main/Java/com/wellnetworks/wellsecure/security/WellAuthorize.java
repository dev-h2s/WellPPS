package com.wellnetworks.wellsecure.security;

import com.wellnetworks.wellcore.java.repository.member.WellGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component  // 스프링 빈으로 등록하기 위한 어노테이션
public class WellAuthorize {
    @Autowired  // 스프링 빈 주입을 위한 어노테이션
    private WellGroupRepository wellGroupRepository;
    private Set<String> userPermissions = null;
    private RoleHierarchy roleHierarchy = null;
    private final Map<String, MenuPermission> menuPermissionMap = MenuPermission.asMap();
    private final Map<String, MenuPermissionAction> menuPermissionActionMap = MenuPermissionAction.asMap();

    // 현재 인증된 사용자의 권한 목록을 반환하는 메소드
    private Set<String> getAuthoritySet() {
        if (userPermissions == null) {
            Collection<GrantedAuthority> userAuthorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            if (roleHierarchy != null) {
                userAuthorities = roleHierarchy.getReachableGrantedAuthorities(userAuthorities);
            }
            userPermissions = AuthorityUtils.authorityListToSet(userAuthorities);
        }
        return userPermissions;
    }

    // 사용자가 특정 권한을 갖고 있는지 확인하는 메소드
    public boolean hasUserPermission(String... permissionKeys) {
        Set<String> permissions = getAuthoritySet();

        if (permissions == null || permissions.isEmpty()) {
            return false;
        }

        if (permissions.contains(PermissionKey.SUPER_ADMIN)) {
            return true;
        }

        for (String permissionKey : permissionKeys) {
            if (permissions.contains(permissionKey)) {
                return true;
            }
        }

        return false;
    }

    // 사용자가 메뉴에 대한 특정 권한을 갖고 있는지 확인하는 메소드
    public boolean hasMenuPermission(String menuPermission, String... menuActions) {
        Set<String> permissions = getAuthoritySet();

        if (permissions == null || permissions.isEmpty()) {
            return false;
        }

        if (permissions.contains(PermissionKey.SUPER_ADMIN)) {
            return true;
        }

        Optional<String> groupPermissionKeyOpt = permissions.stream().filter(p -> p.startsWith("GROUP_")).findFirst();

        if (!groupPermissionKeyOpt.isPresent()) {
            return false;
        }

        String groupPermissionKey = groupPermissionKeyOpt.get();

        List<String> menuPermissionList = wellGroupRepository.findByGroupPermissionKey(groupPermissionKey);

        if (menuPermissionList == null || menuPermissionList.isEmpty()) {
            return false;
        }

        for (String menuAction : menuActions) {
            if (menuPermissionList.contains(MenuPermissionUtil.BuildPermissionString(menuPermission, menuAction))) {
                return true;
            }
        }

        return false;
    }
}
