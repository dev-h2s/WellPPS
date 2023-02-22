package com.wellnetworks.wellcore.domain.enums

import kotlin.reflect.full.companionObject
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.valueParameters

/* 데이터베이스 WellPermission 에 데이터 추가/수정/삭제 시 이 열거형 클래스에도 적용하도록 한다. */

sealed class PermissionList() {
    object SUPER_ADMIN: PermissionList() { const val name = "ROLE_SUPER_ADMIN" } // 최고 관리자
    object LOGIN: PermissionList() { const val name = "RULE_LOGIN" } // 로그인 가능
    object MEMBER: PermissionList() { const val name = "ROLE_MEMBER" } // 직원
    object PARTNER: PermissionList() { const val name = "ROLE_PARTNER" } // 거래처
    object SIGNUP_SCREENING: PermissionList() { const val name = "ROLE_SIGNUP_SCREENING" } // 회원가입 심사
    object PASSWSORD_CHANGE: PermissionList() { const val name = "ROLE_PWD_CHG" } // 패스워드 변경 필요
    object TEMPORARY_PASSWORD: PermissionList() { const val name = "ROLE_TMP_PWD" } // 임시 패스워드 발급 상태
    object MEMBER_SCREENING: PermissionList() { const val name = "ROLE_MEMBER_SCREENING" } // 사원관리 수정 권한
}


annotation class PermissionKey {
    companion object {
        const val PERMISSION_LOGIN = "ROLE_LOGIN" // 로그인 가능
        const val PERMISSION_SUPERADMIN = "ROLE_SUPER_ADMIN" // 최고 관리자
        const val PERMISSION_MEMBER = "ROLE_MEMBER"  // 직원
        const val PERMISSION_PARTNER = "ROLE_PARTNER" // 거래처
        const val PERMISSION_SINGUP_SCREENING = "ROLE_SIGNUP_SCREENING" // 회원가입 심사
        const val PERMISSION_PASSWORD_CHANGE = "ROLE_PWD_CHG" // 패스워드 변경 필요
        const val PERMISSION_USE_TEMPORARY_PASSWORD = "ROLE_TMP_PWD" // 임시 패스워드 발급 상태
        const val PERMISSION_MEMBER_SCREENING = "ROLE_MEMBER_SCREENING" // 사원관리 수정 권한
    }
}

/*
class utiltest {
    companion object {
        fun buildpst(key: PermissionKey): String {
            return key.toString()
        }

        fun BuildPermissionStringAll(): List<String> {
            val permissionKeys = PermissionKey::class.companionObject!!.memberProperties.toList()

            var permissionStringList: MutableList<String> = mutableListOf()

            for (permissionKey in permissionKeys) {
                permissionStringList.add((permissionKey.call(PermissionKey) as List<*>).filter<String>())
                for (menuPermissionType in MenuPermissionAction.values()) {
                    permissionStringList.add(
                        MenuPermissionUtil.BuildPermissionString(
                            menuPermission,
                            menuPermissionType
                        )
                    )
                }
            }

            return permissionStringList.toList()
        }
    }
}
*/