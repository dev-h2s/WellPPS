package com.wellnetworks.wellsecure.java.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;


public class CookieUtil {

        public static void setAccessTokenCookie(HttpServletResponse response, String token, int maxAge) {
        Cookie cookie = new Cookie("access_token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // HttpOnly 설정
        cookie.setMaxAge(maxAge);   
        response.addCookie(cookie);
    }
//    public static void deleteAccessTokenCookie(HttpServletResponse response) {
//        // Access Token 쿠키 삭제
//        deleteCookie(response, "access_token");
//    }

    public static String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public static void addCookieForAccess(HttpServletResponse response, String name, String value, int maxAge) {
        // 쿠키를 생성
        Cookie cookie = new Cookie(name, value);
        // 쿠키의 경로를 설정
        cookie.setPath("/");
        // HttpOnly 속성을 비활성화하여 JavaScript에서 쿠키에 접근 가능하도록
        cookie.setHttpOnly(false);
        // 쿠키의 만료 시간을 설정
        cookie.setMaxAge(maxAge);
        // 응답에 쿠키를 추가
        response.addCookie(cookie);
    }


    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        // 쿠키를 생성
        Cookie cookie = new Cookie(name, value);
        // 쿠키의 경로를 설정
        cookie.setPath("/");
        // HttpOnly 속성을 활성화하여 JavaScript에서 쿠키에 접근할 수 없도록
        cookie.setHttpOnly(true);
        // 쿠키의 만료 시간을 설정
        cookie.setMaxAge(maxAge);
        // 응답에 쿠키를 추가
        response.addCookie(cookie);
    }
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        // HTTP 요청에서 모든 쿠키를 가져옵니다.
        Cookie[] cookies = request.getCookies();

        // 쿠키 배열이 비어있지 않고, 쿠키가 하나 이상 존재하는 경우
        if (cookies != null && cookies.length > 0) {
            // 모든 쿠키를 순회하며 주어진 이름의 쿠키를 찾습니다.
            for (Cookie cookie : cookies) {
                // 이름이 일치하는 쿠키를 찾으면 해당 쿠키를 삭제하기 위해 값과 만료 시간을 설정
                if (name.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    // 응답에 수정된 쿠키를 추가하여 삭제
                    response.addCookie(cookie);
                }
            }
        }
    }

    public static String serialize(Object obj) {
        // 객체를 직렬화하고 Base64로 인코딩하여 문자열로 반환
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        if (cookie != null && cookie.getValue() != null) {
            byte[] bytes = Base64.getUrlDecoder().decode(cookie.getValue());
            if (bytes != null && bytes.length > 0) {
                return cls.cast(SerializationUtils.deserialize(bytes));
            }
        }
        return null;
    }
}
