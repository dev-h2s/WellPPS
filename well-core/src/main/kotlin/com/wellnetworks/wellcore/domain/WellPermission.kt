package com.wellnetworks.wellcore.domain

enum class WellPermission(val permitssion: String) {
    PERMISSION_LOGIN("LOGIN"), // 로그인 가능
    PERMISSION_SUPERADMIN("SUPER_ADMIN"), // 최고 관리자

}