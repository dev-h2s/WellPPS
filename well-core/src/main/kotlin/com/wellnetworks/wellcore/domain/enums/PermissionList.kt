package com.wellnetworks.wellcore.domain.enums

import kotlin.reflect.full.companionObject
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.valueParameters

/* 데이터베이스 WellPermission 에 데이터 추가/수정/삭제 시 이 열거형 클래스에도 적용하도록 한다. */

annotation class PermissionKey {
    companion object {
        const val LOGIN = "ROLE_LOGIN" // 로그인 가능
        const val SUPER_ADMIN = "ROLE_SUPER_ADMIN" // 최고 관리자
        const val MEMBER = "ROLE_MEMBER"  // 직원
        const val PARTNER = "ROLE_PARTNER" // 거래처
        const val SIGNUP_SCREENING = "ROLE_SIGNUP_SCREENING" // 회원가입 심사
        const val PASSWORD_CHANGE = "ROLE_PWD_CHG" // 패스워드 변경 필요
        const val USE_TEMPORARY_PASSWORD = "ROLE_TMP_PWD" // 임시 패스워드 발급 상태
        const val MEMBER_SCREENING = "ROLE_MEMBER_SCREENING" // 사원관리 수정 권한

        fun asMap() : Map<String, String> {
            val props = PermissionKey::class.companionObject!!.memberProperties.associateBy { it.name }
            return props.keys.associateWith { props[it]?.call().toString()!! }
        }
    }
}
