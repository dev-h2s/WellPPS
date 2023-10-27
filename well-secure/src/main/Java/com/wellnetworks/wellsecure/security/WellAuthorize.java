package com.wellnetworks.wellsecure.security;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
//
//@Component  // 스프링 빈으로 등록하기 위한 어노테이션
//public class WellAuthorize {
//    @Autowired  // 스프링 빈 주입을 위한 어노테이션
//    private WellGroupRepository wellGroupRepository;
//    private Set<String> userPermissions = null;
//    private RoleHierarchy roleHierarchy = null;
//
//    //개인 dropDownEntity의 map 초기화
//    public Map<String, WellPersonalDropDownEntity> getPersonalDropdownEntities() {
//        Map<String, WellPersonalDropDownEntity> personalDropdownMap = new HashMap<>();
//        return personalDropdownMap;
//    }
//    //개인 dropDownContentEntity의 map 초기화
//    public Map<String, WellPersonalDropContentEntity> getPersonalDropdownContentEntities() {
//      Map<String, WellPersonalDropContentEntity> personalDropdownContentMap = new HashMap<>();
//      return personalDropdownContentMap;
//    }
//
//    //개인 MenuPermissionEntity의 map 초기화
//    public Map<String, WellPersonalMenuPermissionEntity> getWellPersonalMenuPermissionEnties() {
//        Map<String, WellPersonalMenuPermissionEntity> PersonalMenuPermissionEntityMap = new HashMap<>();
//        return PersonalMenuPermissionEntityMap;
//    }
//
//
//    //그룹 dropDownEntity의 map 초기화
//    public Map<String, WellDepartmentDropDownEntity> getwellDepartmentDropDownEnties() {
//        Map<String, WellDepartmentDropDownEntity> DepartmentDropdownMap = new HashMap<>();
//        return DepartmentDropdownMap;
//    }
//    //그룹 dropDownContentEntity의 map 초기화
//    public Map<String, WellDepartmentDropDownContentEntity> getWellDepartmentDropDownContentEnties() {
//        Map<String, WellDepartmentDropDownContentEntity> DepartmentDropdownContentMap = new HashMap<>();
//        return DepartmentDropdownContentMap;
//    }
//
//    //그룹 MenuPermissionEntity의 map 초기화
//    public Map<String, WellDepartmentMenuPermissionEntity> getWellDepartmentMenuPermissionEnties() {
//        Map<String, WellDepartmentMenuPermissionEntity> DepartmentMenuPermissionEntityMap = new HashMap<>();
//        return DepartmentMenuPermissionEntityMap;
//    }
//
//
//    // 현재 인증된 사용자의 권한 목록을 반환하는 메소드
//    private Set<String> getAuthoritySet() {
//        if (userPermissions == null) {
//            // 오류 1: Collection 타입 호환성 문제 해결
//            // 타입 캐스팅을 통해 정확한 타입으로 변환
//            Collection<? extends GrantedAuthority> userAuthorities =
//                    (Collection<? extends GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//            if (roleHierarchy != null) {
//                // 오류 2: Role Hierarchy를 적용하여 더 높은 권한까지 가져옴
//                userAuthorities = roleHierarchy.getReachableGrantedAuthorities(userAuthorities);
//            }
//            // 사용자 권한을 Set<String>으로 변환
//            userPermissions = AuthorityUtils.authorityListToSet(userAuthorities);
//        }
//        return userPermissions;
//    }
//
//
//    // 사용자가 특정 권한을 갖고 있는지 확인하는 메소드
//    public boolean hasUserPermission(String... permissionKeys) {
//        Set<String> permissions = getAuthoritySet();
//
//        if (permissions == null || permissions.isEmpty()) {
//            return false;
//        }
//
//        if (permissions.contains(PermissionKey.SUPER_ADMIN)) {
//            return true;
//        }
//
//        for (String permissionKey : permissionKeys) {
//            if (permissions.contains(permissionKey)) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    // 사용자가 메뉴에 대한 특정 권한을 갖고 있는지 확인하는 메소드
//    public boolean hasMenuPermission(String menuPermission, String... menuActions) {
//        Set<String> permissions = getAuthoritySet();
//
//        if (permissions == null || permissions.isEmpty()) {
//            return false;
//        }
//
//        if (permissions.contains(PermissionKey.SUPER_ADMIN)) {
//            return true;
//        }
//
//        Optional<String> groupPermissionKeyOpt = permissions.stream().filter(p -> p.startsWith("GROUP_")).findFirst();
//
//        if (!groupPermissionKeyOpt.isPresent()) {
//            return false;
//        }
//
//        String groupPermissionKey = groupPermissionKeyOpt.get();
//
//        List<String> menuPermissionList = wellGroupRepository.findByemployeeManagerGroupKey(groupPermissionKey);
//
//        if (menuPermissionList == null || menuPermissionList.isEmpty()) {
//            return false;
//        }
//
//        for (String menuAction : menuActions) {
//            if (menuPermissionList.contains(MenuPermissionUtil.BuildPermissionString(menuPermission, menuAction))) {
//                return true;
//            }
//        }
//
//        return false;
//    }
//}
