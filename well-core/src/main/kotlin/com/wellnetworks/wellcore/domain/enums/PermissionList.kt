package com.wellnetworks.wellcore.domain.enums

enum class PermissionList(val permitssion: String) {
    PERMISSION_LOGIN("LOGIN"), // 로그인 가능
    PERMISSION_SUPERADMIN("SUPER_ADMIN"), // 최고 관리자
    PERMISSION_MEMBER("MEMBER"),  // 직원
    PERMISSION_PARTNER("PARTNER"), // 거래처
    PERMISSION_PASSWORD_CHANGE("PWD_CHG"),
    PERMISSION_USE_TEMPORARY_PASSWORD("TMP_PWD"),
}