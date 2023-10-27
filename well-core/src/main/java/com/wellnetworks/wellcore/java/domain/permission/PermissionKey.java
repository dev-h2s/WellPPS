package com.wellnetworks.wellcore.java.domain.permission;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
//PermissionKey는 권한 목록을 상수로 가지는 어노테이션
public @interface PermissionKey {
    // 로그인 가능
    String LOGIN = "ROLE_LOGIN";
    // 최고 관리자
    String SUPER_ADMIN = "ROLE_SUPER_ADMIN";
    // 직원
    String MEMBER = "ROLE_MEMBER";
    // 거래처
    String PARTNER = "ROLE_PARTNER";
    // 회원가입 심사
    String SIGNUP_SCREENING = "ROLE_SIGNUP_SCREENING";
    // 패스워드 변경 필요 (오타 주의)
    String PASSWORD_CHANGE = "ROLE_PWD_CHG";
    // 임시 패스워드 발급 상태
    String USE_TEMPORARY_PASSWORD = "ROLE_TMP_PWD";
    // 사원관리 수정 권한
    String MEMBER_SCREENING = "ROLE_MEMBER_SCREENING";

    /**
     * PermissionKeyCompanion은 PermissionKey의 정적 멤버를 관리하는 유틸리티 클래스
     */
    class PermissionKeyCompanion {
        // PermissionKey의 멤버들을 이름과 해당하는 값을 가지는 맵으로 변환하는 메서드
        public Map<String, String> asMap() {
            Map<String, String> map = new HashMap<>();
            // 각 상수 필드와 그에 해당하는 값을 맵에 추가합니다.
            map.put("LOGIN", LOGIN);
            map.put("SUPER_ADMIN", SUPER_ADMIN);
            map.put("MEMBER", MEMBER);
            map.put("PARTNER", PARTNER);
            map.put("SIGNUP_SCREENING", SIGNUP_SCREENING);
            map.put("PASSWORD_CHANGE", PASSWORD_CHANGE);
            map.put("USE_TEMPORARY_PASSWORD", USE_TEMPORARY_PASSWORD);
            map.put("MEMBER_SCREENING", MEMBER_SCREENING);
            // 완성된 맵을 반환합니다.
            return map;
        }
    }
}
