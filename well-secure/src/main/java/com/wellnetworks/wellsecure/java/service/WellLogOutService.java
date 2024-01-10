package com.wellnetworks.wellsecure.java.service;


import com.wellnetworks.wellsecure.java.config.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class WellLogOutService implements LogoutHandler {

    private final RefreshTokenService refreshTokenService;
    private final WellUserDetailService userDetailService; // 이 부분을 추가


    @Autowired
    public WellLogOutService(RefreshTokenService refreshTokenService, WellUserDetailService userDetailService) {
        this.refreshTokenService = refreshTokenService;
        this.userDetailService = userDetailService; // 생성자에서 주입
    }


    // 로그아웃 시 호출될 메서드
//    @Transactional
//
//    public LogoutHandler logoutAndRemoveRefreshToken(String username, HttpServletRequest request, HttpServletResponse response) {
//        UserDetails userDetails = userDetailService.loadUserByUsername(username);
////        db에서 리프레쉬 토큰을 삭제
//        System.out.println("리프레쉬 토큰 삭제가 돌아가나??");
//        refreshTokenService.deleteRefreshToken(userDetails);
//        // 쿠키에서 액세스 토큰을 삭제
////        CookieUtil.deleteCookie(request, response, "access_token");
//        return null;
//    }

    // LogoutHandler 인터페이스의 메서드를 오버라이드합니다.
    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 여기에 로그아웃 로직을 구현합니다.
        System.out.println("리프레쉬 토큰 삭제가 돌아가나??");
        if (authentication != null) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // DB에서 리프레시 토큰 삭제
            refreshTokenService.deleteRefreshToken(userDetails);
            // 쿠키에서 액세스 토큰 삭제
            CookieUtil.deleteCookie(request, response, "access_token");
            // 추가적인 로그아웃 로직 구현
        } else {
            // authentication 객체가 null인 경우 처리
            System.err.println("로그아웃 시도 중 Authentication 객체가 null입니다. 사용자가 이미 로그아웃했거나 세션이 만료되었을 수 있습니다.");
            // 필요한 경우, 사용자에게 오류 메시지 전달 (예: response 객체를 통해 HTTP 상태 코드 설정)
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized 상태 코드 설정
            // 여기에 추가적인 오류 처리 로직을 구현할 수 있습니다.
        }
    }
}
