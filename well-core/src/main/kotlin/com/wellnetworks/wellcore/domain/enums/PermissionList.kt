package com.wellnetworks.wellcore.domain.enums
// 권한 목록
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.memberProperties

/* 데이터베이스 WellPermission에 데이터 추가/수정/삭제 시 이 열거형 클래스에도 적용하도록 한다. */

@Target
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

        // PermissionKey 클래스의 멤버들을 이름과 해당하는 값을 가지는 맵으로 변환
        fun asMap() : Map<String, String> {
            val props = PermissionKey::class.companionObject!!.memberProperties.associateBy { it.name }
            return props.keys.associateWith { props[it]?.call().toString()!! }
        }
    }
}
