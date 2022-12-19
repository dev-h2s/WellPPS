package com.wellnetworks.wellcore.domain.enums

/* 데이터베이스 WellPermission 에 데이터 추가/수정/삭제 시 이 열거형 클래스에도 적용하도록 한다. */

enum class PermissionList(val PermitssionKey: String) {
    PERMISSION_LOGIN("ROLE_LOGIN"), // 로그인 가능
    PERMISSION_SUPERADMIN("ROLE_SUPER_ADMIN"), // 최고 관리자
    PERMISSION_MEMBER("ROLE_MEMBER"),  // 직원
    PERMISSION_PARTNER("ROLE_PARTNER"), // 거래처
    PERMISSION_SINGUP_SCREENING("ROLE_SINGUP_SCREENING"), // 회원가입 심사
    PERMISSION_PASSWORD_CHANGE("ROLE_PWD_CHG"), // 패스워드 변경 필요
    PERMISSION_USE_TEMPORARY_PASSWORD("ROLE_TMP_PWD"), // 임시 패스워드 발급 상태
}