//package com.wellnetworks.secure.java.security;
//
//import com.wellnetworks.wellcore.java.domain.permission.PermissionKey;
//import com.wellnetworks.wellcore.java.repository.member.WellGroupRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.Set;
///*
//* 권한 관련 클래스
//
//* */
//
//// Spring 컨테이너가 해당 클래스의 인스턴스를 Bean으로 생성 관리하게 됩니다.
//@Component
//// 롬복(Lombok)의 어노테이션. 필요한 생성자를 자동으로 생성함.
//@RequiredArgsConstructor
//public class WellAuthorize {
//
//    @Autowired  // 스프링의 의존성 주입을 위한 어노테이션.
//    private final WellGroupRepository wellGroupRepository;
//
//    private Set<String> userPermissions = null;  // 현재 사용자의 권한 저장.
//    private RoleHierarchy roleHierarchy = null;  // 권한 계층 저장.
//
//    private final Map<String, String> menuPermissionMap = MenuPermission.asMap();  // 메뉴 권한 매핑 정보.
//    private final Map<String, String> menuPermissionActionMap = MenuPermissionAction.asMap();  // 메뉴 액션 권한 매핑 정보.
//
//    // 현재 사용자의 권한 목록을 가져오는 메서드
//    private Set<String> getAuthoritySet() {
//        if (userPermissions == null) {  // 권한이 아직 설정되지 않았을 경우
//            var userAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();  // 현재 사용자의 권한을 가져옴.
//            if (roleHierarchy != null) {  // 권한 계층이 설정되어 있다면
//                userAuthorities = roleHierarchy.getReachableGrantedAuthorities(userAuthorities);  // 계층적 권한 확장
//            }
//            userPermissions = AuthorityUtils.authorityListToSet(userAuthorities);  // 권한 목록을 Set으로 변환
//        }
//        return userPermissions;  // 권한 목록 반환
//    }
//
//    // 사용자가 주어진 권한 키를 가지고 있는지 확인하는 메서드
//    public boolean hasUserPermission(String... permissionKeys) {
//        var permissions = getAuthoritySet();  // 사용자 권한 목록 가져오기
//
//        if (permissions == null || permissions.isEmpty()) {  // 권한 목록이 없다면
//            return false;  // false 반환
//        }
//
//        if (permissions.contains(PermissionKey.SUPER_ADMIN)) {  // 사용자가 슈퍼 관리자 권한을 가지고 있다면
//            return true;  // true 반환
//        }
//
//        for (String permissionKey : permissionKeys) {  // 각 권한 키에 대해
//            if (permissions.contains(permissionKey)) {  // 사용자가 해당 권한을 가지고 있다면
//                return true;  // true 반환
//            }
//        }
//
//        return false;  // 해당 권한이 없다면 false 반환
//    }
//
//    // 사용자가 메뉴에 대한 특정 권한을 가지고 있는지 확인하는 메서드
//    public boolean hasMenuPermission(String menuPermission, String... menuActions) {
//        var permissions = getAuthoritySet();  // 사용자 권한 목록 가져오기
//
//        if (permissions == null || permissions.isEmpty()) {  // 권한 목록이 없다면
//            return false;  // false 반환
//        }
//
//        if (permissions.contains(PermissionKey.SUPER_ADMIN)) {  // 사용자가 슈퍼 관리자 권한을 가지고 있다면
//            return true;  // true 반환
//        }
//
//        var groupPermissionKey = permissions.stream().filter(p -> p.startsWith("GROUP_")).findFirst().orElse(null);  // "GROUP_"으로 시작하는 권한을 찾음.
//
//        if (groupPermissionKey == null) {  // 그룹 권한이 없다면
//            return false;  // false 반환
//        }
//
//        var menuPermissionList = wellGroupRepository.findByemployeeManagerGroupKey(groupPermissionKey);  // 그룹 권한에 해당하는 메뉴 권한 목록을 가져옴.
//
//        if (menuPermissionList.isEmpty()) {  // 메뉴 권한 목록이 없다면
//            return false;  // false 반환
//        }
//
//        for (String menuAction : menuActions) {  // 각 메뉴 액션에 대해
//            if (menuPermissionList.get().groupPermissionsKeysStringList.contains(
//                    MenuPermissionUtil.BuildPermissionString(menuPermission, menuAction)
//            )) {  // 메뉴 권한 목록에 해당 메뉴 액션이 포함되어 있다면
//                return true;  // true 반환
//            }
//        }
//
//        return false;  // 해당 메뉴 액션이 없다면 false 반환
//    }
//
//}
