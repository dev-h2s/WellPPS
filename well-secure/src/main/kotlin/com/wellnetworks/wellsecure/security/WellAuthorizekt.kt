package com.wellnetworks.wellsecure.security

import com.wellnetworks.wellcore.domain.enums.MenuPermission
import com.wellnetworks.wellcore.domain.enums.MenuPermissionAction
import com.wellnetworks.wellcore.domain.enums.MenuPermissionUtil
import com.wellnetworks.wellcore.domain.enums.PermissionKey
import com.wellnetworks.wellcore.repository.WellGroupRepository
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.hierarchicalroles.RoleHierarchy
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class WellAuthorizekt {
    @Autowired
    private lateinit var wellGroupRepository: WellGroupRepository

    private var userPermissions: Set<String>? = null
    private var roleHierarchy: RoleHierarchy? = null

    private val menuPermissionMap = MenuPermission.asMap()
    private val menuPermissionActionMap = MenuPermissionAction.asMap()

    private fun getAuthoritySet(): Set<String?>? {
        if (userPermissions == null) {
            var userAuthorities = SecurityContextHolder.getContext().authentication.authorities
            if (roleHierarchy != null) {
                userAuthorities = roleHierarchy!!.getReachableGrantedAuthorities(userAuthorities)
            }
            userPermissions = AuthorityUtils.authorityListToSet(userAuthorities)
        }
        return userPermissions
    }

    fun hasUserPermission(vararg permissionKeys: String): Boolean {
        var permissions = getAuthoritySet();

        if (permissions.isNullOrEmpty()) {
            return false
        }

        if (permissions.contains(PermissionKey.SUPER_ADMIN)) {
            return true
        }

        for (permissionKey in permissionKeys) {
            if (permissions.contains(permissionKey)) {
                return true
            }
        }

        return false
    }

    fun hasMenuPermission(menuPermission: String, vararg menuActions: String): Boolean {
        var permissions = getAuthoritySet();

        if (permissions.isNullOrEmpty()) {
            return false
        }

        if (permissions.contains(PermissionKey.SUPER_ADMIN)) {
            return true
        }

        val groupPermissionKey = permissions.first { it?.startsWith("GROUP_") ?: false };

        if (groupPermissionKey.isNullOrEmpty()) {
            return false
        }

        val menuPermissionList = wellGroupRepository.findByGroupPermissionKey(groupPermissionKey)

        if (menuPermissionList.isEmpty) {
            return false
        }

        for (menuAction in menuActions) {
            if (menuPermissionList.get().groupPermissionsKeysStringList.contains(
                MenuPermissionUtil.BuildPermissionString(menuPermission, menuAction)
            )) {
                return true
            }
        }

        return false
    }
}